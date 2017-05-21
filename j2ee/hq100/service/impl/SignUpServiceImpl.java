/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.framework.util.ObjectUtils;
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

import com.up72.hq.service.ISignUpService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * APP注册DAO实现
 * 
 * @author up72
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class SignUpServiceImpl implements  ISignUpService{
	
	@Autowired
	private SignUpMapper signUpMapper;
	
	public void save(SignUp signUp){
		signUpMapper.insert(signUp);
	}

    public void update(SignUp signUp){
		signUpMapper.update(signUp);
	}

	@Override
	public void update(SignUp signUp, long[] ids) {
		for (long id : ids) {
			SignUp db = signUpMapper.findById(id);
			if (ObjectUtils.isNotEmpty(signUp.getIsDelete())) {
				db.setIsDelete(signUp.getIsDelete());
			}
			update(db);
		}
	}

	public void delete(java.lang.Long id){
		signUpMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public SignUp getById(java.lang.Long id){
		return signUpMapper.findById(id);
	}

	@Transactional(readOnly=true)
	public SignUp getByAppId(String appId) {
		return signUpMapper.findByAppId(appId);
	}

	@Transactional(readOnly=true)
    public Page<SignUp> getPage(Map params, PageBounds rowBounds){
        PageList list = signUpMapper.findPage(params, rowBounds);
		return new Page<SignUp>(list,list.getPagination());
	}
	

}
