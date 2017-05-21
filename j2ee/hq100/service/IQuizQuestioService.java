/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.QuizQuestioResp;
import com.up72.hq.model.QuizQuestio;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 竞猜问题接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IQuizQuestioService {
	
	void save(QuizQuestio quizQuestio);

	void save(QuizQuestio quizQuestio,String[] name1,String[] odds);

    void update(QuizQuestio quizQuestio);
    void chooseSelect(QuizQuestio quizQuestio);
    void update(QuizQuestio quizQuestio,long[] ids,String[] name1,String[] odds);

	void delete(java.lang.Long id);
	
    QuizQuestio getById(java.lang.Long id);
    QuizQuestioResp getByIdResp(java.lang.Long id);

    Page<QuizQuestioResp> getPage(Map params, PageBounds rowBounds);

    List<QuizQuestio> getByQuizId(java.lang.Long quizId);
    List<QuizQuestioResp> getByQuizIdResp(java.lang.Long quizId);

}
