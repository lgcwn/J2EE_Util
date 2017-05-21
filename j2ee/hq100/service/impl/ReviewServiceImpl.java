/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import com.up72.framework.util.ParamUtils;
import com.up72.framework.util.page.Page;
import com.up72.framework.util.page.PageBounds;
import com.up72.framework.util.page.PageList;
import com.up72.hq.dao.ReviewMapper;
import com.up72.hq.model.Review;
import com.up72.hq.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.up72.hq.dto.resp.ReviewResp;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 评论DAO实现
 *
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class ReviewServiceImpl implements IReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    public void save(Review review) {
        reviewMapper.insert(review);
    }

    public void update(Review review) {
        reviewMapper.update(review);
    }

    public void delete(java.lang.Long id) {
        reviewMapper.delete(id);
    }

    @Transactional(readOnly = true)
    public Review getById(java.lang.Long id) {
        return reviewMapper.findById(id);
    }

    @Transactional(readOnly = true)
    public Page<Review> getPage(Map params, PageBounds rowBounds) {
        PageList list = reviewMapper.findPage(params, rowBounds);
        return new Page<Review>(list, list.getPagination());
    }

    public Page<ReviewResp> getPageResp(Map params, PageBounds rowBounds) {
        PageList list = reviewMapper.findPageResp(params, rowBounds);
        return new Page<ReviewResp>(list, list.getPagination());
    }

    @Transactional(readOnly = true)
    public Page<ReviewResp> getReviewRespPage(Map params, PageBounds rowBounds) {
        PageList list = reviewMapper.findReviewRespPage(params, rowBounds);
        return new Page<ReviewResp>(list, list.getPagination());
    }
    @Transactional(readOnly = true)
    public void getReview(long catId,long sourceId,HttpServletRequest request){
        Map<String,Object> params = new HashMap<>();
        params.put("catId",catId);
        params.put("sourceId",sourceId);
        PageBounds pageBounds = $getPageBounds(request,5);
        PageBounds pageBounds1 = $getPageBounds(request,105);
        Page<ReviewResp> reviews = getPageResp(params, pageBounds);

        Page<ReviewResp> reviews1 = getReviewRespPage(params, pageBounds1);
        int i=reviews.getPagination().getTotalCount();
        for(ReviewResp reviewResp:reviews1.getResult()){
            i+=Integer.valueOf(reviewResp.getCountReplay()).intValue();
        }


        request.setAttribute("allPeople",i);

        request.setAttribute("reviews",reviews.getResult());
        request.setAttribute("paginationReview", reviews.getPagination());
        request.setAttribute("reviewPageNumber", reviews.getPagination().getPageNumber());
        request.setAttribute("reviewPageCount", reviews.getPagination().getTotalCount());
        request.setAttribute("currentPageNumber", reviews.getPagination().getTotalPages());
        request.setAttribute("paramsReview", params);
    }
    /** 获取分页信息 */
    private static PageBounds $getPageBounds(ServletRequest request, int... pageSize) {
        int size = 15;
        if (pageSize.length > 0 && pageSize[0] > 0) {
            size = pageSize[0];
        }
        int pageNumber = $getParam(request, "pageNumber", 1);
        if (pageNumber < 1) pageNumber = 1;
        PageBounds pageBounds = new PageBounds(pageNumber, size);
        return pageBounds;
    }
    private static final int $getParam(ServletRequest request, String name,
                                       int defval) {
        String param = request.getParameter(name);
        int value = defval;
        if (param != null) {
            try {
                value = Integer.parseInt(param);
            } catch (NumberFormatException ignore) {
            }
        }
        return value;
    }
    public int findViewCountBySourceId(Map params) {  //根据新闻主键Id和新闻分类Id 查询得到该新闻被评论的次数
        return reviewMapper.findViewCountBySourceId(params);
    }

    @Override
    public ReviewResp getByIdResp(Long id) {
        return reviewMapper.findByIdResp(id);
    }
}
