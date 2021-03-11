package Wurdy;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownloadHTML {
    private String DownloadFileName = "Download.html";
    private static final Logger logger = Logger.getLogger(DownloadHTML.class.getName());

    String downLoad(String inUrl){

        Handler fileHandler = null;
        try {
            fileHandler = new FileHandler("DHP.log");
        }
        catch (IOException e) {
            logger.log(Level.WARNING,"Error: ", e);
        }
        logger.addHandler(fileHandler);

        DownloadHTML urlRename = new DownloadHTML();//Преобразование Кириллицы в URL

        URL url;
        try {
            url = new URL(urlRename.enCoding(inUrl));
        }
        catch (MalformedURLException e) {
            logger.log(Level.WARNING,"Error: ", e);
            return null;
        }
        logger.info("URL-> " + url.toString());


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(new FileWriter(DownloadFileName))){
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
            }
            logger.info("Html-file download. File name: Download.html");
        }
        catch (IOException ie) {
            logger.log(Level.WARNING,"Error: ", ie);       //input-output. Possible: you entered http:\\url. Try: https:\\url
            return null;
        }

        return DownloadFileName;
    }

    String enCoding(String url) {
        Pattern patURL = Pattern.compile("[А-яЁё]");
        Matcher matcherURL = patURL.matcher(url);

        StringBuffer urlBuff = new StringBuffer();

        while (matcherURL.find()) {
            String t = Character.toString(url.charAt(matcherURL.start()));
            try {
                matcherURL.appendReplacement(urlBuff, URLEncoder.encode(t, "UTF-8"));
            }
            catch (UnsupportedEncodingException e) {
                logger.log(Level.WARNING,"Error: ", e);
            }
        }
        matcherURL.appendTail(urlBuff);
        return urlBuff.toString();
    }
}
