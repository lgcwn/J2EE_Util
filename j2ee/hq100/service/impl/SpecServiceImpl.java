/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.hq.dto.resp.Spec2Resp;
import com.up72.hq.dto.resp.SpecResp;
import com.up72.hq.model.*;
import com.up72.hq.dao.*;
import com.up72.hq.service.*;

import com.up72.framework.util.page.PageBounds;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.up72.hq.service.ISpecService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 规格DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class SpecServiceImpl implements  ISpecService{
	@Autowired
	private SpecMapper specMapper;
	@Autowired
	private SpecValueMapper specValueMapper;

	public Spec save(Spec model){
		specMapper.insert(model);
		return model;
	}

	public void update(Spec Spec){
		specMapper.update(Spec);
	}

	public void delete(Long id){
		specMapper.delete(id);
	}

	@Transactional(readOnly=true)
	public Spec getById(Long id){
		return specMapper.findById(id);
	}

	@Transactional(readOnly=true)
	public Page<Spec> getPage(Map params, PageBounds rowBounds){
		PageList<Spec> list = specMapper.findPage(params, rowBounds);
		return new Page<Spec>(list,list.getPagination());
	}

	@Override
	@Transactional(readOnly=true)
	public Page<SpecResp> getRespPage(Map<String, Object> params, PageBounds pageBounds) {
		PageList<SpecResp> list = specMapper.findRespPage(params, pageBounds);
		return new Page<SpecResp>(list,list.getPagination());
	}

	@Override
	public void saveResp(SpecResp attrResp) {
		Spec model = attrResp;
		specMapper.insert(model);
		Long attrId = model.getId();
		List<SpecValue> attrValueList = attrResp.getSpecValueList();
		for (SpecValue attrValue : attrValueList) {
			attrValue.setSpecId(attrId);
			specValueMapper.insert(attrValue);
		}
		attrResp.setId(attrId);
	}

	@Override
	@Transactional(readOnly=true)
	public int cntUsing(Long specId) {
		return specMapper.cntUsing(specId);
	}

	@Override
	public void updateResp(SpecResp attrResp) {
		Spec db = specMapper.findById(attrResp.getId());
		db.setName(attrResp.getName());
		db.setSortId(attrResp.getSortId());
		specMapper.update(db);
		Long attrId = db.getId();
		List<SpecValue> attrValueList = attrResp.getSpecValueList();
		for (SpecValue attrValue : attrValueList) {
			attrValue.setSpecId(attrId);
			if(attrValue.getId() > 0) { // 修改属性值
				specValueMapper.update(attrValue);
			} else { // 添加属性值
				attrValue.setId(null);
				specValueMapper.insert(attrValue);
			}
		}
	}

	@Override
	@Transactional(readOnly=true)
	public SpecResp getRespById(Long id) {
		return specMapper.findRespById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<SpecResp> getListByCat(Cat cat) {
		Map params = new HashMap();
		params.put("catId",cat.getId());
		return specMapper.findListByCat(params);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Spec2Resp> getListByProdId(Long prodId) {
		List<Spec2Resp> spec2RespList = specMapper.findListByProdId(prodId);
		for (Spec2Resp spec2Resp : spec2RespList) {
			Map params = new HashMap();
			params.put("prodId",prodId);
			params.put("specId",spec2Resp.getId());
			List<SpecValue> specValueList = specValueMapper.findListByProdIdAndSpecId(params);
			spec2Resp.setSpecValueList(specValueList);
		}
		return spec2RespList;
	}

}
