
package com.ghaemi;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  This servlet acts as a dynamic non-persistence web crawler for Tweeter and CNN web sites.
 * 
 * @author Ghaemi
 */
@WebServlet(name = "Crawler", urlPatterns = {"/Crawler"})
public class CrawlerServlet extends HttpServlet {

     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        
        // getting and validating input data
        Optional<String> tw_id = Optional.ofNullable(request.getParameter("tid"));
        Optional<String> tw_count = Optional.ofNullable(request.getParameter("tcount"));
        if(tw_count.orElse("").length() == 0){
            tw_count = Optional.ofNullable(null);
        }
        
        Optional<String> cnn_keyword = Optional.ofNullable(request.getParameter("keyw"));
        Optional<String> cnn_count = Optional.ofNullable(request.getParameter("hlines"));
        if(cnn_count.orElse("").length() == 0){
            cnn_count = Optional.ofNullable(null);
        }
        
        
        
        
        request.setAttribute("tweets",TweetCrawler.getTweets(tw_id.orElse("realDonaldTrump"),Integer.parseInt(tw_count.orElse("25"))));
        request.setAttribute("tName", TweetCrawler.getName(tw_id.orElse("realDonaldTrump")));
        
        request.setAttribute("news", SimpleCNNCrawler.getInstance().getNews(cnn_keyword.orElse("trump"),Integer.parseInt(cnn_count.orElse("25")) ));
        
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    
     
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

     
    
    @Override
    public String getServletInfo() {
        return "Tweeter and CNN Crawler";
    } 

}
