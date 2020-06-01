/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.BulkEmailSenderScheduler.utils;

import com.bits.BulkEmailSenderScheduler.service.AnynomousService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author sarker
 */
@PropertySource(value = "classpath:application.properties")
@Component
public class AppUtil {

    Logger logger = LoggerFactory.getLogger(AppUtil.class);
    
    @Value("${app.name}")
    private String APP_NAME;
    

    @Value("${app.full_name}")
    private String APP_FULL_NAME;
    
    @Value("${mail.tracker_server}")
    private String MAIL_TRACKER_SERVER;
    
    @Value("${campaign.attachments}")
    private String CAMPAIGN_ATTACHMENTS;
    
    @Value("${campaign.cids}")
    private String CAMPAIGN_CID;
    
    
    @Value("${mail.bounceaddr}")
    private String BOUNCED_ADR;
    
    @Value("${mail.host}")
    private String BOUNCED_HOST;
    
    @Value("${mail.password}")
    private String BOUNCED_PWD;
    
    @Value("${mail.port}")
    private String BOUNCED_PORT;
    
    @Value("${sender.name}")
    private String SENDER_NAME;
    
    
    @Autowired
    AnynomousService anynomousService;
    
    public static String SHA1(String plainText) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] hash = messageDigest.digest(plainText.getBytes());

            return new String(hash);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }
    
    public synchronized long getMaxVersion(){
        return Calendar.getInstance().getTimeInMillis();
    }

    public static String encodeURL(String url) {
        try {
            return URLEncoder.encode(url); //, StandardCharsets.UTF_8.toString()
        } catch (Exception ex) {
            throw new RuntimeException(ex.getCause());
        }
    }
    
    public static String SHA256(String plainText) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(plainText.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    public synchronized static UUID toUUID(Object obj) {
        try {
            return UUID.fromString(obj.toString());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }

    public synchronized static UUID toUUID(String obj) {
        try {
            return UUID.fromString(obj);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }

    public static String toString(Object str) {
        if (str == null) {
            return "";
        }
        try {
            return String.valueOf(str);
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return "";
    }

    public static int toInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return 0;
    }
    
    public static int toInt(Object number) {
        try {
            return Integer.parseInt(number.toString());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return 0;
    }

    public static long toLong(Object number) {
        try {
            return Long.parseLong(number + "");
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return 0;
    }

    public static long toLong(String number) {
        try {
            return Long.parseLong(number);
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return 0;
    }

    public static boolean toBoolean(Object val) {
        try {
            return (boolean) val;
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return false;
    }

    public static double toDouble(Object number) {
        try {
            return Double.parseDouble(number.toString());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return 0.0f;
    }

    public static Date toDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {

            return sdf.parse(date);
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return new Date();
    }
    
    public static Date toDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
        
        return new Date();
    }
    
    public static String getPunchTime(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        SimpleDateFormat psdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            return psdf.format(sdf.parse(date));
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
        
        return "";
    }

    public static Date getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {

            return sdf.parse(sdf.format(new Date()).toString());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return new Date();
    }
    
    public static Date getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(sdf.format(new Date()));
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return new Date();
    }
    
    public static String getDate(int addDay) {
        try{
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, addDay);
            int m = (cal.get(Calendar.MONTH) + 1);
            int d = cal.get(Calendar.DATE);
            
            return cal.get(Calendar.YEAR) + "-" + ((m<10)? "0"+m:m) + "-" + ((d<10)? "0"+d:d);
        } catch(Exception e){
            return getDateString();
        }
    }
    
    public static String getDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.format(new Date());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return null;
    }
    
    public static String getDateTimeString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.format(new Date());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return null;
    }
    
    public static String getTxnDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.format(new Date()).replaceAll("-", "");
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
        return null;
    }
    
    public static String getTxnDateString(int addDay) {
        try{
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, addDay);
            int m = (cal.get(Calendar.MONTH) + 1);
            int d = cal.get(Calendar.DATE);
            
            return cal.get(Calendar.YEAR) + "-" + ((m<10)? "0"+m:m) + "-" + ((d<10)? "0"+d:d);
        } catch(Exception e){
            return getTxnDateString();
        }
    }
    
    public static String getTxnDateString(String date) {
        
        try {
            return date.replaceAll("-", "");
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
        return null;
    }

    public static synchronized int getRandomNumber(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static String generatePassword(int length) {
        String charStr = "!@#$%^&*()ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890_<>{}[].,+";

        String passStr = "";
        for (int i = 0; i < length; i++) {
            int _rn = AppUtil.getRandomNumber(0, 82);
            passStr += charStr.charAt(_rn);
        }

        return passStr;
    }

    public String getAppName() {
        try {
            return APP_NAME;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "CMS";
    }

    public String getAppFullName() {
        try {
            return APP_FULL_NAME;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Contact Management System";
    }
    
    public String getMailTrackerServer() {
        try {
            return MAIL_TRACKER_SERVER + "/auth/uniqmail/HzBrlotcRkaIgmSarKer/";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Contact Management System";
    }
    
    public String getCampaignAttachPath() {
        try {
            return CAMPAIGN_ATTACHMENTS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/";
    }
    
    public String getCampaignCIDPath() {
        try {
            return CAMPAIGN_CID;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/";
    }
    
    
    public String getBouncedAddr() {
        try {
            return BOUNCED_ADR;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/";
    }
    
    public String getBouncedHost() {
        try {
            return BOUNCED_HOST;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/";
    }
    
    public String getSenderName() {
        try {
            return SENDER_NAME;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "no-reply";
    }
    
    public String getBouncedPwd() {
        try {
            return BOUNCED_PWD;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/";
    }
    
    public String getBouncedPort() {
        try {
            return BOUNCED_PORT;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/";
    }
    
    public synchronized String processEmbededImages(long campaignId, String emailBody){
        Document doc = Jsoup.parse(emailBody);
        Elements imgs = doc.select("img[src]");
        logger.info( "EMD IMG: " + imgs.size() + " embedded image found!");
        int fileNo = 0;
        for(Element src : imgs){
            try{
                decodeToImage(campaignId, src.attr("src"), fileNo);
                src.attr("src", "cid:" + campaignId + "_img_" + fileNo);
                fileNo++;
            } catch(Exception ex){}
            System.out.println(src.attr("src"));
        }
        
        return doc.html();
    }
    
    private synchronized boolean decodeToImage(long campaignId, String imageString, int fileNo) throws IOException {
        String[] typeStr = imageString.split(",");
        String type = (typeStr[0].contains("png"))?"png":"jpg";
        
        InputStream ins = org.apache.commons.io.IOUtils.toInputStream(typeStr[1], "UTF-8");
        String dataString = convertStreamToString(ins);
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(dataString);
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
        logger.info( "EMD IMG: " + " decoded to a BufferedImage!");
        return uploadContentImage(campaignId, image, type, fileNo);
    }
    
    public synchronized boolean uploadContentImage(long campaignId, BufferedImage file, String type, int fileNo) {
        
        try {
            File dir = new File(CAMPAIGN_CID);
            if (!dir.exists()) {
                if (dir.mkdirs()) {
                    logger.info("Directory Created!");
                } else {
                    logger.info("Failed to create directory!");
                }
            } else{
                logger.info("Directory already exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        logger.info( "EMD IMG: " + " direcroty checked/created!");
        
        try {
            String fileName = campaignId + "_img_" + fileNo + "." + type;
            File outputfile = new File( CAMPAIGN_CID + File.separator + fileName);
            
            ImageIO.write(file, "PNG", outputfile);
            
            /*if( type.equalsIgnoreCase("PNG") ){
                ImageIO.write(file, "PNG", outputfile);
            } else if( type.equalsIgnoreCase("JPEG") ){
                ImageIO.write(file, "JPEG", outputfile);
            } else if( type.equalsIgnoreCase("BMP") ){
                ImageIO.write(file, "BMP", outputfile);
            } else{
                ImageIO.write(file, "JPG", outputfile);
            }*/
            
            anynomousService.saveCampaignCIDs(campaignId, fileName);
            logger.info( "EMD IMG: " + " image filed saved");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    private static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
    
}
