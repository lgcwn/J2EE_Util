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

import com.up72.hq.service.ISecurityService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 安全表DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class SecurityServiceImpl implements  ISecurityService{
	
	@Autowired
	private SecurityMapper securityMapper;
	
	public void save(Security security){
		securityMapper.insert(security);
	}

    public void update(Security security){
		securityMapper.update(security);
	}
	
    public void delete(java.lang.Long id){
		securityMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public Security getById(java.lang.Long id){
		return securityMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<Security> getPage(Map params, PageBounds rowBounds){
        PageList list = securityMapper.findPage(params, rowBounds);
		return new Page<Security>(list,list.getPagination());
	}
	

}
