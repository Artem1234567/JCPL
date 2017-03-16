package com.jcplproject.utils;

import com.jcplproject.constants.Constants;
import com.jcplproject.multi.Counter;
import com.jcplproject.multi.WriterTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Utils {

    private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());
    
    public static int parseThreadCount(String[] args) throws Exception {
        try {
            if (args == null) {
                throw new Exception(Constants.WRONG_ARGS);
            } else if (args.length != 1) {
                throw new Exception(Constants.WRONG_ARGS);
            }
            int threadsCount = Integer.parseInt(args[0]);
            check(threadsCount);
            return threadsCount;
        } catch(NumberFormatException ex) {
            throw new Exception(Constants.INCORRECT_VALUE);
        }
    }
    
    public static void check(int threads) throws Exception {
        if (threads < 1 || threads > Integer.MAX_VALUE) {
            throw new Exception(Constants.INCORRECT_VALUE);
        }
    }
    
    public static List<String> loadLinks(Document doc, int threadCount) throws Exception {
        if (doc == null) {
            throw new IllegalArgumentException(Constants.WRONG_DOC);
        } else if (threadCount < 1) {
            throw new Exception(Constants.WRONG_TREAD_COUNT);
        }
        Elements elements = doc.select(Constants.A_TAG);
        List<String> linkList = new ArrayList();
        
        elements.stream().map((elem) -> elem.attr(Constants.HREF_TAG)).filter((attr) -> (attr.contains(Constants.HTTP_LINK))).limit(threadCount).forEachOrdered((attr) -> {
            linkList.add(attr);
        });
        
        LOGGER.log(Level.INFO, Constants.LINKS_LOADED, linkList.size());
        
        return linkList;
    }
    
    public static void savePages(List<String> links, int threadsCount) throws Exception {
        if (links == null || links.isEmpty()) {
            throw new IllegalArgumentException(Constants.WRONG_LINK_LIST);
        } else if (threadsCount < 1) {
            throw new Exception(Constants.WRONG_TREAD_COUNT);
        }
        
        if (threadsCount > links.size()) {
            LOGGER.log(Level.WARNING, Constants.THREADS_COUNT_WARNING);
            threadsCount = links.size();
        }
        
        ExecutorService pool = Executors.newFixedThreadPool(threadsCount);
        Counter counter = new Counter();
        
        for (int i = 0; i < threadsCount; i++) {
            Future<String> result = pool.submit(new WriterTask(i, links.get(i)));
            
            try {
                String res = result.get();
                if (res.equals(Constants.WRITING_DONE)) {
                    counter.increment();
                    printPageCounter(counter.value());
                }
            } catch (InterruptedException | ExecutionException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        pool.shutdown();
        if (pool.isShutdown()) {
            LOGGER.info(Constants.APP_FINISHED);
        }
    }
    
    public static void printPageCounter(int counterValue) {
        LOGGER.log(Level.INFO, Constants.PAGES_LOADED, counterValue);
    }
    
    public static Document loadLinkDocument(String linkURL) throws IOException {
        if (linkURL == null || linkURL.trim().isEmpty()) {
            throw new IllegalArgumentException(Constants.WRONG_LINK_URL);
        }
        Connection.Response resp = Jsoup.connect(linkURL).execute();
        return resp.parse();
    }
}
