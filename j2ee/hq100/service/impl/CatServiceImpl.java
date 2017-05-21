/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2015
 */

package com.up72.hq.service.impl;

import com.up72.framework.util.holder.ApplicationContextHolder;
import com.up72.hq.constant.Cnst;
import com.up72.hq.dao.CatMapper;
import com.up72.hq.dao.SpecMapper;
import com.up72.hq.dao.SpecValueMapper;
import com.up72.hq.dto.resp.CatRankingResp;
import com.up72.hq.dto.resp.CatResp;
import com.up72.hq.dto.resp.CatTwoLevelResp;
import com.up72.hq.dto.resp.CatUsingCntResp;
import com.up72.hq.model.*;
import com.up72.hq.redis.RedisCacheClient;
import com.up72.hq.service.ICatService;
import com.up72.framework.util.ObjectUtils;
import com.up72.framework.util.page.Page;
import com.up72.framework.util.page.PageBounds;
import com.up72.framework.util.page.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 分类DAO实现
 *
 * @author up72
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class CatServiceImpl implements ICatService{
	@Autowired
	private CatMapper catMapper;
	@Autowired
	private SpecValueMapper specValueMapper;
	@Autowired
	private SpecMapper specMapper;

	public Cat save(Cat model) {
		/*
		保存分类
		如果存在父分类，把父分类设置为非叶子节点，删除父分类对应的颜色规格与规格值，添加本分类对应的颜色规格与规格值
		 */
		catMapper.insert(model);
		Long id = model.getId();
		Integer level = model.getLevel();
		if(level == 1) { // 说明是一级分类
			model.setIdPath("" + id);
			model.setRootId(id);
		} else { // 说明是子分类
			Long parentId = model.getParentId();
			model.setIdPath(model.getIdPath() + "," + id);
			Cat parent = catMapper.findById(parentId);
			parent.setIsLeaf(0);
			catMapper.update(parent);
			specValueMapper.deleteByCatId(parentId);
			specMapper.deleteByCatId(parentId);
		}
		Spec spec = newSpec(model);
		specMapper.insert(spec);
		specValueMapper.insert(newSpecValue(spec));
		catMapper.update(model);
		return model;
	}

	/** 生成颜色规格对象 */
	private Spec newSpec(Cat model) {
		Spec spec = new Spec();
		spec.setName("颜色");
		spec.setCatId(model.getId());
		spec.setSortId(1L);
		spec.setIsColor(1);
		return spec;
	}

	/** 生成默认颜色规格值对象 */
	private SpecValue newSpecValue(Spec spec) {
		SpecValue specValue = new SpecValue();
		specValue.setSortId(1L);
		specValue.setSpecId(spec.getId());
		specValue.setSpecValue("默认颜色");
		return specValue;
	}


	public void update(Cat Cat){
		catMapper.update(Cat);
	}

	public void delete(Long id) {
		/*
		1.删除颜色相关的规格值与规格
		2.删除自身
		3.如果删除自身之后，父节点下空了，则把父节点修改为叶子节点，并且给父节点添加颜色规格和规格值
		 */
		// 1.
		specValueMapper.deleteByCatId(id);
		specMapper.deleteByCatId(id);

		// 2.
		Cat cat = catMapper.findById(id);
		catMapper.delete(id);

		// 3.
		Long parentId = cat.getParentId();
		if(parentId == null) {
			return;
		}
		int subCnt = catMapper.cntSub(parentId);
		if(subCnt > 0) {
			return;
		}

		Cat parent = catMapper.findById(parentId);
		parent.setIsLeaf(1);
		catMapper.update(parent);

		Spec spec = newSpec(parent);
		specMapper.insert(spec);
		specValueMapper.insert(newSpecValue(spec));
	}

	@Transactional(readOnly=true)
	public Cat getById(Long id){
		return catMapper.findById(id);
	}
	@Transactional(readOnly=true)
	public CatResp getRespById(Long id){
		return catMapper.findRespById(id);
	}

	@Transactional(readOnly=true)
	public Page<Cat> getPage(Map params, PageBounds rowBounds){
		PageList<Cat> list = catMapper.findPage(params, rowBounds);
		return new Page<Cat>(list,list.getPagination());
	}

	@Transactional
	public List<CatResp> getList(Integer type) {
		List<CatResp> catResps	= catMapper.findTopLevelList(type);
		return catResps;
	}
	@Transactional
	public List<CatResp> getListById(Long id) {
		List<CatResp> catResps	= catMapper.findTopLevelListById(id);
		return catResps;
	}

	@Transactional(readOnly=true)
	public int cntSub(Long parentId){
		return catMapper.cntSub(parentId);
	}

	@Override
	public CatUsingCntResp getUsingCnt(Long id) {
		return catMapper.findUsingCnt(id);
	}

	@Override
	public CatTwoLevelResp getTowLevelCat(Long id) {
		return catMapper.findTowLevelCat(id);
	}

	@Override
	public List<CatTwoLevelResp> getTowLevelList() {
		return catMapper.findTowLevelList();
	}

	@Override
	public List<CatUsingCntResp> getTop10OfToday() {
		Map params = new HashMap();
		String date = Cnst.SDF_SHORT.format(new Date());
		params.put("createDateStart",Long.parseLong(date  + "000000000"));
		params.put("createDateEnd",  Long.parseLong(date  + "235959999"));
		return catMapper.findTop10OfToday(params);
	}

	@Override
	public List<Cat> getLeafList() {
		return catMapper.findLeafList();
	}

	@Override
	public List<CatResp> getAndOrderByIds(String ids) {
		return catMapper.findAndOrderByIds(ids);
	}

	@Override
	public List<CatResp> getByName(String name) {
		return catMapper.findByName(name);
	}


	@Override
	public Page<CatResp> getPageResp(Map params, PageBounds rowBounds) {
		PageList<CatResp> list = catMapper.findPageResp(params, rowBounds);
		return new Page<CatResp>(list,list.getPagination());
	}

	@Override
	public List<CatResp> getListByParentId(Long parentId) {
		return catMapper.findListByParentId(parentId);
	}

	/**
	 * 获取用户关注的商品所拥有的分类
	 * @param request
	 * @return
	 */
	@Override
	public List<Cat> getMemberCatList(HttpServletRequest request) {
		List<Cat> catList = new ArrayList<Cat>();
		Member member = (Member) request.getSession().getAttribute(Cnst.Member.LOGIN_MEMBER);
		if(ObjectUtils.isNotEmpty(member)){
			Map map = new HashMap<>();
			map.put("memberId",member.getId());
			map.put("type",1);
			catList = catMapper.findFavouriteCatList(map);
			//去重
			Set<Cat> setList = new HashSet<Cat>();
			setList.addAll(catList);
			catList = new ArrayList<Cat>();
			catList.addAll(setList);
		}
		return catList;
	}

	@Override
	public List<Cat> getListByParams(Map params) {
		return catMapper.findListByParams(params);
	}

	@Transactional
	public List<CatRankingResp> getListByCatId(Long catId,Integer type) {
		Map<String,Object> params=new HashMap<>();
		params.put("id", catId);
		params.put("type", type);
		return	catMapper.findListByRanking(params);
	}

	@Transactional
	public List<CatRankingResp> getListByCat(Integer type) {
		Map<String,Object> params=new HashMap<>();
		params.put("type", type);
		return	catMapper.findListByRanking(params);
	}
}
