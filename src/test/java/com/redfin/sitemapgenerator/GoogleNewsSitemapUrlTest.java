package com.redfin.sitemapgenerator;

import java.io.File;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.redfin.sitemapgenerator.W3CDateFormat.Pattern;

public class GoogleNewsSitemapUrlTest extends TestCase {

    File dir;
    GoogleNewsSitemapGenerator wsg;

    public static final String BASE = "  ";

    @Override
    public void setUp() throws Exception {
        dir = File.createTempFile(GoogleNewsSitemapUrlTest.class.getSimpleName(), "");
        dir.delete();
        dir.mkdir();
        dir.deleteOnExit();
    }

    @Override
    public void tearDown() {
        wsg = null;
        for (File file : dir.listFiles()) {
            file.deleteOnExit();
            file.delete();
        }
        dir.delete();
        dir = null;
    }

    public void testSimpleUrl() throws Exception {
        W3CDateFormat dateFormat = new W3CDateFormat(Pattern.SECOND);
        dateFormat.setTimeZone(W3CDateFormat.ZULU);
        wsg = GoogleNewsSitemapGenerator.builder("http://www.example.com", dir).dateFormat(dateFormat).build();
        GoogleNewsSitemapUrl url = new GoogleNewsSitemapUrl("http://www.example.com/index.html", "Beispieltitel",
                new Date(0), "Beispiel", "de");
        wsg.addUrl(url);
        // @formatter:off
        String expected =
                  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\" xmlns:news=\"http://www.google.com/schemas/sitemap-news/0.9\" >\n"
                + BASE+"<url>\n"
                + BASE+BASE+"<loc>http://www.example.com/index.html</loc>\n"
                + BASE+BASE+"<news:news>\n"
                + BASE+BASE+BASE+"<news:publication>\n"
                + BASE+BASE+BASE+BASE+"<news:name>Beispiel</news:name>\n"
                + BASE+BASE+BASE+BASE+"<news:language>de</news:language>\n"
                + BASE+BASE+BASE+"</news:publication>\n"
                + BASE+BASE+BASE+"<news:publication_date>1970-01-01T00:00:00Z</news:publication_date>\n"
                + BASE+BASE+BASE+"<news:title>Beispieltitel</news:title>\n"
                + BASE+BASE+"</news:news>\n"
                + BASE+"</url>\n"
                + "</urlset>";
        // @formatter:on
        String sitemap = writeSingleSiteMap(wsg);
        assertEquals(expected, sitemap);
    }

    public void testKeywords() throws Exception {
        W3CDateFormat dateFormat = new W3CDateFormat(Pattern.SECOND);
        dateFormat.setTimeZone(W3CDateFormat.ZULU);
        wsg = GoogleNewsSitemapGenerator.builder("http://www.example.com", dir).dateFormat(dateFormat).build();
        GoogleNewsSitemapUrl url = new GoogleNewsSitemapUrl.Options("http://www.example.com/index.html",
                "Beispieltitel", new Date(0), "Beispiel", "de").keywords("Klaatu", "Barrata", "Nicto").build();
        wsg.addUrl(url);
        // @formatter:off
        String expected =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
              + "<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\" xmlns:news=\"http://www.google.com/schemas/sitemap-news/0.9\" >\n"
              + BASE+"<url>\n"
              + BASE+BASE+"<loc>http://www.example.com/index.html</loc>\n"
              + BASE+BASE+"<news:news>\n"
              + BASE+BASE+BASE+"<news:publication>\n"
              + BASE+BASE+BASE+BASE+"<news:name>Beispiel</news:name>\n"
              + BASE+BASE+BASE+BASE+"<news:language>de</news:language>\n"
              + BASE+BASE+BASE+"</news:publication>\n"
              + BASE+BASE+BASE+"<news:publication_date>1970-01-01T00:00:00Z</news:publication_date>\n"
              + BASE+BASE+BASE+"<news:title>Beispieltitel</news:title>\n"
              + BASE+BASE+BASE+"<news:keywords>Klaatu, Barrata, Nicto</news:keywords>\n"
              + BASE+BASE+"</news:news>\n"
              + BASE+"</url>\n"
              + "</urlset>";
        // @formatter:on
        String sitemap = writeSingleSiteMap(wsg);
        assertEquals(expected, sitemap);
    }

    private String writeSingleSiteMap(GoogleNewsSitemapGenerator wsg) {
        List<File> files = wsg.write();
        assertEquals("Too many files: " + files.toString(), 1, files.size());
        assertEquals("Sitemap misnamed", "sitemap.xml", files.get(0).getName());
        return TestUtil.slurpFileAndDelete(files.get(0));
    }
}
