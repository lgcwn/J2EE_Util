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

import com.up72.hq.service.ICategoryService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class CategoryServiceImpl implements  ICategoryService{
	
	@Autowired
	private CategoryMapper categoryMapper;
	
	public void save(Category category){
		categoryMapper.insert(category);
	}

    public void update(Category category){
		categoryMapper.update(category);
	}
	
    public void delete(java.lang.Long id){
		categoryMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public Category getById(java.lang.Long id){
		return categoryMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<Category> getPage(Map params, PageBounds rowBounds){
        PageList list = categoryMapper.findPage(params, rowBounds);
		return new Page<Category>(list,list.getPagination());
	}
	

}
