 
package com.ghaemi;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.NodeList;

/**
 * A simple CNN Web Crawler
 * 
 * @author Ghaemi
 */
public class SimpleCNNCrawler {
    
    // Maximal number of all CNN articles that we are allowed to process
    private int MaxReadArticles = 100;
    
    
    public List<CNNNews> getNews(String query,int size){
        List<CNNNews> res = new ArrayList<>();
        
        if(query == null || query.length() ==0)
            query = "trump";
        
        final String keyword = query;
        if(size<=0)
            size = 25;
        
        try{
            List<String> firstNews = getSiteMaps();
            List<CNNNews> cNews = getBasicArticleInfo(firstNews);
            res = cNews.stream().sorted((a, b) -> b.getLastmod().compareTo(a.getLastmod())). 
                     filter(p -> p.containts(keyword)).
                     limit(size).
                     collect(Collectors.toList());
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return res;
    }
    
    private static final SimpleCNNCrawler crawler = new SimpleCNNCrawler();
    
    
    public static SimpleCNNCrawler getInstance(){
        return crawler;
    }

    private SimpleCNNCrawler() {
    }
    
    /** It is a heavy task. The size of each web page of CNN is almost 2MB (just the text).
     *  This method is re-implemented in ShowNews servlet
     * @param articles 
     */
    
    private void fillTitleAndContent(List<CNNNews> articles){
        
    }
    
    
    
    private List<CNNNews> getBasicArticleInfo(List<String> sources) {
        List<CNNNews> res = new ArrayList<>();
        final AtomicInteger counter = new AtomicInteger(0);
        sources.forEach(addr -> {
            if (res.size() < MaxReadArticles) {

                try {
                    System.out.println("Working On: " + addr);
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder;

                    dBuilder = dbFactory.newDocumentBuilder();

                    org.w3c.dom.Document doc = dBuilder.parse(new URL(addr).openStream());
                    doc.getDocumentElement().normalize();

                    XPath xPath = XPathFactory.newInstance().newXPath();

                    NodeList nodeList = (NodeList) xPath.compile("/urlset/url").evaluate(
                            doc, XPathConstants.NODESET);

                    IntStream.range(0, nodeList.getLength())
                            .mapToObj(i -> ((org.w3c.dom.Element) nodeList.item(i)))
                            .map(e -> {
                                org.w3c.dom.Element e2 = ((org.w3c.dom.Element) e.getElementsByTagName("image:image").item(0));
                                String imgLoc = "";
                                String imgCap = "";

                                if (e2 != null) {
                                    if (e2.getElementsByTagName("image:loc").item(0) != null) {
                                        imgLoc = e2.getElementsByTagName("image:loc").item(0).getTextContent();
                                    }
                                    if (e2.getElementsByTagName("image:caption").item(0) != null) {
                                        imgCap = e2.getElementsByTagName("image:caption").item(0).getTextContent();
                                    }
                                }

                                return CNNNews.make(e.getElementsByTagName("loc").item(0).getTextContent(),
                                        e.getElementsByTagName("lastmod").item(0).getTextContent(),
                                        imgLoc,
                                        imgCap);
                            })
                            .forEach(e -> {
                                res.add(e);
                            });
 
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        return res;
    }
    
    private List<String> getSiteMaps() {
        List<String> res = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;

            dBuilder = dbFactory.newDocumentBuilder();

            org.w3c.dom.Document doc = dBuilder.parse(new URL("https://edition.cnn.com/sitemaps/cnn/index.xml").openStream());
            doc.getDocumentElement().normalize();
            XPath xPath = XPathFactory.newInstance().newXPath();

            NodeList nodeList = (NodeList) xPath.compile("/sitemapindex/sitemap").evaluate(
                    doc, XPathConstants.NODESET);

            IntStream.range(0, nodeList.getLength())
                    .mapToObj(i -> ((org.w3c.dom.Element) nodeList.item(i)))
                    .map(e -> e.getElementsByTagName("loc").item(0).getTextContent())
                    .filter(e -> e.contains("edition") && e.contains("article"))
                    .sorted((a, b) -> b.compareTo(a))
                    .forEach(e -> {
                        res.add(e.replace("edition", "us"));
                        res.add(e);
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
