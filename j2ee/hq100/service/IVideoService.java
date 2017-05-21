/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.VideoResp;
import com.up72.hq.model.Video;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * (视频接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IVideoService {
	
	void save(Video video);

    void update(Video video);
	
	void delete(java.lang.Long id);

    VideoResp getById(java.lang.Long id);

    Page<VideoResp> getPage(Map params, PageBounds rowBounds);
	
    List<Video> getHot();

    List<Video> getByCatId(java.lang.Long catId);
    //相关视频
    List<Video> getXgVideo(java.lang.Long catId);

    List<Video> getLimit9(Map params);
    //首页展示使用
    List<Video> getHome(java.lang.Long catId);
}
