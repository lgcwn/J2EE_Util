/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.hq.dto.resp.ChooseListResp;
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

import com.up72.hq.service.IChooseListService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * (评选名单)DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class ChooseListServiceImpl implements  IChooseListService{
	
	@Autowired
	private ChooseListMapper chooseListMapper;
	
	public void save(ChooseList chooseList){
		chooseListMapper.insert(chooseList);
	}

    public void update(ChooseList chooseList){
		chooseListMapper.update(chooseList);
	}
	
    public void delete(java.lang.Long id){
		chooseListMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public ChooseListResp getById(java.lang.Long id){
		return chooseListMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<ChooseListResp> getPage(Map params, PageBounds rowBounds){
        PageList list = chooseListMapper.findPage(params, rowBounds);
		return new Page<ChooseListResp>(list,list.getPagination());
	}
	

}
