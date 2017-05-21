/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.framework.util.ObjectUtils;
import com.up72.framework.util.holder.ApplicationContextHolder;
import com.up72.hq.constant.Cnst;
import com.up72.hq.dto.req.ChooseReq;
import com.up72.hq.dto.resp.ChooseListResp;
import com.up72.hq.dto.resp.ChooseResp;
import com.up72.hq.dto.resp.MemberResp;
import com.up72.hq.model.*;
import com.up72.hq.dao.*;
import com.up72.hq.service.*;

import com.up72.framework.util.page.PageBounds;

import com.up72.hq.utils.HqUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.up72.hq.service.IChooseService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * (评选之最)DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class ChooseServiceImpl implements  IChooseService{

	private  static  final Logger logger=Logger.getLogger(ChooseServiceImpl.class);
	
	@Autowired
	private ChooseMapper chooseMapper;
	@Autowired
	private ChooseListMapper chooseListMapper;

	public void save(Choose choose){
		chooseMapper.insert(choose);
	}

    public void update(Choose choose){
		chooseMapper.update(choose);
	}
	
    public void delete(java.lang.Long id){
		chooseMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public ChooseResp getById(java.lang.Long id){
		return chooseMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<ChooseResp> getPage(Map params, PageBounds rowBounds){
        PageList<ChooseResp> list = chooseMapper.findPage(params, rowBounds);
		return new Page<ChooseResp>(list,list.getPagination());
	}

	/**
	 * 保存评选之最或时事评选数据，同时保存相对应的名单数据
	 * @param chooseReq
	 */
    public void saveChooseAndList(ChooseReq chooseReq){
		save(chooseReq);
		String [] imgsArr=chooseReq.getImgsArr();
		String [] chooseNameArr=chooseReq.getChooseNameArr();
		String [] virtualVotesArr=chooseReq.getVirtualVotesArr();
		String [] introsArr=chooseReq.getIntrosArr();
		if(StringUtils.isNotBlank(imgsArr[0]) && StringUtils.isNotBlank(chooseNameArr[0]) &&
			StringUtils.isNotBlank(virtualVotesArr[0]) && StringUtils.isNotBlank(introsArr[0])){
			for (int i = 0; i < imgsArr.length ; i++) {
				ChooseList chooseList=new ChooseList();
				chooseList.setHqChooseId(chooseReq.getId());
				chooseList.setImg(imgsArr[i]);
				chooseList.setAddTime(Cnst.getCurTime());
				chooseList.setName(chooseNameArr[i]);
				chooseList.setVirtualVotes(Long.valueOf(virtualVotesArr[i]));
				chooseList.setRealVotes(0L);
				chooseList.setContent(introsArr[i]);
				chooseListMapper.insert(chooseList);
			}
		}
	}
}
