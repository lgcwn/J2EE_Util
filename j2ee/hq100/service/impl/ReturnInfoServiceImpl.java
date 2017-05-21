/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.hq.dto.resp.ReturnInfoResp;
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

import com.up72.hq.service.IReturnInfoService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 回报信息DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class ReturnInfoServiceImpl implements  IReturnInfoService{
	
	@Autowired
	private ReturnInfoMapper returnInfoMapper;
	
	public void save(ReturnInfo returnInfo){
		returnInfoMapper.insert(returnInfo);
	}

    public void update(ReturnInfo returnInfo){
		returnInfoMapper.update(returnInfo);
	}
	
    public void delete(java.lang.Long id){
		returnInfoMapper.delete(id);
	}

	@Override
	public void deleteByCrowdId(Long id) {
		returnInfoMapper.deleteByCrowdId(id);
	}

	@Transactional(readOnly=true)
    public ReturnInfo getById(java.lang.Long id){
		return returnInfoMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<ReturnInfo> getPage(Map params, PageBounds rowBounds){
        PageList list = returnInfoMapper.findPage(params, rowBounds);
		return new Page<ReturnInfo>(list,list.getPagination());
	}

	@Override
	public List<ReturnInfoResp> getByCrowdId(Long crowdId) {
		return returnInfoMapper.findByCrowdId(crowdId);
	}


}
