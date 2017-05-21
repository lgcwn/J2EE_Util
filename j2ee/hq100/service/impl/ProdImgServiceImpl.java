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

import com.up72.hq.service.IProdImgService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 产品颜色图片DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class ProdImgServiceImpl implements  IProdImgService{
	
	@Autowired
	private ProdImgMapper prodImgMapper;
	
	public void save(ProdImg prodImg){
		prodImgMapper.insert(prodImg);
	}

    public void update(ProdImg prodImg){
		prodImgMapper.update(prodImg);
	}
	
    public void delete(java.lang.Long id){
		prodImgMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public ProdImg getById(java.lang.Long id){
		return prodImgMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<ProdImg> getPage(Map params, PageBounds rowBounds){
        PageList list = prodImgMapper.findPage(params, rowBounds);
		return new Page<ProdImg>(list,list.getPagination());
	}

	@Transactional(readOnly=true)
	public List<ProdImg> getListByProdId(Long prodId) {
		return prodImgMapper.findListByProdId(prodId);
	}

	@Transactional(readOnly=true)
	public ProdImg getByParams(Map map) {
		return prodImgMapper.findByParams(map);
	}



}
