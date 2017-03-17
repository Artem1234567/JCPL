package com.jcplproject.boot;

import com.jcplproject.constants.Constants;
import com.jcplproject.utils.Utils;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;

/**
 *
 * @author Artem S
 */
public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    
    private static int threadsCount = 1; // min = 1
    
    public static void main(String[] args) {
        LOGGER.info(Constants.APP_STARTED);
        Main m = new Main();
        
        try {
            Optional<Integer> thCount = Utils.parseThreadCount(args);
            if (thCount.isPresent()) {
                threadsCount = thCount.get();
            }
            
            Document doc = Utils.loadLinkDocument(Constants.MAIN_PAGE_URL);
            
            List<String> links = Utils.loadLinks(doc, threadsCount);
            Utils.savePages(links, threadsCount);
            
        } catch(HttpStatusException ex) {
            LOGGER.log(Level.SEVERE, Constants.CONNECTION_ERROR, Constants.MAIN_PAGE_URL);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}
