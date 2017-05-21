/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.TagResp;
import com.up72.hq.model.Cat;
import com.up72.hq.model.Tag;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 标签接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface ITagService {

    Tag save(Tag Tag);

    void update(Tag Tag);

    void delete(Long id);

    Tag getById(Long id);

    Page<Tag> getPage(Map params, PageBounds rowBounds);

    Page<TagResp> getRespPage(Map params, PageBounds rowBounds);

    int cntUsing(Long tagId);

    TagResp getRespById(Long id);

    List<Tag> getListByCat(Cat cat);
	

}
