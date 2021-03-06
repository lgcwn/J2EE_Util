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

import com.up72.hq.service.IAreaService;
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
public class AreaServiceImpl implements  IAreaService{
	
	@Autowired
	private AreaMapper areaMapper;
	
	public void save(Area area){
		areaMapper.insert(area);
	}

    public void update(Area area){
		areaMapper.update(area);
	}
	
    public void delete(java.lang.Long id){
		areaMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public Area getById(java.lang.Long id){
		return areaMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<Area> getPage(Map params, PageBounds rowBounds){
        PageList list = areaMapper.findPage(params, rowBounds);
		return new Page<Area>(list,list.getPagination());
	}
	

}
