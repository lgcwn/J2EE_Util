/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

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

import com.up72.hq.service.IParamValueService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 参数值DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class ParamValueServiceImpl implements  IParamValueService{

	@Autowired
	private ParamValueMapper paramValueMapper;

	public ParamValue save(ParamValue model){
		paramValueMapper.insert(model);
		return model;
	}

	public void update(ParamValue dhsParamValue){
		paramValueMapper.update(dhsParamValue);
	}

	public void delete(Long id){
		paramValueMapper.delete(id);
	}

	@Transactional(readOnly=true)
	public ParamValue getById(Long id){
		return paramValueMapper.findById(id);
	}

	@Transactional(readOnly=true)
	public Page<ParamValue> getPage(Map params, PageBounds rowBounds){
		PageList<ParamValue> list = paramValueMapper.findPage(params, rowBounds);
		return new Page<ParamValue>(list,list.getPagination());
	}

	@Override
	public int cntUsing(Long paramValueId) {
		return paramValueMapper.cntUsing(paramValueId);
	}

}
