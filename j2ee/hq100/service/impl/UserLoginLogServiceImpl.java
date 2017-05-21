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

import com.up72.hq.service.IUserLoginLogService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 用户登录日志DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class UserLoginLogServiceImpl implements  IUserLoginLogService{
	
	@Autowired
	private UserLoginLogMapper userLoginLogMapper;
	
	public void save(UserLoginLog userLoginLog){
		userLoginLogMapper.insert(userLoginLog);
	}

    public void update(UserLoginLog userLoginLog){
		userLoginLogMapper.update(userLoginLog);
	}
	
    public void delete(java.lang.Long id){
		userLoginLogMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public UserLoginLog getById(java.lang.Long id){
		return userLoginLogMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<UserLoginLog> getPage(Map params, PageBounds rowBounds){
        PageList list = userLoginLogMapper.findPage(params, rowBounds);
		return new Page<UserLoginLog>(list,list.getPagination());
	}
	

}
