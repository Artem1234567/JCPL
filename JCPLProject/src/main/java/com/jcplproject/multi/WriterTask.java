package com.jcplproject.multi;

import com.jcplproject.constants.Constants;
import com.jcplproject.utils.Utils;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;

public class WriterTask implements Callable<String> {

    private static final Logger LOGGER = Logger.getLogger(WriterTask.class.getName());

    private String link;
    private Integer name;
    
    public WriterTask(String link) {
        this.link = link;
    }

    public WriterTask(Integer i, String link) {
        this.name = i;
        this.link = link;
    }
    
    @Override
    public String call() throws Exception {
        createFile();
        return Constants.WRITING_DONE;
    }
    
    public void createFile() throws IOException {
        File f = new File(Constants.WEBPAGES_DIR, name + Constants.PAGE_EXT);
        
        Optional<Document> doc = Utils.loadLinkDocument(link);
        if (doc.isPresent()) {
            FileUtils.writeStringToFile(f, doc.get().outerHtml(), Constants.ENCODING_NAME);
        } else {
            LOGGER.warning(Constants.WRONG_DOC);
        }
    }
}
