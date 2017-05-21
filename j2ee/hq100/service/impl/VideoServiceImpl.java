/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.framework.util.ObjectUtils;
import com.up72.hq.constant.Cnst;
import com.up72.hq.dto.resp.VideoResp;
import com.up72.hq.model.*;
import com.up72.hq.dao.*;
import com.up72.hq.service.*;

import com.up72.framework.util.page.PageBounds;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.up72.hq.service.IVideoService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * (视频DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class VideoServiceImpl implements  IVideoService{
	
	@Autowired
	private VideoMapper videoMapper;
	
	public void save(Video video){
		video.setLookCnt(0);
		videoMapper.insert(video);
	}

    public void update(Video video){
		videoMapper.update(video);
	}
	
    public void delete(java.lang.Long id){
		videoMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public VideoResp getById(java.lang.Long id){
		return videoMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<VideoResp> getPage(Map params, PageBounds rowBounds){
        PageList list = videoMapper.findPage(params, rowBounds);
		return new Page<VideoResp>(list,list.getPagination());
	}

	@Override
	public List<Video> getHot() {
		return videoMapper.findHot();
	}

	@Override
	public List<Video> getByCatId(java.lang.Long catId) {
		return videoMapper.findByCatId(catId);
	}

	@Override
	public List<Video> getXgVideo(Long catId) {
		return videoMapper.findXgVideo(catId);
	}

	@Override
	public List<Video> getLimit9(Map params) {
		return videoMapper.findLimit9(params);
	}

	@Override
	public List<Video> getHome(Long catId) {
		return videoMapper.findHome(catId);
	}


	/**
	 * 每1分钟执行定时任务
	 * 检查时间，到点自动发布
	 *//*
	@Scheduled(cron="0 0/1 * * * ? ")
	public void executeTimedTask() {
		try {
			Long nowTime= Cnst.getCurTime();
			Map<String,Object> params=new HashMap<String,Object>();
			PageList<VideoResp>  newList =videoMapper.findPage(params, new PageBounds(1, Integer.MAX_VALUE));
			if(ObjectUtils.isNotEmpty(newList) || newList.size()>0){
				for (int i = 0; i < newList.size(); i++) {
					Video video =newList.get(i);
					Long publishTime=video.getPublishTime();
					if(publishTime>nowTime){
						video.setStatus(Cnst.HqNews.YES_STATUS);
					}
					videoMapper.update(video);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}*/

}
