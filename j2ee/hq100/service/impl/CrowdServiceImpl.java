/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.framework.util.ObjectUtils;
import com.up72.hq.constant.Cnst;
import com.up72.hq.dto.resp.CrowdResp;
import com.up72.hq.model.*;
import com.up72.hq.dao.*;

import com.up72.framework.util.page.PageBounds;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.up72.hq.service.ICrowdService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 众筹DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class CrowdServiceImpl implements  ICrowdService{
	
	@Autowired
	private CrowdMapper crowdMapper;
	@Autowired
	private ReturnInfoServiceImpl returnInfoServiceImpl;//回报信息
	@Autowired
	private ProjectProgressServiceImpl projectProgressServiceImpl;//项目进展
	
	public void save(Crowd crowd){
		crowdMapper.insert(crowd);
	}

    public void update(Crowd crowd){
		crowdMapper.update(crowd);
	}
	
    public void delete(java.lang.Long id){
		crowdMapper.delete(id);
	}

	public void delete(long[] ids) {
		for(long id : ids){
			returnInfoServiceImpl.delete(id);
			projectProgressServiceImpl.delete(id);
			delete(id);
		}
	}

	@Transactional(readOnly=true)
    public Crowd getById(java.lang.Long id){
		return crowdMapper.findById(id);
	}

	@Override
	public CrowdResp getRespById(Long id) {
		return crowdMapper.findRespById(id);
	}

	@Transactional(readOnly=true)
    public Page<CrowdResp> getPage(Map params, PageBounds rowBounds){
        PageList list = crowdMapper.findPage(params, rowBounds);
		return new Page<CrowdResp>(list,list.getPagination());
	}

	@Override
	public List<CrowdResp> getList3(Long catId) {
		return crowdMapper.findList3(catId);
	}

	@Override
	public List<CrowdResp> getHome() {
		return crowdMapper.findHome();
	}

	@Override
	public List<CrowdResp> getHoTList(Long catId) {
		return crowdMapper.findHotList(catId);
	}

	/**
	 * 每1分钟执行定时任务
	 * 检查时间，到点自动发布
	 */
	/*@Scheduled(cron="0 0/1 * * * ? ")
	public void executeTimedTask() {
		try {
			//众筹
			Long nowTime= Cnst.getCurTime();
			Map<String,Object> params=new HashMap<String,Object>();
			PageList<CrowdResp>  crowdRespList =crowdMapper.findPage(params, new PageBounds(1, Integer.MAX_VALUE));
			if(ObjectUtils.isNotEmpty(crowdRespList) || crowdRespList.size()>0){
				for (int i = 0; i < crowdRespList.size(); i++) {
					CrowdResp crowds =crowdRespList.get(i);
					Crowd crowd = new Crowd();
					crowd.setId(crowds.getId());
					crowd.setCatId(crowds.getCatId());
					crowd.setName(crowds.getName());
					crowd.setStartTime(crowds.getStartTime());
					crowd.setEndTime(crowds.getEndTime());
					crowd.setImg(crowds.getImg());
					crowd.setDays(crowds.getDays());
					crowd.setTargetAmount(crowds.getTargetAmount());
					crowd.setProjectIntro(crowds.getProjectIntro());
					crowd.setProjectOverview(crowds.getProjectOverview());
					crowd.setAddTime(crowds.getAddTime());
					crowd.setStatus(crowds.getStatus());
					Long startTime=crowds.getStartTime();
					Long endTime=crowds.getEndTime();
					if(startTime<nowTime&&startTime<endTime){
						crowd.setStatus(Cnst.HqNews.YES_STATUS);
					}else if (nowTime>endTime){
						crowd.setStatus(Cnst.HqNews.NOT_STATUS);
					}
					crowdMapper.update(crowd);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Scheduled(cron="0 0 1 * * ?") //每天凌晨1点执行
	public void executeTimedTask1() {
		try {
			//众筹
			Long nowTime= Cnst.getCurTime();
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("status",Cnst.HqNews.YES_STATUS);
			PageList<CrowdResp>  crowdRespList =crowdMapper.findPage(params, new PageBounds(1, Integer.MAX_VALUE));
			if(ObjectUtils.isNotEmpty(crowdRespList) || crowdRespList.size()>0){
				for (int i = 0; i < crowdRespList.size(); i++) {
					CrowdResp crowds =crowdRespList.get(i);
					Crowd crowd = new Crowd();
					crowd.setId(crowds.getId());
					crowd.setCatId(crowds.getCatId());
					crowd.setName(crowds.getName());
					crowd.setStartTime(crowds.getStartTime());
					crowd.setEndTime(crowds.getEndTime());
					crowd.setImg(crowds.getImg());
					crowd.setDays(crowds.getDays()-1);
					crowd.setTargetAmount(crowds.getTargetAmount());
					crowd.setProjectIntro(crowds.getProjectIntro());
					crowd.setProjectOverview(crowds.getProjectOverview());
					crowd.setAddTime(crowds.getAddTime());
					crowd.setStatus(crowds.getStatus());
					crowdMapper.update(crowd);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}*/

}
