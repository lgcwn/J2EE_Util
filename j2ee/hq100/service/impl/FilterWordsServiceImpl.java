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

import com.up72.hq.service.IFilterWordsService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 过滤词DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class FilterWordsServiceImpl implements  IFilterWordsService{
	
	@Autowired
	private FilterWordsMapper filterWordsMapper;
	
	public void save(FilterWords filterWords){
		filterWordsMapper.insert(filterWords);
	}

    public void update(FilterWords filterWords){
		filterWordsMapper.update(filterWords);
	}
	
    public void delete(java.lang.Long id){
		filterWordsMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public FilterWords getById(java.lang.Long id){
		return filterWordsMapper.findById(id);
	}

	@Override
	public FilterWords getByWords(String word) {
		return filterWordsMapper.findByWords(word);
	}

	@Transactional(readOnly=true)
    public Page<FilterWords> getPage(Map params, PageBounds rowBounds){
        PageList list = filterWordsMapper.findPage(params, rowBounds);
		return new Page<FilterWords>(list,list.getPagination());
	}

	@Override
	public List<FilterWords> getAll() {
		return filterWordsMapper.findAll();
	}

	//审核发布评论
	public String filterWords(String content){

		List<FilterWords> list = getAll();
		for(FilterWords filterWords : list){
			content=content.replaceAll(filterWords.getWords(),filterWords.getReplaceWords());
		}
		return content;
	}
}
