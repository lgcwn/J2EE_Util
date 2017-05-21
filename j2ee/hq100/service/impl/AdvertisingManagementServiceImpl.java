/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.hq.dto.resp.AdvertisingManagementResp;
import com.up72.hq.model.*;
import com.up72.hq.dao.*;

import com.up72.framework.util.page.PageBounds;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.up72.hq.service.IAdvertisingManagementService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 广告位管理DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class AdvertisingManagementServiceImpl implements  IAdvertisingManagementService{
	
	@Autowired
	private AdvertisingManagementMapper advertisingManagementMapper;
	
	public void save(AdvertisingManagement advertisingManagement){
		advertisingManagementMapper.insert(advertisingManagement);
	}

    public void update(AdvertisingManagement advertisingManagement){
		advertisingManagementMapper.update(advertisingManagement);
	}
	
    public void delete(java.lang.Long id){
		advertisingManagementMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public AdvertisingManagement getById(java.lang.Long id){
		return advertisingManagementMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<AdvertisingManagement> getPage(Map params, PageBounds rowBounds){
        PageList list = advertisingManagementMapper.findPage(params, rowBounds);
		return new Page<AdvertisingManagement>(list,list.getPagination());
	}

	@Transactional(readOnly=true)
	public Page<AdvertisingManagementResp> getRespPage(Map params, PageBounds rowBounds){
		PageList list = advertisingManagementMapper.findRespPage(params, rowBounds);
		return new Page<AdvertisingManagementResp>(list,list.getPagination());
	}
	

}
