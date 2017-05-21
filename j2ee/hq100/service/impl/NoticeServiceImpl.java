/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import com.up72.framework.util.page.Page;
import com.up72.framework.util.page.PageBounds;
import com.up72.framework.util.page.PageList;
import com.up72.hq.dao.NoticeMapper;
import com.up72.hq.dto.resp.NoticeResp;
import com.up72.hq.model.Notice;
import com.up72.hq.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 活动，公告，案例DAO实现
 *
 * @author up72
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class NoticeServiceImpl implements INoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    public void save(Notice notice) {
        noticeMapper.insert(notice);
    }

    public void update(Notice notice) {

        noticeMapper.update(notice);
    }

    public void delete(Long id) {
        noticeMapper.delete(id);
    }

    @Transactional(readOnly = true)
    public Notice getById(Long id) {
        return noticeMapper.findById(id);
    }

    @Transactional(readOnly = true)
    public Page<NoticeResp> getPage(Map params, PageBounds rowBounds) {
        PageList list = noticeMapper.findPage(params, rowBounds);
        return new Page<NoticeResp>(list, list.getPagination());
    }

    @Transactional(readOnly = true)
    public Notice getByType(Integer type) {
        return noticeMapper.findByType(type);
    }


}
