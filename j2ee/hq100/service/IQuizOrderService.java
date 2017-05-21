/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.QuizOrderResp;
import com.up72.hq.model.QuizOrder;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 竞猜订单接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IQuizOrderService {
	
	void save(QuizOrder quizOrder);

    void update(QuizOrder quizOrder);
	
	void delete(java.lang.Long id);
	
    QuizOrder getById(java.lang.Long id);

    Page<QuizOrder> getPage(Map params, PageBounds rowBounds);
    Page<QuizOrderResp> getPageResp(Map params, PageBounds rowBounds);

    List<QuizOrderResp> getAll(Map params);

    List<QuizOrderResp> getListCount(Map params);
}
