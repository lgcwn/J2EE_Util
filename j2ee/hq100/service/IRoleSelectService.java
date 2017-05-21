/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.RoleSelectResp;
import com.up72.hq.model.RoleSelect;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 角色选拔接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IRoleSelectService {
	
	void save(RoleSelect roleSelect);

    void update(RoleSelect roleSelect);
	
	void delete(java.lang.Long id);

    void delRoleSelect(java.lang.Long id);

    RoleSelectResp getById(java.lang.Long id);

    Page<RoleSelectResp> getPage(Map params, PageBounds rowBounds);
	

}
