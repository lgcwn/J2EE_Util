/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.Feedback;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 反馈建议接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IFeedbackService {
	
	void save(Feedback feedback);

    void update(Feedback feedback);
	
	void delete(java.lang.Long id);
	
    Feedback getById(java.lang.Long id);

    Page<Feedback> getPage(Map params, PageBounds rowBounds);
	

}
