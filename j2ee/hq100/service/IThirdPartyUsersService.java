/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.ThirdPartyUsers;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 第三方用户接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IThirdPartyUsersService {
	
	void save(ThirdPartyUsers thirdPartyUsers);

    void update(ThirdPartyUsers thirdPartyUsers);
	
	void delete(java.lang.Long id);
	
    ThirdPartyUsers getById(java.lang.Long id);

    ThirdPartyUsers getParam(Map params);

    Page<ThirdPartyUsers> getPage(Map params, PageBounds rowBounds);
	

}
