/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.hq.dto.resp.TagResp;
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

import com.up72.hq.service.ITagService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 标签DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class TagServiceImpl implements  ITagService{

	@Autowired
	private TagMapper tagMapper;

	public Tag save(Tag model){
		tagMapper.insert(model);
		return model;
	}

	public void update(Tag dhsTag){
		tagMapper.update(dhsTag);
	}

	public void delete(Long id){
		tagMapper.delete(id);
	}

	@Transactional(readOnly=true)
	public Tag getById(Long id){
		return tagMapper.findById(id);
	}

	@Transactional(readOnly=true)
	public Page<Tag> getPage(Map params, PageBounds rowBounds){
		PageList<Tag> list = tagMapper.findPage(params, rowBounds);
		return new Page<Tag>(list,list.getPagination());
	}

	@Transactional(readOnly=true)
	public Page<TagResp> getRespPage(Map params, PageBounds rowBounds){
		PageList<TagResp> list = tagMapper.findRespPage(params, rowBounds);
		return new Page<TagResp>(list,list.getPagination());
	}

	@Transactional(readOnly=true)
	@Override
	public int cntUsing(Long tagId) {
		Map params = new HashMap();
		params.put("tagId", tagId);
		return tagMapper.cntUsing(params);
	}

	@Transactional(readOnly=true)
	@Override
	public TagResp getRespById(Long id) {
		return tagMapper.findRespById(id);
	}

	@Override
	public List<Tag> getListByCat(Cat cat) {
		Map params = new HashMap();
		params.put("catId",cat.getId());
		return tagMapper.findListByCat(params);
	}


}
