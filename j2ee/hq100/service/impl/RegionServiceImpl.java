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

import com.up72.hq.service.IRegionService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 区域字典DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class RegionServiceImpl implements  IRegionService{
	
	@Autowired
	private RegionMapper regionMapper;
	
	public void save(Region region){
		regionMapper.insert(region);
	}

    public void update(Region region){
		regionMapper.update(region);
	}
	
    public void delete(java.lang.Long id){
		regionMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public Region getById(java.lang.Long id){
		return regionMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<Region> getPage(Map params, PageBounds rowBounds){
        PageList list = regionMapper.findPage(params, rowBounds);
		return new Page<Region>(list,list.getPagination());
	}
	

}
