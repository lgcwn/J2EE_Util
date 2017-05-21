/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.hq.dto.resp.RoleManagerResp;
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

import com.up72.hq.service.IRoleManagerService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 角色管理DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class RoleManagerServiceImpl implements  IRoleManagerService{
	
	@Autowired
	private RoleManagerMapper roleManagerMapper;
	
	public void save(RoleManager roleManager){
		roleManagerMapper.insert(roleManager);
	}

    public void update(RoleManager roleManager){
		roleManagerMapper.update(roleManager);
	}
	
    public void delete(java.lang.Long id){
		roleManagerMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public RoleManagerResp getById(java.lang.Long id){
		return roleManagerMapper.findById(id);
	}

	@Override
	public RoleManager getId(Long id) {
		return roleManagerMapper.findId(id);
	}

	@Transactional(readOnly=true)
    public Page<RoleManagerResp> getPage(Map params, PageBounds rowBounds){
        PageList list = roleManagerMapper.findPage(params, rowBounds);
		return new Page<RoleManagerResp>(list,list.getPagination());
	}

}
