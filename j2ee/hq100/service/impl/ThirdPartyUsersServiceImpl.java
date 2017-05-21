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

import com.up72.hq.service.IThirdPartyUsersService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 第三方用户DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class ThirdPartyUsersServiceImpl implements  IThirdPartyUsersService{
	
	@Autowired
	private ThirdPartyUsersMapper thirdPartyUsersMapper;
	
	public void save(ThirdPartyUsers thirdPartyUsers){
		thirdPartyUsersMapper.insert(thirdPartyUsers);
	}

    public void update(ThirdPartyUsers thirdPartyUsers){
		thirdPartyUsersMapper.update(thirdPartyUsers);
	}
	
    public void delete(java.lang.Long id){
		thirdPartyUsersMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public ThirdPartyUsers getById(java.lang.Long id){
		return thirdPartyUsersMapper.findById(id);
	}

	@Override
	public ThirdPartyUsers getParam(Map params) {
		return thirdPartyUsersMapper.findParam(params);
	}

	@Transactional(readOnly=true)
    public Page<ThirdPartyUsers> getPage(Map params, PageBounds rowBounds){
        PageList list = thirdPartyUsersMapper.findPage(params, rowBounds);
		return new Page<ThirdPartyUsers>(list,list.getPagination());
	}
	

}
