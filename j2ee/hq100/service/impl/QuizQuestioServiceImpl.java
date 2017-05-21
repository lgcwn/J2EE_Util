/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.hq.constant.Cnst;
import com.up72.hq.dto.resp.MemberMessageResp;
import com.up72.hq.dto.resp.MemberResp;
import com.up72.hq.dto.resp.QuizOrderResp;
import com.up72.hq.dto.resp.QuizQuestioResp;
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

import com.up72.hq.service.IQuizQuestioService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 竞猜问题DAO实现
 *
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class QuizQuestioServiceImpl implements IQuizQuestioService {

    @Autowired
    private QuizQuestioMapper quizQuestioMapper;
    @Autowired
    private QuizSelectServiceImpl quizSelectServiceImpl;
    @Autowired
    private QuizOrderServiceImpl quizOrderServiceImpl;
    @Autowired
    private MemberServiceImpl memberServiceImpl;
    @Autowired
    private ScoreListServiceImpl scoreListServiceImpl;
    @Autowired
    private MemberMessageServiceImpl memberMessageServiceImpl;

    public void save(QuizQuestio quizQuestio) {

        quizQuestioMapper.insert(quizQuestio);
    }

    @Override
    public void save(QuizQuestio quizQuestio, String[] name1, String[] odds) {
        save(quizQuestio);
        if (name1.length > 0 && odds.length > 0) {
            for (int i = 0; i < name1.length; i++) {
                QuizSelect quizSelect = new QuizSelect();
                quizSelect.setHqQuizQuestioId(quizQuestio.getId());
                quizSelect.setName(name1[i]);
                quizSelect.setOdds(odds[i]);
                quizSelectServiceImpl.save(quizSelect);
            }
        }

    }

    public void update(QuizQuestio quizQuestio) {
        quizQuestioMapper.update(quizQuestio);
    }

    @Override
    public void chooseSelect(QuizQuestio quizQuestio) {
        update(quizQuestio);
        //获取这个问题下选择这个选项的用户
        Map<String ,Object> param = new HashMap<String,Object>();
        param.put("hqQuizSelectId",quizQuestio.getSelectId());
        param.put("hqQuizQuestioId",quizQuestio.getId());
        List<QuizOrderResp> quizOrders = quizOrderServiceImpl.getAll(param);
        for(QuizOrderResp quizOrder : quizOrders){
            //获取用户信息插入竞猜积分信息
            Member member = memberServiceImpl.getId(quizOrder.getMemberId());
            member.setScore(member.getScore()+quizOrder.getQuizReturn());
            //更新用户积分
            memberServiceImpl.update(member);
            //插入积分明细
            ScoreList scoreList = new ScoreList();
            scoreList.setMemberId(member.getId());
            scoreList.setType(Cnst.HqScore.HD);
            scoreList.setSourceType(Cnst.HqScore.JC);
            scoreList.setScore(quizOrder.getQuizReturn());
            scoreList.setRemark("竞猜【"+quizOrder.getTrueName()+"】，获得"+quizOrder.getQuizReturn()+"积分");
            scoreList.setAddTime(Cnst.getCurTime());
            scoreList.setIsOver(Cnst.HqScore.NOT_OVER);
            scoreList.setTbOrJf(Cnst.HqScore.JF);
            scoreListServiceImpl.save(scoreList);
            //插入用户通知
            MemberMessage memberMessage = new MemberMessage();
            memberMessage.setHqMemberId(member.getId());
            memberMessage.setTitle("竞猜成功获得积分");
            memberMessage.setContent("亲爱的会员：恭喜您参与【"+quizOrder.getQuizQuestioName()+"】竞猜赢得"+quizOrder.getQuizReturn()+"积分！");
            memberMessageServiceImpl.save(memberMessage);
            QuizOrder quizOrder1 = quizOrderServiceImpl.getById(quizOrder.getId());
            quizOrder1.setIsSuccess(1);//竞猜成功
            quizOrderServiceImpl.update(quizOrder1);
        }
    }


    @Override
    public void update(QuizQuestio quizQuestio, long[] ids, String[] name1, String[] odds) {
        update(quizQuestio);

        for (int i = 0; i < ids.length; i++) {
            if (ids[i] != -1) {
                QuizSelect quizSelect = quizSelectServiceImpl.getById(ids[i]);
                quizSelect.setName(name1[i]);
                quizSelect.setOdds(odds[i]);
                quizSelectServiceImpl.update(quizSelect);
            } else {
                QuizSelect quizSelect = new QuizSelect();
                quizSelect.setHqQuizQuestioId(quizQuestio.getId());
                quizSelect.setName(name1[i]);
                quizSelect.setOdds(odds[i]);
                quizSelectServiceImpl.save(quizSelect);
            }
        }


    }

    public void delete(java.lang.Long id) {
        quizQuestioMapper.delete(id);
    }

    @Transactional(readOnly = true)
    public QuizQuestio getById(java.lang.Long id) {
        return quizQuestioMapper.findById(id);
    }

    @Override
    public QuizQuestioResp getByIdResp(Long id) {
        return quizQuestioMapper.findByIdResp(id);
    }

    @Transactional(readOnly = true)
    public Page<QuizQuestioResp> getPage(Map params, PageBounds rowBounds) {
        PageList list = quizQuestioMapper.findPage(params, rowBounds);
        return new Page<QuizQuestioResp>(list, list.getPagination());
    }

    @Override
    public List<QuizQuestio> getByQuizId(Long quizId) {
        return quizQuestioMapper.findByQuizId(quizId);
    }

    @Override
    public List<QuizQuestioResp> getByQuizIdResp(Long quizId) {
        return quizQuestioMapper.findByQuizIdResp(quizId);
    }


}
