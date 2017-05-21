/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.req.ChooseReq;
import com.up72.hq.dto.resp.ChooseResp;
import com.up72.hq.model.Choose;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * (评选之最)接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IChooseService {
	
	void save(Choose choose);

    void update(Choose choose);
	
	void delete(java.lang.Long id);
	
    ChooseResp getById(java.lang.Long id);

    Page<ChooseResp> getPage(Map params, PageBounds rowBounds);

    void saveChooseAndList(ChooseReq chooseReq);
	

}
