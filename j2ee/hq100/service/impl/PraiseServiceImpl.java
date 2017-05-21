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

import com.up72.hq.service.IPraiseService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 我的点赞DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class PraiseServiceImpl implements  IPraiseService{
	
	@Autowired
	private PraiseMapper praiseMapper;
	
	public void save(Praise praise){
		praiseMapper.insert(praise);
	}

    public void update(Praise praise){
		praiseMapper.update(praise);
	}
	
    public void delete(java.lang.Long id){
		praiseMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public Praise getById(java.lang.Long id){
		return praiseMapper.findById(id);
	}

	@Override
	public Praise getByParam(Map map) {
		return praiseMapper.findByParam(map);
	}

	@Transactional(readOnly=true)
    public Page<Praise> getPage(Map params, PageBounds rowBounds){
        PageList list = praiseMapper.findPage(params, rowBounds);
		return new Page<Praise>(list,list.getPagination());
	}
	

}
