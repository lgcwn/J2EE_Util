/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.hq.dto.resp.FavouriteResp;
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

import com.up72.hq.service.IFavouriteService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 我的收藏DAO实现
 *
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class FavouriteServiceImpl implements IFavouriteService {

    @Autowired
    private FavouriteMapper favouriteMapper;

    public void save(Favourite favourite) {
        favouriteMapper.insert(favourite);
    }

    public void update(Favourite favourite) {
        favouriteMapper.update(favourite);
    }

    public void delete(java.lang.Long id) {
        favouriteMapper.delete(id);
    }

    @Transactional(readOnly = true)
    public Favourite getById(java.lang.Long id) {
        return favouriteMapper.findById(id);
    }

    @Override
    public Favourite getByParam(Map params) {
        return favouriteMapper.findByParam(params);
    }

    @Transactional(readOnly = true)
    public Page<Favourite> getPage(Map params, PageBounds rowBounds) {
        PageList list = favouriteMapper.findPage(params, rowBounds);
        return new Page<Favourite>(list, list.getPagination());
    }

    @Override
    public Page<FavouriteResp> getPageCrowd(Map params, PageBounds rowBounds) {
        PageList list = favouriteMapper.findPageCrowd(params, rowBounds);
        return new Page<FavouriteResp>(list, list.getPagination());
    }

    @Override
    public Page<FavouriteResp> getPageChoose(Map params, PageBounds rowBounds) {
        PageList list = favouriteMapper.findPageChoose(params, rowBounds);
        return new Page<FavouriteResp>(list, list.getPagination());
    }

    @Override
    public Page<FavouriteResp> getPageVideo(Map params, PageBounds rowBounds) {
        PageList list = favouriteMapper.findPageVideo(params, rowBounds);
        return new Page<FavouriteResp>(list, list.getPagination());
    }

    @Override
    public Page<FavouriteResp> getPageNew(Map params, PageBounds rowBounds) {
        PageList list = favouriteMapper.findPageNew(params, rowBounds);
        return new Page<FavouriteResp>(list, list.getPagination());
    }

    @Override
    public Page<FavouriteResp> getPageStarRanking(Map params, PageBounds rowBounds) {
        PageList list = favouriteMapper.findPageStarRanking(params, rowBounds);
        return new Page<FavouriteResp>(list, list.getPagination());
    }

    @Override
    public Page<FavouriteResp> getPageRoleSelect(Map params, PageBounds rowBounds) {
        PageList list = favouriteMapper.findPageRoleSelect(params, rowBounds);
        return new Page<FavouriteResp>(list, list.getPagination());
    }

    @Transactional(readOnly = true)
    public int findFavouriteCountByNewId(Long newsId) {   //根据新闻主键Id 查询 该新闻被收藏的次数
        return favouriteMapper.findFavouriteCountByNewId(newsId);
    }


}
