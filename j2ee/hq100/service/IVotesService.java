/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.req.VotesReq;
import com.up72.hq.dto.resp.VotesResp;
import com.up72.hq.model.Votes;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 我的投票接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IVotesService {
	
	void save(Votes votes);

    void update(Votes votes);
	
	void delete(java.lang.Long id);
	
    Votes getById(java.lang.Long id);

    Page<Votes> getPage(Map params, PageBounds rowBounds);

    boolean isAddVotes(Long memberId,Long sourceId,Integer type);

    Votes getLastVotes(Long sourceId,Integer type);

    VotesResp statVotes(Map params,Integer type);

    Long statHighestVotes(Map params);

    Long statLowestVotes(Map params);

    List<Votes> getYearList(Map params);

}
