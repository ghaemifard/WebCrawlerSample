package com.ghaemi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents the essential data structure of CNN article web pages
 * 
 * @author Ghaemi
 */
public class CNNNews {
    private String addr="";
    private Date lastmod;
    private String imgAddr="";
    private String imgTitle="CNN:";
    // not used 
    private String title="";
    // not used
    private String content="";
    
    private static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
    
    public CNNNews(String addr, Date lastmod, String imgAddr, String imgTitle) {
        this.addr = addr;
        this.lastmod = lastmod;
        this.imgAddr = imgAddr;
        this.imgTitle = imgTitle;
    }
    public boolean containts(String key){
        key = key.toLowerCase();
        return addr.toLowerCase().contains(key) || imgAddr.toLowerCase().contains(key) || imgTitle.toLowerCase().contains(key);
    }

    @Override
    public String toString() {
        return addr + "\n\t" + lastmod.toString();
    }
    
    
    
     public static CNNNews make(String addr, String lastmod, String imgAddr, String imgTitle) {
        try{
            
            return new CNNNews(addr, formatter.parse(lastmod), imgAddr, imgTitle);
        }catch(Exception e){
            System.out.println("Cannot Format " + lastmod);
            e.printStackTrace();
        }
        return null;
    }
    
    

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Date getLastmod() {
        return lastmod;
    }

    public void setLastmod(Date lastmod) {
        this.lastmod = lastmod;
    }

    public String getImgAddr() {
        return imgAddr;
    }

    public void setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr;
    }

    public String getImgTitle() {
        return imgTitle;
    }

    public void setImgTitle(String imgTitle) {
        if( imgTitle != null && imgTitle.trim().length() > 0)
        this.imgTitle = imgTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    
}
