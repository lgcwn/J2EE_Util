/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

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

import com.up72.hq.service.IFeedbackService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 反馈建议DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class FeedbackServiceImpl implements  IFeedbackService{
	
	@Autowired
	private FeedbackMapper feedbackMapper;
	
	public void save(Feedback feedback){
		feedbackMapper.insert(feedback);
	}

    public void update(Feedback feedback){
		feedbackMapper.update(feedback);
	}
	
    public void delete(java.lang.Long id){
		feedbackMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public Feedback getById(java.lang.Long id){
		return feedbackMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<Feedback> getPage(Map params, PageBounds rowBounds){
        PageList list = feedbackMapper.findPage(params, rowBounds);
		return new Page<Feedback>(list,list.getPagination());
	}
	

}
