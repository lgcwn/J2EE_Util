/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.UserLoginLog;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 用户登录日志接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IUserLoginLogService {
	
	void save(UserLoginLog userLoginLog);

    void update(UserLoginLog userLoginLog);
	
	void delete(java.lang.Long id);
	
    UserLoginLog getById(java.lang.Long id);

    Page<UserLoginLog> getPage(Map params, PageBounds rowBounds);
	

}
