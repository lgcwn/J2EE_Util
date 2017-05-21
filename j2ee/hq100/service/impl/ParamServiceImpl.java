/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.hq.dto.resp.ParamResp;
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

import com.up72.hq.service.IParamService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 参数DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class ParamServiceImpl implements  IParamService{

	@Autowired
	private ParamMapper paramMapper;
	@Autowired
	private ParamValueMapper paramValueMapper;

	public Param save(Param model){
		paramMapper.insert(model);
		return model;
	}

	public void update(Param dhsParam){
		paramMapper.update(dhsParam);
	}

	public void delete(Long id){
		paramValueMapper.deleteByParamId(id);
		paramMapper.delete(id);
	}

	@Transactional(readOnly=true)
	public Param getById(Long id){
		return paramMapper.findById(id);
	}

	@Transactional(readOnly=true)
	public Page<Param> getPage(Map params, PageBounds rowBounds){
		PageList<Param> list = paramMapper.findPage(params, rowBounds);
		return new Page<Param>(list,list.getPagination());
	}

	@Override
	public Page<ParamResp> getRespPage(Map<String, Object> params, PageBounds pageBounds) {
		PageList<ParamResp> list = paramMapper.findRespPage(params, pageBounds);
		return new Page<ParamResp>(list,list.getPagination());
	}

	@Override
	public void saveResp(ParamResp paramResp) {
		Param model = paramResp;
		paramMapper.insert(model);
		Long attrId = model.getId();
		List<ParamValue> attrValueList = paramResp.getParamValueList();
		for (ParamValue attrValue : attrValueList) {
			attrValue.setParamId(attrId);
			paramValueMapper.insert(attrValue);
		}
		paramResp.setId(attrId);
	}

	@Override
	public void updateResp(ParamResp paramResp) {
		Param db = paramMapper.findById(paramResp.getId());
		db.setName(paramResp.getName());
		db.setSortId(paramResp.getSortId());
		paramMapper.update(db);
		Long attrId = db.getId();
		List<ParamValue> attrValueList = paramResp.getParamValueList();
		for (ParamValue attrValue : attrValueList) {
			attrValue.setParamId(attrId);
			if(attrValue.getId() > 0) { // 修改属性值
				paramValueMapper.update(attrValue);
			} else { // 添加属性值
				attrValue.setId(null);
				paramValueMapper.insert(attrValue);
			}
		}
	}

	@Override
	public ParamResp getRespById(Long id) {
		return paramMapper.findRespById(id);
	}

	@Override
	public int cntUsing(Long paramId) {
		return paramMapper.cntUsing(paramId);
	}

	@Override
	public List<ParamResp> getListByCat(Cat cat) {
		Map params = new HashMap();
		params.put("catId",cat.getId());
		return paramMapper.findListByCat(params);
	}


}
