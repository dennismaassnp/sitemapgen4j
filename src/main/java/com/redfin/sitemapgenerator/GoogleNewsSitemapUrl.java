package com.redfin.sitemapgenerator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * One configurable Google News Search URL. To configure, use {@link Options}
 *
 * @author Dan Fabulich
 * @see Options
 * @see <a href="http://www.google.com/support/news_pub/bin/answer.py?answer=74288">Creating a News Sitemap</a>
 */
public class GoogleNewsSitemapUrl extends WebSitemapUrl {

    private final String publicationDate;
    private final String keywords;
    private final String title;
    private final String publicationName;
    private final String publicationLanguage;
    private final List<String> genres;
    private final List<String> stockTickers;
    private final Access access;

    /** Options to configure Google News URLs */
    public static class Options extends AbstractSitemapUrlOptions<GoogleNewsSitemapUrl, Options> {
        private String publicationDate;
        private String keywords;
        private String title;
        private String publicationName;
        private String publicationLanguage;
        private List<String> genres = new ArrayList<String>();
        private List<String> stockTickers = new ArrayList<String>();
        private Access access;

        /** Specifies an URL and publication date (which is mandatory for Google News) */
        public Options(String url, String title, String publicationDate, String publicationName,
                String publicationLanguage) throws MalformedURLException {
            this(new URL(url), title, publicationDate, publicationName, publicationLanguage);
        }

        /** Specifies an URL and publication date (which is mandatory for Google News) */
        public Options(URL url, String title, String publicationDate, String publicationName, String publicationLanguage) {
            super(url, GoogleNewsSitemapUrl.class);
            this.title = title;
            this.publicationName = publicationName;
            this.publicationLanguage = publicationLanguage;
            if (publicationDate == null) {
                throw new NullPointerException("publicationDate must not be null");
            }
            this.publicationDate = publicationDate;

        }

        public Options title(String title) {
            this.title = title;
            return this;
        }

        public Options publicationName(String publicationName) {
            this.publicationName = publicationName;
            return this;
        }

        public Options publicationLanguage(String publicationLanguage) {
            this.publicationLanguage = publicationLanguage;
            return this;
        }

        public Options genres(List<String> genres) {
            this.genres = genres;
            return this;
        }

        public Options stockTickers(List<String> stockTickers) {
            this.stockTickers = stockTickers;
            return this;
        }

        public Options access(Access access) {
            this.access = access;
            return this;
        }

        /** Specifies a list of comma-delimited keywords */
        public Options keywords(String keywords) {
            this.keywords = keywords;
            return this;
        }

        /** Specifies a list of comma-delimited keywords */
        public Options keywords(Iterable<String> keywords) {
            StringBuilder sb = new StringBuilder();
            boolean first = true;
            for (String keyword : keywords) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                sb.append(keyword);
            }
            this.keywords = sb.toString();
            return this;
        }

        /** Specifies a list of comma-delimited keywords */
        public Options keywords(String... keywords) {
            return keywords(Arrays.asList(keywords));
        }

    }

    /** Specifies an URL and publication date (which is mandatory for Google News) */
    public GoogleNewsSitemapUrl(URL url, String title, String publicationDate, String publicationName,
            String publicationLanguage) {
        this(new Options(url, title, publicationDate, publicationName, publicationLanguage));
    }

    /** Specifies an URL and publication date (which is mandatory for Google News) */
    public GoogleNewsSitemapUrl(String url, String title, String publicationDate, String publicationName,
            String publicationLanguage) throws MalformedURLException {
        this(new Options(url, title, publicationDate, publicationName, publicationLanguage));
    }

    /** Configures an URL with options */
    public GoogleNewsSitemapUrl(Options options) {
        super(options);
        publicationDate = options.publicationDate;
        keywords = options.keywords;
        title = options.title;
        publicationName = options.publicationName;
        publicationLanguage = options.publicationLanguage;
        genres = options.genres;
        stockTickers = options.stockTickers;
        access = options.access;
    }

    /** Retrieves the publication date */
    public String getPublicationDate() {
        return publicationDate;
    }

    /** Retrieves the list of comma-delimited keywords */
    public String getKeywords() {
        return keywords;
    }

    public String getTitle() {
        return title;
    }

    public String getPublicationName() {
        return publicationName;
    }

    public String getPublicationLanguage() {
        return publicationLanguage;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<String> getStockTickers() {
        return stockTickers;
    }

    public Access getAccess() {
        return access;
    }

}
