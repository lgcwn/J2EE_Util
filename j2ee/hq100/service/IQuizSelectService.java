/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.QuizSelect;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 竞猜选项接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IQuizSelectService {
	
	void save(QuizSelect quizSelect);

    void update(QuizSelect quizSelect);
	
	void delete(java.lang.Long id);
	
    QuizSelect getById(java.lang.Long id);

    Page<QuizSelect> getPage(Map params, PageBounds rowBounds);

    List<QuizSelect> getList(Map params);

}
