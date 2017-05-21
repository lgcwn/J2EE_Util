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

import com.up72.hq.service.IBrandService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 品牌表DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class BrandServiceImpl implements  IBrandService{
	
	@Autowired
	private BrandMapper brandMapper;
	
	public void save(Brand brand){
		brandMapper.insert(brand);
	}

    public void update(Brand brand){
		brandMapper.update(brand);
	}
	
    public void delete(java.lang.Long id){
		brandMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public Brand getById(java.lang.Long id){
		return brandMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<Brand> getPage(Map params, PageBounds rowBounds){
        PageList list = brandMapper.findPage(params, rowBounds);
		return new Page<Brand>(list,list.getPagination());
	}
	

}
