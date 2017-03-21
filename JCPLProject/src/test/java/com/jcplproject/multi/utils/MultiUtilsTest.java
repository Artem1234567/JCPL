package com.jcplproject.multi.utils;

import com.jcplproject.utils.Utils;
import java.io.IOException;
import java.util.Optional;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

public class MultiUtilsTest {
    
    private static final String linkURL0 = "http://developer.alexanderklimov.ru/android/";
    private static final String linkURL1 = "http://developer.alexanderklimov.ru/android/sssssssssssssssssssssssssssssssss";
    private static final String linkURLNull = null;
    private static final String linkURLEmp = "";
    private static final String linkURLEmp1 = "  ";
    private static Document expResult;
    
    private static final String errMess01 = "HTTP error fetching URL";
    
    @Before
    public void setUp() throws IOException {
        expResult = Jsoup.connect(linkURL0).execute().parse();
    }
    
    @Test
    public void testLoadLinkDocument() throws IOException {
        Throwable thr = catchThrowable(() -> Utils.loadLinkDocument(linkURLNull));
        assertThat(thr).isInstanceOf(IllegalArgumentException.class);
        Throwable thr1 = catchThrowable(() -> Utils.loadLinkDocument(linkURLEmp));
        assertThat(thr1).isInstanceOf(IllegalArgumentException.class);
        Throwable thr2 = catchThrowable(() -> Utils.loadLinkDocument(linkURLEmp1));
        assertThat(thr2).isInstanceOf(IllegalArgumentException.class);
        
        Optional<Document> result1 = Utils.loadLinkDocument(linkURL0);
        assertThat(result1).isNotNull();
        assertThat(result1.get().body().data()).isEqualTo(expResult.body().data());
        
        Throwable thr01 = catchThrowable(() -> Utils.loadLinkDocument(linkURL1));
        assertThat(thr01).isInstanceOf(HttpStatusException.class).hasMessage(errMess01);
    }
}
