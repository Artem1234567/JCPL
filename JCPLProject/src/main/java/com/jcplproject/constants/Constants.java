package com.jcplproject.constants;

public interface Constants {
    String ENCODING_NAME = "UTF-8";
    
    /* Directory name for storing pages */
    String WEBPAGES_DIR = "webpages";
    
    String PAGE_EXT = ".html";
    
    String MAIN_PAGE_URL = "http://developer.alexanderklimov.ru/android/";
    
    /* Notification message */
    String WRITING_DONE = "DONE";
    String APP_STARTED = "Application has started.";
    String APP_FINISHED = "Application has finished successfully.";
    
    String LINKS_LOADED = "Loaded links: {0}";
    String PAGES_LOADED = "Loaded pages: {0}";
    
    String THREADS_COUNT_WARNING = "Entered value greater than links count on page! Will be saved number of pages equal to the number of links.";
    
    /* Tags */
    String A_TAG = "a";
    String HREF_TAG = "href";
    
    /* Additional parameters */
    String HTTP_LINK = "http:";
    
    /* Console Messages */
    String CONNECTION_ERROR = "Connection error for URL: {0}";
    String WRONG_ARGS = "Please, enter one value for loading pages count!";
    String INCORRECT_VALUE = "Cannot parse entered value! Enter correct value!";
    
    /* For methods */
    String WRONG_LINK_URL = "LinkURL is null or empty.";
    String WRONG_DOC = "Document is null or empty.";
    String WRONG_LINK_LIST = "Link list is null or empty.";
    
    String WRONG_TREAD_COUNT = "Threads count cannot be less than 1!";
}
