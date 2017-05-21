/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2015
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.CatRankingResp;
import com.up72.hq.dto.resp.CatResp;
import com.up72.hq.dto.resp.CatTwoLevelResp;
import com.up72.hq.dto.resp.CatUsingCntResp;
import com.up72.hq.model.Cat;
import com.up72.framework.util.page.Page;
import com.up72.framework.util.page.PageBounds;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 分类接口
 *
 * @author up72
 * @version 1.0
 * @since 1.0
 */
public interface ICatService {

    Cat save(Cat Cat);

    void update(Cat Cat);

    void delete(Long id);

    Cat getById(Long id);

    CatResp getRespById(Long id);

    Page<Cat> getPage(Map params, PageBounds rowBounds);

    Page<CatResp> getPageResp(Map params, PageBounds rowBounds);

    List<CatResp> getList(Integer type);

    List<CatResp> getListById(Long id);

    List<CatResp> getListByParentId(Long parentId);

    int cntSub(Long parentId);

    CatUsingCntResp getUsingCnt(Long id);

    CatTwoLevelResp getTowLevelCat(Long id);

    List<CatTwoLevelResp> getTowLevelList();

    List<CatUsingCntResp> getTop10OfToday();

    List<Cat> getLeafList();

    /**
     * 根据ids查找数据
     * @param ids
     */
    public List<CatResp> getAndOrderByIds(String ids);

    /**
     * 根据名称搜索
     * @param searchName
     * @return
     */
    public List<CatResp> getByName(String searchName);
    /**
     * 获取用户关注的商品所拥有的分类
     * @param request
     * @return
     */
    public List<Cat> getMemberCatList(HttpServletRequest request);

    //根据不同分类获取数据
    List<Cat> getListByParams(Map params);

    List<CatRankingResp> getListByCatId(Long catId,Integer type);
    List<CatRankingResp> getListByCat(Integer type);
}
