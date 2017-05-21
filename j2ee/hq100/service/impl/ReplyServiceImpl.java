/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.hq.dto.resp.ReplyResp;
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

import com.up72.hq.service.IReplyService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 回复表DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class ReplyServiceImpl implements  IReplyService{
	
	@Autowired
	private ReplyMapper replyMapper;
	
	public void save(Reply reply){
		replyMapper.insert(reply);
	}

    public void update(Reply reply){
		replyMapper.update(reply);
	}
	
    public void delete(java.lang.Long id){
		replyMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public Reply getById(java.lang.Long id){
		return replyMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<ReplyResp> getPage(Map params, PageBounds rowBounds){
        PageList list = replyMapper.findPage(params, rowBounds);
		return new Page<ReplyResp>(list,list.getPagination());
	}
	

}
