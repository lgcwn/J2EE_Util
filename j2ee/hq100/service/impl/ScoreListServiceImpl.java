/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.hq.dto.resp.ScoreRespListResp;
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

import com.up72.hq.service.IScoreListService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 积分明细DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class ScoreListServiceImpl implements  IScoreListService{
	
	@Autowired
	private ScoreListMapper scoreListMapper;
	
	public void save(ScoreList scoreList){
		scoreListMapper.insert(scoreList);
	}

    public void update(ScoreList scoreList){
		scoreListMapper.update(scoreList);
	}
	
    public void delete(java.lang.Long id){
		scoreListMapper.delete(id);
	}

	@Override
	public void delete(long[] ids) {
		for(long id : ids){
			delete(id);
		}
	}

	@Transactional(readOnly=true)
    public ScoreList getById(java.lang.Long id){
		return scoreListMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<ScoreList> getPage(Map params, PageBounds rowBounds){
        PageList list = scoreListMapper.findPage(params, rowBounds);
		return new Page<ScoreList>(list,list.getPagination());
	}

	@Transactional(readOnly=true)
	public Page<ScoreRespListResp> getRespPage(Map params, PageBounds rowBounds){
		PageList list = scoreListMapper.findRespPage(params, rowBounds);
		return new Page<ScoreRespListResp>(list,list.getPagination());
	}
	

}
