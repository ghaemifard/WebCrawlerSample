

package com.ghaemi;

import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *  A simple Tweeter data extractor 
 * 
 * @author praghletoos
 */
public class TweetCrawler {
    
    // returns a list o tweets
    public static List<String> getTweets(String id,int count){
        List<String> tweets = new ArrayList<>();
        if(id.length() ==0)
            id = "realDonaldTrump";
        
        if(count<=0){
            count = 25;
        } 
        
        if(count>200){
            count = 200;
        } 
        
        String URL = "https://twitter.com/"+id;
        try{
            
            Document document = Jsoup.connect(URL).timeout(10000).get();
            Elements divs = document.select("div.stream-container");
             
            // we use this variable to get more than 20 tweets
            String mpos = divs.first().attr("data-min-position");
            

            document.select("p.tweet-text").forEach(e -> {tweets.add(e.html());});
            while(tweets.size()<count){
                 String jurl = "https://twitter.com/i/profiles/show/"+id+"/timeline/tweets?include_available_features=1&include_entities=1&max_position="+mpos+"&reset_error_state=false";
                 TwitterJson twitter = new Gson().fromJson(new InputStreamReader(new URL(jurl).openStream()), TwitterJson.class);
                 mpos = twitter.getMin_position();
                 Document doc = Jsoup.parseBodyFragment(twitter.getItems_html());
                 doc.select("p.tweet-text").forEach(e -> {tweets.add(e.html());});
                
            } 
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{ 
            return tweets.stream().limit(count).collect(Collectors.toList());
        }
    }
    
    //gives the name of a tweeter account
    public static String getName(String id){
        if(id.length() ==0)
            id = "realDonaldTrump";
        
        String URL = "https://twitter.com/"+id;
        try{
            
            Document document = Jsoup.connect(URL).timeout(10000).get();
            Elements as = document.select("h1.ProfileHeaderCard-name a");
              
            return as.first().wholeText();
            
  
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return "He/She";
        
    }
}
