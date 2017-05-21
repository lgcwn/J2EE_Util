/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.hq.constant.Cnst;
import com.up72.hq.dto.resp.QuizOrderResp;
import com.up72.hq.model.*;
import com.up72.hq.dao.*;
import com.up72.hq.service.*;

import com.up72.framework.util.page.PageBounds;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.up72.hq.service.IQuizOrderService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 竞猜订单DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class QuizOrderServiceImpl implements  IQuizOrderService{
	
	@Autowired
	private QuizOrderMapper quizOrderMapper;
	@Autowired
	private MemberMapper memberMapper;

	public void save(QuizOrder quizOrder){
		Member member = memberMapper.findById(quizOrder.getMemberId());
		member.setScore(member.getScore()-quizOrder.getQuizMoney());
		memberMapper.update(member);
		quizOrder.setAddTime(Cnst.getCurTime());
		quizOrderMapper.insert(quizOrder);
	}

    public void update(QuizOrder quizOrder){
		quizOrderMapper.update(quizOrder);
	}
	
    public void delete(java.lang.Long id){
		quizOrderMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public QuizOrder getById(java.lang.Long id){
		return quizOrderMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<QuizOrder> getPage(Map params, PageBounds rowBounds){
        PageList list = quizOrderMapper.findPage(params, rowBounds);
		return new Page<QuizOrder>(list,list.getPagination());
	}

	@Override
	public Page<QuizOrderResp> getPageResp(Map params, PageBounds rowBounds) {
		PageList list = quizOrderMapper.findPageResp(params, rowBounds);
		return new Page<QuizOrderResp>(list,list.getPagination());
	}

	@Override
	public List<QuizOrderResp> getAll(Map params) {
		return quizOrderMapper.findAll(params);
	}

	@Override
	public List<QuizOrderResp> getListCount(Map params) {
		return quizOrderMapper.findListCount(params);
	}


}
