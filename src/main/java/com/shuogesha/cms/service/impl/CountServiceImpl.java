package com.shuogesha.cms.service.impl;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.shuogesha.cms.dao.CountDao;
import com.shuogesha.cms.entity.Count;
import com.shuogesha.cms.service.CountService;
import com.shuogesha.platform.entity.Site;
import com.shuogesha.platform.service.SiteService;
import com.shuogesha.platform.web.RequestUtils;

import net.sf.ehcache.Ehcache;
@Service
public class CountServiceImpl implements CountService{

	@Autowired
	private CountDao countDao;

	 
	@Override
	public Count findByid(String threadid) {
		Count ThreadCount = countDao.findByid(threadid);
		return ThreadCount;
	}


	@Override
	public int freshCacheToDB(Ehcache cache) throws ParseException {
		List<Site> sites = siteService.findAll();
		clearCount(sites.get(0));
		int count = countDao.freshCacheToDB(cache);
		//copyCount(sites.get(0));
		return count;
	}

	private int clearCount(Site site) throws ParseException {
		Calendar curr = Calendar.getInstance();
		Calendar last = Calendar.getInstance();
		last.setTime(RequestUtils.parse(site.getCountClearTime()));
		int currDay = curr.get(Calendar.DAY_OF_YEAR);
		int lastDay = last.get(Calendar.DAY_OF_YEAR);
		if (currDay != lastDay) {
			int currWeek = curr.get(Calendar.WEEK_OF_YEAR);
			int lastWeek = last.get(Calendar.WEEK_OF_YEAR);
			int currMonth = curr.get(Calendar.MONTH);
			int lastMonth = last.get(Calendar.MONTH);
			siteService.updateCountClearTime(RequestUtils.getDateStr(curr.getTime()),site.getId());
			return countDao.clearCount(currWeek != lastWeek, currMonth != lastMonth);
		} else {
			return 0;
		}
	}
	
	private int copyCount(Site site) throws ParseException {
		long curr = System.currentTimeMillis();
		long last = RequestUtils.parse(site.getCountCopyTime()).getTime();
		if (curr > interval + last) {
			siteService.updateCountCopyTime(RequestUtils.getDateStr(new Date(curr)),site.getId());
			return countDao.copyCount();
		} else {
			return 0;
		}
	}
	
	@Override
	public void saveEntity(Count threadCount) {
		countDao.saveEntity(threadCount);
	}
	
	 
	
	@Override
	public long count(String id) {
		return countDao.count(id);
	}
	 
	@Override
	public void removeById(String id) {
		countDao.removeById(id);
	}
	
	@Override
	public Count saveCount(String referId, String refer) {
		if(countDao.count(referId,refer)<=0){
			Count entity = new Count();
			entity.setId(referId);
			entity.setRefer(refer);
			entity.setReferId(referId);
			countDao.saveEntity(entity);
			return entity;
		}else {
			return null;
		}
	}
	
	 
	
	@Autowired
	private SiteService siteService;
	
	private int interval = 12 * 60 * 60 * 1000; // 一小时
	
	/**
	 * 设置拷贝间隔时间。默认一小时。
	 * 
	 * @param interval
	 *            单位分钟
	 */
	public void setInterval(int interval) {
		this.interval = interval * 60 * 1000;
	}



}
