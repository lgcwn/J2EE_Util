/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.MemberMessageResp;
import com.up72.hq.model.MemberMessage;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 用户消息接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IMemberMessageService {
	
	void save(MemberMessage memberMessage);
	void saveAll(MemberMessage memberMessage);

    void update(MemberMessage memberMessage);

    void update(long[] ids);

	void delete(java.lang.Long id);

	void delete(long[] ids);

    MemberMessage getById(java.lang.Long id);

    MemberMessageResp getByIdResp(java.lang.Long id);

    Page<MemberMessageResp> getPage(Map params, PageBounds rowBounds);

    //获取用户消息信息
    MemberMessageResp getByMemberId(java.lang.Long memberId);
}
