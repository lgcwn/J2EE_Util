/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.Menu;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 平台菜单配置接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IMenuService {
	
	void save(Menu menu);

    void update(Menu menu);
	
	void delete(java.lang.Long id);
	
    Menu getById(java.lang.Long id);

    Page<Menu> getPage(Map params, PageBounds rowBounds);
	

}
