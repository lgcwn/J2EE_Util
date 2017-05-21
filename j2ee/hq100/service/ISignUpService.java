/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.SignUp;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * APP注册接口
 * 
 * @author up72
 * @version 1.0
 * @since 1.0
 */
public interface ISignUpService {
	
	void save(SignUp signUp);

    void update(SignUp signUp);

    void update(SignUp signUp,long[] ids);

	void delete(java.lang.Long id);
	
    SignUp getById(java.lang.Long id);

    SignUp getByAppId (java.lang.String appId);

    Page<SignUp> getPage(Map params, PageBounds rowBounds);
	

}
