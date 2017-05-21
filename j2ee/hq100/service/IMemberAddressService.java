/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.MemberAddressResp;
import com.up72.hq.model.MemberAddress;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 会员收货地址接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IMemberAddressService {
	
	void save(MemberAddress memberAddress);

    void update(MemberAddress memberAddress);
	
	void delete(java.lang.Long id);

    MemberAddressResp getById(java.lang.Long id);

    Page<MemberAddress> getPage(Map params, PageBounds rowBounds);

    Page<MemberAddressResp> getRespPage(Map params, PageBounds rowBounds);
    //获取会员默认地址
    MemberAddressResp getMemberAddress(java.lang.Long memberId);

    List<MemberAddressResp> getMemberAddressList(java.lang.Long memberId);

}
