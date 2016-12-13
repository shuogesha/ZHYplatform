package com.shuogesha.cms.service.impl;

import java.text.ParseException;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.shuogesha.cms.entity.Count;
import com.shuogesha.cms.service.CountCacheService;
import com.shuogesha.cms.service.CountService;

@Service
public class CountCacheServiceImpl implements CountCacheService, DisposableBean{
	 
	public int[] viewAndGet(String id) throws ParseException {
		Count count = countService.findByid(id);
		if (count == null) {
			count = new Count();
			count.setId(id);
			countService.saveEntity(count);
		}
		Element e = cache.get(id);
		Integer views;
		if (e != null) {
			views = (Integer) e.getValue() + 1;
		} else {
			views = 1;
		}
		cache.put(new Element(id, views));
		refreshToDB();
		return new int[] { views + count.getViews() };
	}
	
	private void refreshToDB() throws ParseException {
		long time = System.currentTimeMillis();
		if (time > refreshTime + interval) {
			refreshTime = time;
			int count = countService.freshCacheToDB(cache);
			// 清除缓存
			cache.removeAll();
		}
	}
	@Override
	public void destroy() throws Exception {
		int count = countService.freshCacheToDB(cache);
	}
	// 间隔时间
	private int interval = 60; // 1分钟
	// 最后刷新时间
	private long refreshTime = System.currentTimeMillis();
	
	/**
	 * 刷新间隔时间
	 * 
	 * @param interval
	 *            单位分钟
	 */
	public void setInterval(int interval) {
		this.interval = interval * 60 * 1000;
	}
	
	private Ehcache cache;
	@Autowired
	private CountService  countService;

	@Autowired
	public void setCache(@Qualifier("count") Ehcache cache) {
		this.cache = cache;
	}

}
