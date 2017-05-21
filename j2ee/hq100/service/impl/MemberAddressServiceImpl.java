/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.framework.util.page.*;
import com.up72.hq.dto.resp.MemberAddressResp;
import com.up72.hq.model.*;
import com.up72.hq.dao.*;
import com.up72.hq.service.*;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.up72.hq.service.IMemberAddressService;

/**
 * 会员收货地址DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class MemberAddressServiceImpl implements  IMemberAddressService{
	
	@Autowired
	private MemberAddressMapper memberAddressMapper;
	
	public void save(MemberAddress memberAddress){
		memberAddressMapper.insert(memberAddress);
	}

    public void update(MemberAddress memberAddress){
		memberAddressMapper.update(memberAddress);
	}
	
    public void delete(java.lang.Long id){
		memberAddressMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public MemberAddressResp getById(java.lang.Long id){
		return memberAddressMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<MemberAddress> getPage(Map params, PageBounds rowBounds){
        PageList list = memberAddressMapper.findPage(params, rowBounds);
		return new Page<MemberAddress>(list,list.getPagination());
	}

	@Transactional(readOnly=true)
	public Page<MemberAddressResp> getRespPage(Map params, PageBounds rowBounds){
		PageList list = memberAddressMapper.findRespPage(params, rowBounds);
		return new Page<MemberAddressResp>(list,list.getPagination());
	}


	@Transactional(readOnly=true)
	public MemberAddressResp getMemberAddress(Long memberId) {
		return memberAddressMapper.findMemberAddress(memberId);
	}

	@Override
	public List<MemberAddressResp> getMemberAddressList(Long memberId) {
		Map<String,Object> params=new HashedMap();
		params.put("hqMemberId",memberId);
		PageBounds pageBounds=new PageBounds(1,Integer.MAX_VALUE);
		pageBounds.setOrders(com.up72.framework.util.page.Order.formString("ID.DESC"));
		PageList<MemberAddressResp> list = memberAddressMapper.findRespPage(params, pageBounds);
		return list;
	}
}
