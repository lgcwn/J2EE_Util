/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.hq.constant.Cnst;
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

import com.up72.hq.service.IAdvertisingInfoService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * 广告信息DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class AdvertisingInfoServiceImpl implements  IAdvertisingInfoService{
	
	@Autowired
	private AdvertisingInfoMapper advertisingInfoMapper;
	@Autowired
	private AdvertisingManagementMapper advertisingManagementMapper;
	
	public void save(AdvertisingInfo advertisingInfo){
		advertisingInfoMapper.insert(advertisingInfo);
	}

    public void update(AdvertisingInfo advertisingInfo){
		advertisingInfoMapper.update(advertisingInfo);
	}
	
    public void delete(java.lang.Long id){
		advertisingInfoMapper.delete(id);
	}

	@Override
	public void delete(long[] ids) {
		for(long id:ids){
			delete(id);
		}
	}

	@Transactional(readOnly=true)
    public AdvertisingInfo getById(java.lang.Long id){
		return advertisingInfoMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<AdvertisingInfo> getPage(Map params, PageBounds rowBounds){
        PageList list = advertisingInfoMapper.findPage(params, rowBounds);
		return new Page<AdvertisingInfo>(list,list.getPagination());
	}

	@Transactional(readOnly=true)
	public List<AdvertisingInfo> getListByColumnsAndTypes(Integer columns, Integer types) {
		Map<String,Object> params=new HashMap<>();
		params.put("columns",columns);
		params.put("types",types);
		return advertisingInfoMapper.findListByColumns(params);
	}

}
