/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.QuizResp;
import com.up72.hq.model.Quiz;

import com.up72.framework.util.page.PageBounds;

import java.util.List;
import java.util.Map;

import com.up72.framework.util.page.Page;


/**
 * 竞猜接口
 *
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IQuizService {

    void save(Quiz quiz);

    void update(Quiz quiz);

    void update(Quiz quiz,long[] ids);

    void delete(java.lang.Long id);

    Quiz getById(java.lang.Long id);

    QuizResp getByIdResp(java.lang.Long id);

    Page<QuizResp> getPage(Map params, PageBounds rowBounds);

    Page<Quiz> getPages(Map params, PageBounds rowBounds);

    Page<QuizResp> getResp(Map params, PageBounds rowBounds);

    List<QuizResp> getListBy4(java.lang.Long type);

    List<QuizResp> getListByArenaId(java.lang.Long type);

    List<QuizResp> getHot();


}
