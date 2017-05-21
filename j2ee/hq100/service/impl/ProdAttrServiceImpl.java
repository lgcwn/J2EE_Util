/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.hq.dto.resp.ProdAttrResp;
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

import com.up72.hq.service.IProdAttrService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 产品属性中间表DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class ProdAttrServiceImpl implements  IProdAttrService{
	
	@Autowired
	private ProdAttrMapper prodAttrMapper;
	
	public void save(ProdAttr prodAttr){
		prodAttrMapper.insert(prodAttr);
	}

    public void update(ProdAttr prodAttr){
		prodAttrMapper.update(prodAttr);
	}
	
    public void delete(java.lang.Long id){
		prodAttrMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public ProdAttr getById(java.lang.Long id){
		return prodAttrMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<ProdAttr> getPage(Map params, PageBounds rowBounds){
        PageList list = prodAttrMapper.findPage(params, rowBounds);
		return new Page<ProdAttr>(list,list.getPagination());
	}

	@Transactional(readOnly=true)
	public List<ProdAttrResp> getByAttrIdValId(Map map) {
		return prodAttrMapper.findByAttrIdValId(map);
	}
	

}
