/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2015
 */

package com.up72.hq.service.impl;

import com.up72.framework.util.page.Page;
import com.up72.framework.util.page.PageBounds;
import com.up72.framework.util.page.PageList;
import com.up72.hq.dao.InformationMapper;
import com.up72.hq.model.Information;
import com.up72.hq.service.IInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 信息表DAO实现
 * 
 * @author LIUGUICHENG
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class InformationServiceImpl implements IInformationService {
	
	@Autowired
	private InformationMapper mnInformationMapper;
	
	public void save(Information mnInformation){
		mnInformationMapper.insert(mnInformation);
	}

    public void update(Information mnInformation){
		mnInformationMapper.update(mnInformation);
	}
	
    public void delete(Long id){
		mnInformationMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public Information getById(Long id){
		return mnInformationMapper.findById(id);
	}

	@Override
	public Information getdByType(Integer type) {
		return mnInformationMapper.findByType(type);
	}

	@Transactional(readOnly=true)
    public Page<Information> getPage(Map params, PageBounds rowBounds){
        PageList list = mnInformationMapper.findPage(params, rowBounds);
		return new Page<Information>(list,list.getPagination());
	}

	@Override
	public void batchRealDel(String ids) {
		mnInformationMapper.batchRealDel(ids);
	}


}
