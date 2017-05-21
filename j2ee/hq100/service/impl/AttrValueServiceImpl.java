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

import com.up72.hq.service.IAttrValueService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 属性值DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class AttrValueServiceImpl implements  IAttrValueService{
	@Autowired
	private AttrValueMapper attrValueMapper;

	public AttrValue save(AttrValue model){
		attrValueMapper.insert(model);
		return model;
	}

	public void update(AttrValue dhsAttrValue){
		attrValueMapper.update(dhsAttrValue);
	}

	public void delete(Long id){
		attrValueMapper.delete(id);
	}

	@Transactional(readOnly=true)
	public AttrValue getById(Long id){
		return attrValueMapper.findById(id);
	}

	@Transactional(readOnly=true)
	public Page<AttrValue> getPage(Map params, PageBounds rowBounds){
		PageList<AttrValue> list = attrValueMapper.findPage(params, rowBounds);
		return new Page<AttrValue>(list,list.getPagination());
	}

	@Override
	public int cntUsing(Long attrValueId) {
		return attrValueMapper.cntUsing(attrValueId);
	}

	@Override
	public List<AttrValue> getAndOrderByIds(String ids) {
		return attrValueMapper.findAndOrderByIds(ids);
	}
	
}
