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

import com.up72.hq.service.ICatBrandService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 品牌分类关系DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class CatBrandServiceImpl implements  ICatBrandService{
	
	@Autowired
	private CatBrandMapper catBrandMapper;
	
	public void save(CatBrand catBrand){
		catBrandMapper.insert(catBrand);
	}

    public void update(CatBrand catBrand){
		catBrandMapper.update(catBrand);
	}
	
    public void delete(java.lang.Long id){
		catBrandMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public CatBrand getById(java.lang.Long id){
		return catBrandMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<CatBrand> getPage(Map params, PageBounds rowBounds){
        PageList list = catBrandMapper.findPage(params, rowBounds);
		return new Page<CatBrand>(list,list.getPagination());
	}
	

}
