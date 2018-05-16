package com.mycompany.rss;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

@Service
public class RssFeedServiceImpl implements RssFeedService {

	//get log (using log4j)
	private Log log = LogFactory.getLog(getClass());
	
	/**attaching the cache to store the feed data in. 
	 * the cache will empty itself every hour so that the next getFeed() will trigger
	 *  a call to the url to insure updated information. 
	 */
	@Resource(name="feedCache")
	private Cache feedCache;
	
	
	@Override
	public List<FeedItemRO> getFeed() {
		//get from the cache (the data feed)
		Element cached = feedCache.get("allFeedData");
		// if cache is empty fill it by calling the internal method getFeedFromGoogle()
		if (cached == null) {
			cached = new Element("allFeedData", getFeedFromGoogle());
			feedCache.put(cached);
		}
		// return the cached data cast as representation objects
		return (List<FeedItemRO>) cached.getObjectValue();
	}

	//the method parses the RSS feed using ROME and return a RO list containing the requested data - title, link, description.
	private List<FeedItemRO> getFeedFromGoogle() {
		List<FeedItemRO> feedItemROs = new ArrayList<>();
		// parse the rss feed with ROME library inside a try-catch so that if there is a connection problem it will throw an exception.
		URL feedUrl;
		try {
			feedUrl = new URL("https://news.google.com/gn/news/?ned=us&gl=US&hl=en&output=rss");
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(feedUrl));
			// every news story in the feed is an entry, for each one construct a RO with title, link, description and add it to the list. 
			List<SyndEntry> entries = feed.getEntries();
			for (SyndEntry syndEntry : entries) {
				feedItemROs.add(new FeedItemRO(syndEntry.getTitle(),syndEntry.getLink(), syndEntry.getDescription().getValue()));
			}
		} catch (Exception e) {
			//log the error
			log.error(e, e);
		}
		
		// return RO list of news stories
		return feedItemROs;
	}


	
}
