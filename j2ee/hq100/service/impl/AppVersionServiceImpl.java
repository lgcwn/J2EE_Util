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

import com.up72.hq.service.IAppVersionService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 版本管理DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class AppVersionServiceImpl implements  IAppVersionService{
	
	@Autowired
	private AppVersionMapper appVersionMapper;
	
	public void save(AppVersion appVersion){
		appVersionMapper.insert(appVersion);
	}

    public void update(AppVersion appVersion){
		appVersionMapper.update(appVersion);
	}
	
    public void delete(java.lang.Long id){
		appVersionMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public AppVersion getById(java.lang.Long id){
		return appVersionMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<AppVersion> getPage(Map params, PageBounds rowBounds){
        PageList list = appVersionMapper.findPage(params, rowBounds);
		return new Page<AppVersion>(list,list.getPagination());
	}

	/**
	 * 获取最新版本
	 * @param osType
	 * @return
	 */
	public AppVersion getLastestVersionByType(Integer osType) {
		return appVersionMapper.findLastestVersionByType(osType);
	}


}
