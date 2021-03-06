/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.Security;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 安全表接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface ISecurityService {
	
	void save(Security security);

    void update(Security security);
	
	void delete(java.lang.Long id);
	
    Security getById(java.lang.Long id);

    Page<Security> getPage(Map params, PageBounds rowBounds);
	

}
