package Rss.Rss;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class RssFeedServiceImpl {
	
	// create variable single_instance for the Singleton
    private static RssFeedServiceImpl single_instance = null;
 
    // private constructor restricted to this class itself
    private RssFeedServiceImpl(){
    }
 
    // if it does not already exist create an instance of Singleton 
    public static RssFeedServiceImpl getInstance()
    {
        if (single_instance == null)
            single_instance = new RssFeedServiceImpl();
 
        return single_instance;
    }
    
    // create global variable - a list of representation objects to save the feed data
	private List<FeedItemRO> feedItemROs = null;

	public List<FeedItemRO> getFeed() {
		// if the global variable containing the feed data is empty - call the URL using the private method getFeedFromGoogle().
		if (feedItemROs == null) {
			feedItemROs = getFeedFromGoogle();
		}

		// if it's not empty - return the feed data already stored.
		return feedItemROs;
	}

	//the method parses the RSS feed using ROME and return a RO list containing the requested data - title, link, description.
	private List<FeedItemRO> getFeedFromGoogle() {
		// create RO list
		List<FeedItemRO> feedItemROs = new ArrayList<>();
		// parse the data with ROME library inside a try-catch so that if there is a connection problem it will throw an exception.
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
			//Should be logged properly
			e.printStackTrace();
		}
		
		// return RO list of news stories.
		return feedItemROs;
	}


	
}
