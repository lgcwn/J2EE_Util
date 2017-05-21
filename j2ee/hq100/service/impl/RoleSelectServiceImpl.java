/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.framework.util.ObjectUtils;
import com.up72.hq.constant.Cnst;
import com.up72.hq.dto.resp.ChooseResp;
import com.up72.hq.dto.resp.RoleSelectResp;
import com.up72.hq.model.*;
import com.up72.hq.dao.*;
import com.up72.hq.service.*;

import com.up72.framework.util.page.PageBounds;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.up72.hq.service.IRoleSelectService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 角色选拔DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class RoleSelectServiceImpl implements  IRoleSelectService{
	
	@Autowired
	private RoleSelectMapper roleSelectMapper;
	@Autowired
	private RuleManagerOrderMapper ruleManagerOrderMapper;
	@Autowired
	private RoleManagerMapper roleManagerMapper;

	public void save(RoleSelect roleSelect){
		roleSelectMapper.insert(roleSelect);
	}

    public void update(RoleSelect roleSelect){
		roleSelectMapper.update(roleSelect);
	}
	
    public void delete(java.lang.Long id){
		roleSelectMapper.delete(id);
	}

	@Override
	public void delRoleSelect(Long id) {
		ruleManagerOrderMapper.deleteByRoleSelectId(id);
		roleManagerMapper.deleteByRoleSelectId(id);
		roleSelectMapper.delete(id);
	}

	@Transactional(readOnly=true)
    public RoleSelectResp getById(java.lang.Long id){
		return roleSelectMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<RoleSelectResp> getPage(Map params, PageBounds rowBounds){
        PageList list = roleSelectMapper.findPage(params, rowBounds);
		return new Page<RoleSelectResp>(list,list.getPagination());
	}


	

}
