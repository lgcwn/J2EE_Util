/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.framework.util.page.Page;
import com.up72.framework.util.page.PageBounds;
import com.up72.hq.dto.resp.NoticeResp;
import com.up72.hq.model.Notice;

import java.util.Map;


/**
 * 活动，公告，案例接口
 * 
 * @author up72
 * @version 1.0
 * @since 1.0
 */
public interface INoticeService {
	
	void save(Notice notice);

    void update(Notice notice);
	
	void delete(Long id);

    Notice getById(Long id);

    Page<NoticeResp> getPage(Map params, PageBounds rowBounds);

    Notice  getByType(Integer type);
}
