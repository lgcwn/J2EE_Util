/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.CrowdResp;
import com.up72.hq.model.Crowd;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 众筹接口
 * 
 * @author liuguicheng
 * @version 1.02
 * @since 1.0
 */
public interface ICrowdService {
	
	void save(Crowd crowd);

    void update(Crowd crowd);
	
	void delete(java.lang.Long id);

	void delete(long[] ids);

    Crowd getById(java.lang.Long id);

    CrowdResp getRespById(java.lang.Long id);

    Page<CrowdResp> getPage(Map params, PageBounds rowBounds);

    List<CrowdResp> getList3(java.lang.Long catId);

    List<CrowdResp> getHome();
    //获取热门信息
    List<CrowdResp> getHoTList(java.lang.Long catId);
}
