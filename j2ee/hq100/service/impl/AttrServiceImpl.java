/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.hq.dto.resp.AttrResp;
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

import com.up72.hq.service.IAttrService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 属性DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class AttrServiceImpl implements  IAttrService{

	@Autowired
	private AttrMapper attrMapper;
	@Autowired
	private AttrValueMapper attrValueMapper;

	public Attr save(Attr model){
		attrMapper.insert(model);
		return model;
	}

	public void update(Attr dhsAttr){
		attrMapper.update(dhsAttr);
	}

	public void delete(Long id){
		attrValueMapper.deleteByAttrId(id);
		attrMapper.delete(id);
	}

	@Transactional(readOnly=true)
	public Attr getById(Long id){
		return attrMapper.findById(id);
	}

	@Transactional(readOnly=true)
	public Page<Attr> getPage(Map params, PageBounds rowBounds){
		PageList<Attr> list = attrMapper.findPage(params, rowBounds);
		return new Page<Attr>(list,list.getPagination());
	}

	@Override
	public AttrResp saveResp(AttrResp attrResp) {
		Attr model = attrResp;
		attrMapper.insert(model);
		Long attrId = model.getId();
		List<AttrValue> attrValueList = attrResp.getAttrValueList();
		for (AttrValue attrValue : attrValueList) {
			attrValue.setAttrId(attrId);
			attrValueMapper.insert(attrValue);
		}
		attrResp.setId(attrId);
		return attrResp;
	}

	@Override
	public Page<AttrResp> getRespPage(Map<String, Object> params, PageBounds rowBounds) {
		PageList<AttrResp> list = attrMapper.findRespPage(params, rowBounds);
		return new Page<AttrResp>(list,list.getPagination());
	}

	@Override
	public AttrResp getRespById(Long id) {
		return attrMapper.findRespById(id);
	}

	@Override
	public void updateResp(AttrResp attrResp) {
		Attr db = attrMapper.findById(attrResp.getId());
		db.setName(attrResp.getName());
		db.setSortId(attrResp.getSortId());
		attrMapper.update(db);
		Long attrId = db.getId();
		List<AttrValue> attrValueList = attrResp.getAttrValueList();
		for (AttrValue attrValue : attrValueList) {
			attrValue.setAttrId(attrId);
			if(attrValue.getId() > 0) { // 修改属性值
				attrValueMapper.update(attrValue);
			} else { // 添加属性值
				attrValue.setId(null);
				attrValueMapper.insert(attrValue);
			}
		}
	}

	@Override
	public int cntUsing(Long attrId) {
		return attrMapper.cntUsing(attrId);
	}

	@Override
	public List<AttrResp> getListByCat(Cat cat) {
		Map params = new HashMap();
		params.put("catId",cat.getId());
		return attrMapper.findListByCat(params);
	}

	@Override
	public List<Attr> getAndOrderByIds(String ids) {
		return attrMapper.findAndOrderByIds(ids);
	}
	

}
