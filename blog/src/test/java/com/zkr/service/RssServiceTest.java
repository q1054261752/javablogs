package com.zkr.service;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.zkr.entity.Item;
import com.zkr.exception.RssException;

public class RssServiceTest {
	
	private RssService rssService;

	@Before
	public void setUp() throws Exception {
		rssService = new RssService();
	}

	@Test
	public void testGetItemsFile() throws RssException {
	
		//List<Item> items = rssService.getItems("test-rss/javavids.xml");
	//	List<Item> items = rssService.getItems("http://blog.sina.com.cn/rss/1286528122.xml");
		List<Item> items = rssService.getItems("http://blog.csdn.net/q1054261752/rss/list");
		assertEquals(20, items.size());
		System.out.println(items.size());
	}

}
