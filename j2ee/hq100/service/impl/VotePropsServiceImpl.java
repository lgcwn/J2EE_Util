/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.framework.util.page.*;
import com.up72.hq.model.*;
import com.up72.hq.dao.*;
import com.up72.hq.service.*;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.up72.hq.service.IVotePropsService;

/**
 * 投票道具DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class VotePropsServiceImpl implements  IVotePropsService{
	
	@Autowired
	private VotePropsMapper votePropsMapper;
	
	public void save(VoteProps voteProps){
		votePropsMapper.insert(voteProps);
	}

    public void update(VoteProps voteProps){
		votePropsMapper.update(voteProps);
	}
	
    public void delete(java.lang.Long id){
		votePropsMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public VoteProps getById(java.lang.Long id){
		return votePropsMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<VoteProps> getPage(Map params, PageBounds rowBounds){
        PageList list = votePropsMapper.findPage(params, rowBounds);
		return new Page<VoteProps>(list,list.getPagination());
	}

	@Transactional
	public List<VoteProps> getListByType(Integer type) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("type", type);
		params.put("enable", 1);
		PageBounds pageBounds = new PageBounds(1,Integer.MAX_VALUE);
		pageBounds.setOrders(com.up72.framework.util.page.Order.formString("SORT_ID.DESC"));
		Page<VoteProps> votePropss =getPage(params, pageBounds);
		return  votePropss.getResult();
	}


}
