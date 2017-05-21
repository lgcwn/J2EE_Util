/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.hq.constant.Cnst;
import com.up72.hq.dto.resp.MemberMessageResp;
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

import com.up72.hq.service.IMemberMessageService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 用户消息DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class MemberMessageServiceImpl implements  IMemberMessageService{
	
	@Autowired
	private MemberMessageMapper memberMessageMapper;
	@Autowired
	private MemberServiceImpl memberServiceImpl;

	public void save(MemberMessage memberMessage){

		memberMessage.setAddTime(Cnst.getCurTime());
		memberMessage.setStatus(Cnst.MemberMessage.NOT);
		memberMessage.setIsDel(Cnst.MemberMessage.NOT_DEL);
		memberMessage.setType(Cnst.MemberMessage.SYS);//只能为1
		memberMessageMapper.insert(memberMessage);
	}
	public void saveAll(MemberMessage memberMessage){

		List<Member> members = memberServiceImpl.getAll();
		for(Member member : members){
			MemberMessage memberMessage1 = new MemberMessage();
			memberMessage1.setContent(memberMessage.getContent());
			memberMessage1.setHqMemberId(member.getId());
			memberMessage1.setAddTime(Cnst.getCurTime());
			memberMessage1.setTitle(memberMessage.getTitle());
			memberMessage1.setStatus(Cnst.MemberMessage.NOT);
			memberMessage1.setIsDel(Cnst.MemberMessage.NOT_DEL);
			memberMessage1.setType(Cnst.MemberMessage.SYS);//只能为1
			memberMessageMapper.insert(memberMessage1);
		}
	}
    public void update(MemberMessage memberMessage){
		memberMessageMapper.update(memberMessage);
	}

	@Override
	public void update(long[] ids) {
		for(Long id : ids){
			MemberMessage memberMessage = getById(id);
			memberMessage.setStatus(Cnst.MemberMessage.YES);
			update(memberMessage);
		}
	}

	public void delete(java.lang.Long id){
		memberMessageMapper.delete(id);
	}

	@Override
	public void delete(long[] ids) {
		for(Long id : ids){
			MemberMessage memberMessage = getById(id);
			memberMessage.setIsDel(Cnst.MemberMessage.YES_DEL);
			update(memberMessage);
		}
	}

	@Transactional(readOnly=true)
    public MemberMessage getById(java.lang.Long id){
		return memberMessageMapper.findById(id);
	}

	@Override
	public MemberMessageResp getByIdResp(Long id) {
		return memberMessageMapper.findByIdResp(id);
	}

	@Transactional(readOnly=true)
    public Page<MemberMessageResp> getPage(Map params, PageBounds rowBounds){
        PageList list = memberMessageMapper.findPage(params, rowBounds);
		return new Page<MemberMessageResp>(list,list.getPagination());
	}

	@Override
	public MemberMessageResp getByMemberId(Long memberId) {
		return memberMessageMapper.findByMemberId(memberId);
	}


}
