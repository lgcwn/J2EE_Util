/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.FavouriteResp;
import com.up72.hq.model.Favourite;

import com.up72.framework.util.page.PageBounds;

import java.util.List;
import java.util.Map;

import com.up72.framework.util.page.Page;


/**
 * 我的收藏接口
 *
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IFavouriteService {

    void save(Favourite favourite);

    void update(Favourite favourite);

    void delete(java.lang.Long id);

    Favourite getById(java.lang.Long id);

    Favourite getByParam(Map params);

    Page<Favourite> getPage(Map params, PageBounds rowBounds);

    Page<FavouriteResp> getPageCrowd(Map params, PageBounds rowBounds);
    Page<FavouriteResp> getPageChoose(Map params, PageBounds rowBounds);
    Page<FavouriteResp> getPageVideo(Map params, PageBounds rowBounds);
    Page<FavouriteResp> getPageNew(Map params, PageBounds rowBounds);
    Page<FavouriteResp> getPageStarRanking(Map params, PageBounds rowBounds);
    Page<FavouriteResp> getPageRoleSelect(Map params, PageBounds rowBounds);

    int findFavouriteCountByNewId(Long newsId);

}
