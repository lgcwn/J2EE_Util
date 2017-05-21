/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.framework.util.page.Page;
import com.up72.framework.util.page.PageBounds;
import com.up72.hq.dto.resp.ReviewResp;
import com.up72.hq.model.Review;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 评论接口
 *
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IReviewService {

    void save(Review review);

    void update(Review review);

    void delete(java.lang.Long id);

    Review getById(java.lang.Long id);

    Page<Review> getPage(Map params, PageBounds rowBounds);

    Page<ReviewResp> getPageResp(Map params, PageBounds rowBounds);

    Page<ReviewResp> getReviewRespPage(Map params, PageBounds rowBounds);

    int findViewCountBySourceId(Map params);  //根据咨询主键Id和资讯分类Id查询得到该新闻被评论的次数

    ReviewResp getByIdResp(java.lang.Long id);

    void getReview(long catId,long sourceId,HttpServletRequest request);
}
