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

import com.up72.hq.service.ISpecValueService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 规格值DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class SpecValueServiceImpl implements  ISpecValueService{

	@Autowired
	private SpecValueMapper specValueMapper;

	public SpecValue save(SpecValue model){
		specValueMapper.insert(model);
		return model;
	}

	public void update(SpecValue SpecValue){
		specValueMapper.update(SpecValue);
	}

	public void delete(Long id){
		specValueMapper.delete(id);
	}

	@Transactional(readOnly=true)
	public SpecValue getById(Long id){
		return specValueMapper.findById(id);
	}

	@Transactional(readOnly=true)
	public Page<SpecValue> getPage(Map params, PageBounds rowBounds){
		PageList<SpecValue> list = specValueMapper.findPage(params, rowBounds);
		return new Page<SpecValue>(list,list.getPagination());
	}

	@Override
	public int cntUsing(Long specValueId) {
		return specValueMapper.cntUsing(specValueId);
	}

	@Override
	public List<SpecValue> getBySpecId(Long specId) {
		return specValueMapper.findListBySpecId(specId);
	}

}
