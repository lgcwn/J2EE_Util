/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.alibaba.druid.sql.visitor.functions.If;
import com.up72.framework.util.ObjectUtils;
import com.up72.framework.util.page.*;
import com.up72.hq.dto.req.VotesReq;
import com.up72.hq.dto.resp.VotesResp;
import com.up72.hq.model.*;
import com.up72.hq.dao.*;
import com.up72.hq.service.*;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.up72.hq.service.IVotesService;

/**
 * 我的投票DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class VotesServiceImpl implements  IVotesService{
	
	@Autowired
	private VotesMapper votesMapper;
	
	public void save(Votes votes){
		votesMapper.insert(votes);
	}

    public void update(Votes votes){
		votesMapper.update(votes);
	}
	
    public void delete(java.lang.Long id){
		votesMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public Votes getById(java.lang.Long id){
		return votesMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<Votes> getPage(Map params, PageBounds rowBounds){
        PageList list = votesMapper.findPage(params, rowBounds);
		return new Page<Votes>(list,list.getPagination());
	}

	@Override
	public boolean isAddVotes(Long memberId,Long sourceId,Integer type) {
		Map<String,Object> params=new HashMap<>();
		params.put("type",type);
		params.put("memberId",memberId);
		params.put("sourceId",sourceId);
		List<Votes> votesList=getPage(params,new PageBounds(1,1)).getResult();
		if(ObjectUtils.isEmpty(votesList) || votesList.size()==0){
			return  true;
		}else{
			return false;
		}
	}

	@Transactional
	public Votes getLastVotes(Long sourceId, Integer type) {
		Map<String,Object> params=new HashMap<>();
		params.put("type",type);
		params.put("sourceId",sourceId);
		PageBounds pageBounds=new PageBounds(1,1);
		pageBounds.setOrders(com.up72.framework.util.page.Order.formString("ID.DESC"));
		List<Votes> votesList=getPage(params,pageBounds).getResult();
		return votesList.get(0);
	}

	@Transactional
	public VotesResp statVotes(Map params,Integer type) {
		Long highest=0L;//最大值
		Long lowest=0L;//最小值
		Long votesSum=0L;//总票数或收盘值
		Integer ranking=0;//排名
		VotesResp votesResp=new VotesResp();
		PageList<Votes> votesPageList=votesMapper.findPage(params,new PageBounds(1,Integer.MAX_VALUE));
		if (type==1){
			for (int i = 0; i <votesPageList.size() ; i++) {
				Votes votes=votesPageList.get(i);
				Integer number=votes.getNumber();
				votesSum=votesSum+number;
				if(number<0 && lowest==0){
					lowest=votesSum;
				}
				ranking=votes.getRanking();
				if(votesSum>highest){
					highest=votesSum;
				}else if(votesSum<lowest){
					lowest=votesSum;
				}
			}
		}else{
			highest=Long.valueOf(votesPageList.size());
			lowest=Long.valueOf(votesPageList.size());
			votesSum=highest;
		}
		votesResp.setHighest(highest);
		votesResp.setLowest(lowest);
		votesResp.setClose(votesSum);
		votesResp.setRanking(ranking);
		return votesResp;
	}

	@Transactional
	public Long statHighestVotes(Map params) {
		return votesMapper.statHighestVotes(params);
	}

	@Transactional
	public Long statLowestVotes(Map params) {
		return votesMapper.statLowestVotes(params);
	}

	@Transactional
	public List<Votes> getYearList(Map params) {
		return votesMapper.findYearList(params);
	}


}
