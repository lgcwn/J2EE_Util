/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.framework.util.ObjectUtils;
import com.up72.hq.constant.Cnst;
import com.up72.hq.dto.resp.NewsResp;
import com.up72.hq.dto.resp.QuizResp;
import com.up72.hq.model.*;
import com.up72.hq.dao.*;
import com.up72.hq.service.*;

import com.up72.framework.util.page.PageBounds;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.up72.hq.service.IQuizService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 竞猜DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class QuizServiceImpl implements  IQuizService{
	
	@Autowired
	private QuizMapper quizMapper;
	@Autowired
	private QuizQuestioServiceImpl quizQuestioServiceImpl;

	public void save(Quiz quiz){
		quiz.setAddTime(Cnst.getCurTime());
		quizMapper.insert(quiz);
	}

    public void update(Quiz quiz){
		quizMapper.update(quiz);
	}

	@Override
	public void update(Quiz quiz, long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			Quiz db = quizMapper.findById(ids[i]);
			if (ObjectUtils.isNotEmpty(quiz.getAutoOrManually())) {
				db.setAutoOrManually(quiz.getAutoOrManually());
			}
			if (ObjectUtils.isNotEmpty(quiz.getStatus())) {
				db.setStatus(quiz.getStatus());
			}
			update(db);
		}
	}

	public void delete(java.lang.Long id){
		quizMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public Quiz getById(java.lang.Long id){
		return quizMapper.findById(id);
	}

	@Override
	public QuizResp getByIdResp(Long id) {
		return quizMapper.findByIdResp(id);
	}

	@Transactional(readOnly=true)
    public Page<QuizResp> getPage(Map params, PageBounds rowBounds){
        PageList list = quizMapper.findPage(params, rowBounds);
		return new Page<QuizResp>(list,list.getPagination());
	}

	@Override
	public Page<Quiz> getPages(Map params, PageBounds rowBounds) {
		PageList list = quizMapper.findPages(params, rowBounds);
		return new Page<Quiz>(list, list.getPagination());
	}

	@Override
	public List<QuizResp> getListBy4(java.lang.Long type) {
		return quizMapper.findListBy4(type);
	}

	public List<QuizResp> getListByArenaId(Long type) {
		return quizMapper.findListByArenaId(type);
	}
	public Page<QuizResp> getResp(Map params, PageBounds rowBounds) {
		PageList list = quizMapper.findResp(params, rowBounds);
		return new Page<QuizResp>(list, list.getPagination());
	}
	@Override
	public List<QuizResp> getHot() {
		return quizMapper.findHot();
	}

	/**
	 * 每1分钟执行定时任务
	 * 检查时间，到点自动发布
	 *//*
	@Scheduled(cron="0 0/1 * * * ? ")
	public void executeTimedTask() {
		try {
			//1.更改竞猜状态
			Long nowTime= Cnst.getCurTime();
			Map<String,Object> params=new HashMap<String,Object>();
			PageList<Quiz>  quizList =quizMapper.findPages(params, new PageBounds(1, Integer.MAX_VALUE));
			if(ObjectUtils.isNotEmpty(quizList) || quizList.size()>0){
				for (int i = 0; i < quizList.size(); i++) {
					Quiz quiz =quizList.get(i);
					Long startTime=quiz.getStartTime();
					Long endTime=quiz.getEndTime();

					List<QuizQuestio> quizQuestios =  quizQuestioServiceImpl.getByQuizId(quiz.getId());

					if(startTime<nowTime&&nowTime<endTime){
						quiz.setStatus(Cnst.HqNews.YES_STATUS);
						for(QuizQuestio quizQuestio:quizQuestios){
							quizQuestio.setStatus(Cnst.GetQuizQuestion.START);
							quizQuestioServiceImpl.update(quizQuestio);
						}
					}else if (nowTime>endTime){
						quiz.setStatus(Cnst.HqNews.NOT_STATUS);
					}
					quizMapper.update(quiz);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}*/

}
