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

import com.up72.hq.service.ISearchService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 搜索表DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class SearchServiceImpl implements  ISearchService{
	
	@Autowired
	private SearchMapper searchMapper;
	
	public void save(Search search){
		searchMapper.insert(search);
	}

    public void update(Search search){
		searchMapper.update(search);
	}
	
    public void delete(java.lang.Long id){
		searchMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public Search getById(java.lang.Long id){
		return searchMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<Search> getPage(Map params, PageBounds rowBounds){
        PageList list = searchMapper.findPage(params, rowBounds);
		return new Page<Search>(list,list.getPagination());
	}
	

}
