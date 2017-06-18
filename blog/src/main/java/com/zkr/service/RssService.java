package com.zkr.service;

import java.io.File;
import java.nio.channels.Channel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.springframework.stereotype.Service;

import com.zkr.entity.Item;
import com.zkr.exception.RssException;
import com.zkr.rss.ObjectFactory;
import com.zkr.rss.TRss;
import com.zkr.rss.TRssChannel;
import com.zkr.rss.TRssItem;

@Service
public class RssService {

	public List<Item> getItems(File file) throws RssException{
		
		return getItems(new StreamSource(file));
	}
    public List<Item> getItems(String url) throws RssException{
		
		return getItems(new StreamSource(url));
	}
	
	
	public List<Item> getItems(Source source) throws RssException{
		ArrayList<Item> list= new ArrayList<Item>();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			JAXBElement<TRss> jaxbElement = unmarshaller.unmarshal(source, TRss.class);
			TRss rss = jaxbElement.getValue();
			
			List<TRssChannel> channels = rss.getChannel();
			for(TRssChannel channel:channels){
				List<TRssItem> rssItems = channel.getItem();
				for(TRssItem rssItem:rssItems){
					
					Item item = new Item();
					item.setTitle(rssItem.getTitle());
					item.setDescription(rssItem.getDescription());
					item.setLink(rssItem.getLink());
					Date pubDate = null;
					
					try {
						 pubDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z",Locale.ENGLISH).parse(rssItem.getPubDate());
					} catch (ParseException e) {
						
						 try {
							pubDate = new SimpleDateFormat("yyyy-MM-dd").parse(rssItem.getPubDate());
						} catch (Exception e1) {
							
							 pubDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(rssItem.getPubDate());
						}
						 
					}
					
					item.setPublishedDate(pubDate);
					list.add(item);
				}
			}
			
			
		} catch (JAXBException e) {
			
			throw new RssException(e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new RssException(e);
		}
		
		return list;
	}
}
