/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.RefundRequest;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;

import javax.servlet.http.HttpServletRequest;


/**
 * 退款/换货申请接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IRefundRequestService {
	
	void save(RefundRequest refundRequest);

    void update(RefundRequest refundRequest);
	
	void delete(java.lang.Long id);
	
    RefundRequest getById(java.lang.Long id);

    Page<RefundRequest> getPage(Map params, PageBounds rowBounds);

    /**
     * 获取退换货商品订单
     * @author liuguicheng
     * @param map
     * @return
     */
    RefundRequest getByOrderIdGoodsId(Map map);

    /**
     *
     * 新增或修改 退换货订单
     * *@author liuguicheng
     * @param request
     * @param dhsRefundRequest
     * @return
     */
    Map<String,Object>  doAddOrEdit(HttpServletRequest request, RefundRequest dhsRefundRequest);

    /**
     * 根据退货单号查找
     * @param orderRefundSn
     * @return
     */
    RefundRequest getByReturnSn(String orderRefundSn);

    Integer getMemberOrderCnt(Map map);

    /**
     *
     * 退换货详情
     *
     * @author liguuicheng
     * @param refundRequestId
     * @return
     */
     RefundRequest getRefundRequestDetail(Long refundRequestId);

    /***
     * 客服处理
     * @param model
     * @return
     */
    Boolean customerServiceProcessing(RefundRequest model);


}
