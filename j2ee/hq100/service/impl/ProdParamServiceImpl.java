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

import com.up72.hq.service.IProdParamService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 产品参数中间表DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class ProdParamServiceImpl implements  IProdParamService{
	
	@Autowired
	private ProdParamMapper prodParamMapper;
	
	public void save(ProdParam prodParam){
		prodParamMapper.insert(prodParam);
	}

    public void update(ProdParam prodParam){
		prodParamMapper.update(prodParam);
	}
	
    public void delete(java.lang.Long id){
		prodParamMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public ProdParam getById(java.lang.Long id){
		return prodParamMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<ProdParam> getPage(Map params, PageBounds rowBounds){
        PageList list = prodParamMapper.findPage(params, rowBounds);
		return new Page<ProdParam>(list,list.getPagination());
	}
	

}
