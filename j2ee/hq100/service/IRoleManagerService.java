/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.RoleManagerResp;
import com.up72.hq.model.RoleManager;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 角色管理接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IRoleManagerService {
	
	void save(RoleManager roleManager);

    void update(RoleManager roleManager);
	
	void delete(java.lang.Long id);
	
    RoleManagerResp getById(java.lang.Long id);
    RoleManager getId(java.lang.Long id);

    Page<RoleManagerResp> getPage(Map params, PageBounds rowBounds);
	

}
