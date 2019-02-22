 
package com.ghaemi;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

/**
 *  Used to represent the title and content of a CNN web page in JSON format.
 * 
 * @author Ghaemi
 */
@WebServlet(name = "ShowNews", urlPatterns = {"/ShowNews"})
public class ShowNewsServlet extends HttpServlet {

    /**
     *  Reads the content of a CNN web page and then extracts the title and content of it.
     *  
     * @param url a CNN article web page.
     * @return 
     */
    private CnnInfo readWebPage(String url) {
        
        CnnInfo info = new CnnInfo();
        try {
            InputStream openStream = new URL(url).openStream();
            IOUtils.skipFully(openStream, (long) (1024*1024*1.7));
            String data = IOUtils.toString(openStream, "UTF-8");

            final Pattern pattern = Pattern.compile("<body class(.+?)</body>", Pattern.DOTALL);
            final Matcher matcher = pattern.matcher(data);
            
            while(matcher.find()){ 
                String fragment = "<body class "+ matcher.group(1) + "</body>";
                org.jsoup.nodes.Document document = Jsoup.parseBodyFragment(fragment); 
                Elements headers = document.select("h1.pg-headline"); 
                if(headers.size()>0)
                    info.title = headers.get(0).wholeText();
                
                Elements paragraphs =document.select("section .l-container .zn-body__paragraph");
                if(paragraphs.size()>0){
                    paragraphs.forEach(e -> info.content += e.outerHtml());
                }
                 
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }
    
    /**
     *  A simple entity class for storing data from CNN article web pages.
     *  
     */
    private static class CnnInfo{
        String title="";
        String content="";
    }
     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final String url = request.getParameter("url");
        
        if(url == null || url.length() == 0)
            return;
         
        CnnInfo info = readWebPage(url);
        
        String jsonInfo = new Gson().toJson(info);
 
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonInfo);
        out.flush();
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
        return "Extracts data from CNN web pages";
    }// </editor-fold>

}
