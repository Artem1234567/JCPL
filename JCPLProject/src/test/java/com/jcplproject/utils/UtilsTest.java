package com.jcplproject.utils;

import com.jcplproject.constants.Constants;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import org.jsoup.Jsoup;

public class UtilsTest {
    
    private static final String linkURL0 = "http://developer.alexanderklimov.ru/android/";
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void testParseThreadCount() throws Exception {
        System.out.println("parseThreadCount");
        String[] args0 = null;
        String[] args1 = {"5", "a"};
        String[] args2 = {"5,"};
        String[] args3 = {"5."};
        String[] args4 = {"sd"};
        
        String[] args11 = {"1"};
        int expCount11 = 1;
        
        String[] args12 = {"0"};
        String[] args13 = {"-2"};
        String[] args14 = {"2147483648"};
        String[] args15 = {"2147483647"};
        int expCount15 = 2147483647;
        
        Throwable thr = catchThrowable(() -> Utils.parseThreadCount(args0));
        assertThat(thr).isInstanceOf(Exception.class).hasMessage(Constants.WRONG_ARGS);
        
        Throwable thr01 = catchThrowable(() -> Utils.parseThreadCount(args1));
        assertThat(thr01).isInstanceOf(Exception.class).hasMessage(Constants.WRONG_ARGS);
        Throwable thr02 = catchThrowable(() -> Utils.parseThreadCount(args2));
        assertThat(thr02).isInstanceOf(Exception.class).hasMessage(Constants.INCORRECT_VALUE);
        Throwable thr03 = catchThrowable(() -> Utils.parseThreadCount(args3));
        assertThat(thr03).isInstanceOf(Exception.class).hasMessage(Constants.INCORRECT_VALUE);
        Throwable thr04 = catchThrowable(() -> Utils.parseThreadCount(args4));
        assertThat(thr04).isInstanceOf(Exception.class).hasMessage(Constants.INCORRECT_VALUE);
        
        Optional<Integer> count11 = Utils.parseThreadCount(args11);
        assertThat(count11.get()).isEqualTo(expCount11);
        
        Throwable thr05 = catchThrowable(() -> Utils.parseThreadCount(args12));
        assertThat(thr05).isInstanceOf(Exception.class).hasMessage(Constants.INCORRECT_VALUE);
        Throwable thr06 = catchThrowable(() -> Utils.parseThreadCount(args13));
        assertThat(thr06).isInstanceOf(Exception.class).hasMessage(Constants.INCORRECT_VALUE);
        Throwable thr07 = catchThrowable(() -> Utils.parseThreadCount(args14));
        assertThat(thr07).isInstanceOf(Exception.class).hasMessage(Constants.INCORRECT_VALUE);
        
        Optional<Integer> count15 = Utils.parseThreadCount(args15);
        assertThat(count15.get()).isEqualTo(expCount15);
    }

    @Test
    public void testCheck() throws Exception {
        System.out.println("check");
        int th0 = 0;
        int th1 = -2;
        
        Throwable thr01 = catchThrowable(() -> Utils.check(th0));
        assertThat(thr01).isInstanceOf(Exception.class).hasMessage(Constants.INCORRECT_VALUE);
        Throwable thr02 = catchThrowable(() -> Utils.check(th1));
        assertThat(thr02).isInstanceOf(Exception.class).hasMessage(Constants.INCORRECT_VALUE);
    }

    @Test
    public void testLoadLinks() throws IOException, Exception {
        System.out.println("loadLinks");
        Document docNull = null;
        int tc01 = 1;
        int tc02 = 0;
        
        int tc11 = 5;
        Document doc = new Document("");
        
        Throwable thr01 = catchThrowable(() -> Utils.loadLinks(docNull, tc01));
        assertThat(thr01).isInstanceOf(IllegalArgumentException.class).hasMessage(Constants.WRONG_DOC);
        Throwable thr02 = catchThrowable(() -> Utils.loadLinks(doc, tc02));
        assertThat(thr02).isInstanceOf(Exception.class).hasMessage(Constants.WRONG_TREAD_COUNT);
        
        Document doc2 = Jsoup.connect(linkURL0).execute().parse();
        List<String> links1 = Utils.loadLinks(doc2, tc11);
        assertThat(links1.size()).isEqualTo(tc11);
    }

    @Test
    public void testSavePages() throws Exception {
        System.out.println("savePages");
        List<String> linksNull = null;
        List<String> linksEmp = new ArrayList();
        int tc1 = 1;
        List<String> links01 = new ArrayList();
        links01.add("5");
        int tc02 = 0;

        Throwable thr01 = catchThrowable(() -> Utils.savePages(linksNull, tc1));
        assertThat(thr01).isInstanceOf(IllegalArgumentException.class).hasMessage(Constants.WRONG_LINK_LIST);
        Throwable thr02 = catchThrowable(() -> Utils.savePages(linksEmp, tc1));
        assertThat(thr02).isInstanceOf(IllegalArgumentException.class).hasMessage(Constants.WRONG_LINK_LIST);
        Throwable thr03 = catchThrowable(() -> Utils.savePages(links01, tc02));
        assertThat(thr03).isInstanceOf(Exception.class).hasMessage(Constants.WRONG_TREAD_COUNT);
        
        int tc11 = 6;
        List<String> links5 = new ArrayList();
        links5.add("http://www.cddoma.com.ua/328/0/Antivirusy/");
        links5.add("http://www.ozon.ru/context/detail/id/135330880/?partner=visual");
        links5.add("https://pagead2.googlesyndication.com/pub-config/r20160913/ca-pub-4224968932772057.js");
        links5.add("https://storage.googleapis.com/code.getmdl.io/1.0.0/material.indigo-light_blue.min.css");
        links5.add("https://pagead2.googlesyndication.com/pub-config/r20160913/ca-pub-4224968932772057.js");
        
        Utils.savePages(links5, tc11);
        
        checkFilesExists(links5);
    }
    
    private void checkFilesExists(List<String> links5) throws IOException {
        Path dirPath = Paths.get(Constants.WEBPAGES_DIR);
        long files = Files.walk(dirPath).collect(Collectors.toList()).parallelStream().filter(p -> !p.toFile().isDirectory()).count();
        assertThat(files).isEqualTo(links5.size());
    }
}
