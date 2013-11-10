package ru.compscicenter.trends.source.cleaning;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @author alexeyev
 */
public class TechcrunchCleaningTest implements HtmlSourceCleaningTestable {

    @Test
    public void testDate() throws IOException {
        //<meta name='sailthru.date' content='2007-12-07 18:28:47' />
        final Date date = extractor.getDate();
        assert (date != null);
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 7);
        cal.set(Calendar.YEAR, 2007);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        assert (cal.getTime().equals(date));
    }

    @Test
    public void testTitle() throws IOException {
        final String title = extractor.getTitle();
        assert (title != null);
        assert (title.equals("Ask and Wii shall receive: Guitar Hero 3 in Stereo"));
    }

    @Test
    public void testTags() throws IOException {
        Set<String> returnedSet = extractor.getTags();
        assert (returnedSet != null);
        assert (!returnedSet.isEmpty());
        Set<String> testSet = new HashSet<String>(Arrays.asList(
                "Activision", "Nintendo", "Wii", "Guitar Hero", "CrunchArcade", "Headline"));
        assert (returnedSet.equals(testSet));
    }

    @Test
    public void testText() throws IOException {
        String text = extractor.getText();
        assert (text != null);
        assert (!text.matches(".*</?\\w+.*>.*"));
    }

    @Test
    public void testLinks() throws IOException {
        final Set<String> links = extractor.getLinks();

        System.out.println(links);
        for (String link : links) {
            // validity check
            final URL tryUrl = new URL(link);
        }
        assert (!links.isEmpty());
    }

    private final String html = "<!DOCTYPE html>\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:og=\"http://opengraphprotocol.org/schema/\" xmlns:fb=\"http://www.facebook.com/2008/fbml\" lang=\"en\">\n" +
            "<head>\n" +
            "\t<title>Ask and Wii shall receive: Guitar Hero 3 in Stereo  |  TechCrunch</title>\n" +
            "\t<meta charset=\"UTF-8\">\n" +
            "\t\t\t<script type=\"text/javascript\">var _sf_startpt = (new Date()).getTime()</script>\n" +
            "\t\t<meta name=\"HandheldFriendly\" content=\"True\">\n" +
            "\t<meta name=\"MobileOptimized\" content=\"320\">\n" +
            "\t<meta name=\"viewport\" content=\"initial-scale=1.0,width=device-width,user-scalable=no,minimum-scale=1.0,maximum-scale=1.0\">\n" +
            "\t<meta http-equiv=\"cleartype\" content=\"on\">\n" +
            "\t<meta name=\"apple-mobile-web-app-title\" content=\"TechCrunch\">\n" +
            "\t<link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"http://s1.wp.com/wp-content/themes/vip/techcrunch-2013/assets/images/favicon.ico?m=1381204869g\" />\n" +
            "\t<meta name=\"google-site-verification\" content=\"4U1OC1LwZlFHAehLhCV4rt3YzWI_AyF7Gb0XqlaVEhE\" />\n" +
            "<meta name=\"msvalidate.01\" content=\"5ABD8A078F3356F3A6A8C8643C31FB8F\" />\n" +
            "\t\t<script src='http://r-login.wordpress.com/remote-login.php?action=js&amp;host=techcrunch.com&amp;id=24588526&amp;t=1382913785&amp;back=techcrunch.com%2F2007%2F12%2F07%2Fask-and-wii-shall-receive-guitar-hero-3-in-stereo%2F' type=\"text/javascript\"></script>\n" +
            "\t\t<script type=\"text/javascript\">\n" +
            "\t\t/* <![CDATA[ */\n" +
            "\t\t\tif ( 'function' === typeof WPRemoteLogin ) {\n" +
            "\t\t\t\tdocument.cookie = \"wordpress_test_cookie=test; path=/\";\n" +
            "\t\t\t\tif ( document.cookie.match( /(;|^)\\s*wordpress_test_cookie\\=/ ) ) {\n" +
            "\t\t\t\t\tWPRemoteLogin();\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}\n" +
            "\t\t/* ]]> */\n" +
            "\t\t</script>\n" +
            "\t\t<link rel=\"alternate\" type=\"application/rss+xml\" title=\"TechCrunch &raquo; Feed\" href=\"http://techcrunch.com/feed/\" />\n" +
            "<link rel=\"alternate\" type=\"application/rss+xml\" title=\"TechCrunch &raquo; Comments Feed\" href=\"http://techcrunch.com/comments/feed/\" />\n" +
            "<link rel=\"alternate\" type=\"application/rss+xml\" title=\"TechCrunch &raquo; Ask and Wii shall receive: Guitar Hero 3 in&nbsp;Stereo Comments Feed\" href=\"http://techcrunch.com/2007/12/07/ask-and-wii-shall-receive-guitar-hero-3-in-stereo/feed/\" />\n" +
            "<script type=\"text/javascript\">\n" +
            "/* <![CDATA[ */\n" +
            "function addLoadEvent(func){var oldonload=window.onload;if(typeof window.onload!='function'){window.onload=func;}else{window.onload=function(){oldonload();func();}}}\n" +
            "/* ]]> */\n" +
            "</script>\n" +
            "<link rel='stylesheet' id='all-css-0' href='http://s1.wp.com/_static/??-eJx9jkkOwjAQBD+EmTiJWA6Itxh7sJ14k2dMxO+JIm4sx5aqWgVLETonxsQQmyihWZ8Igp+RYEIuSs9iW3tNtIPveMnE4h6Ur0D8DPjBssO4Hj58AUbtdG1JO9F3cngL0adfkhvBhnxT4V/B4o1FJlgRsDmbispsydd4kcOpP47ycO6mF97tV3Y=' type='text/css' media='all' />\n" +
            "<script type='text/javascript'>\n" +
            "/* <![CDATA[ */\n" +
            "var Live_Cache = {\"ajaxurl\":\"http:\\/\\/techcrunch.com\",\"auto_updates\":{\"live-cache-widget-1-title\":\"#live-cache-widget-1 h2\",\"live-cache-widget-1-text\":\"#live-cache-widget-1 div\",\"live-cache-title\":\".live-subtitle\",\"live-cache-text\":\".vid-caption\",\"live-cache-title-livecaster\":\".livecaster-title\",\"live-cache-text-livecaster\":\".livecaster-text\"}};\n" +
            "var gravityInsightsParams = {\"type\":\"content\",\"site_guid\":\"fca4fa8af1286d8a77f26033fdeed202\"};\n" +
            "var tcOmniture = {\"pageName\":\"Ask and Wii shall receive: Guitar Hero 3 in Stereo\",\"prop1\":\"article\",\"prop2\":\"gadgets\",\"prop54\":\"wordpress\",\"prop9\":\"tcr:17708\",\"prop16\":\"standard-article\",\"prop19\":\"activision|nintendo|wii|guitar hero|cruncharcade|headline\",\"prop22\":\"devin-coldewey\",\"prop23\":\"12-07-2007\",\"omni_env\":\"prod\"};\n" +
            "var TC_Async_Head_Scripts = {\"tc_ads_wrapper_omniture\":\"http:\\/\\/o.aolcdn.com\\/os_merge\\/?file=\\/moat\\/prod\\/moatuac.js&file=\\/ads\\/adsWrapper.js&file=\\/omniture\\/prod\\/omniunih.js\"};\n" +
            "/* ]]> */\n" +
            "</script>\n" +
            "<script type='text/javascript' src='http://s2.wp.com/_static/??-eJyFj+sKwjAMhV/IWrsh+Ed8lq5mW+p6sUk39OmtMvHCYORHIOdLzomcojDBM3iWlqQLDQ4gMkHSXZkJ9G3YWtrIwqE3Qz4DPUF7zZBuc1sFhMMuaYatQ/+Gv1xjIHZAVCwX1F8r9CPCtIpZ4KjNRSQgvMNSQJra0Fgw/H+Leyhh5IhRMpjepOxNL6qdqqUmAn5tD9iIjzr/dXJHVR8qVWqv7AP5VYCC'></script>\n" +
            "<link rel='stylesheet' id='all-css-0' href='http://s2.wp.com/wp-content/mu-plugins/highlander-comments/style.css?m=1377793621g' type='text/css' media='all' />\n" +
            "<!--[if lt IE 8]>\n" +
            "<link rel='stylesheet' id='highlander-comments-ie7-css'  href='http://s2.wp.com/wp-content/mu-plugins/highlander-comments/style-ie7.css?m=1351637563g&#038;ver=20110606' type='text/css' media='all' />\n" +
            "<![endif]-->\n" +
            "<link rel=\"EditURI\" type=\"application/rsd+xml\" title=\"RSD\" href=\"http://tctechcrunch2011.wordpress.com/xmlrpc.php?rsd\" />\n" +
            "<link rel=\"wlwmanifest\" type=\"application/wlwmanifest+xml\" href=\"http://tctechcrunch2011.wordpress.com/wp-includes/wlwmanifest.xml\" /> \n" +
            "<link rel='prev' title='Zygo might be the UK&#039;s Twitter, but with&nbsp;revenues' href='http://techcrunch.com/2007/12/07/zygo-might-be-the-uks-twitter-but-with-revenues/' />\n" +
            "<link rel='next' title='Major League Gaming&#039;s A40 Headphones make me all wobbly&nbsp;inside' href='http://techcrunch.com/2007/12/07/major-league-gamings-a40-headphones-make-me-all-wobbly-inside/' />\n" +
            "<meta name=\"generator\" content=\"WordPress.com\" />\n" +
            "<link rel='canonical' href='http://techcrunch.com/2007/12/07/ask-and-wii-shall-receive-guitar-hero-3-in-stereo/' />\n" +
            "<link rel='shortlink' href='http://wp.me/p1FaB8-4BC' />\n" +
            "<link rel=\"alternate\" type=\"application/json+oembed\" href=\"http://public-api.wordpress.com/oembed/1.0/?format=json&amp;url=http%3A%2F%2Ftechcrunch.com%2F2007%2F12%2F07%2Fask-and-wii-shall-receive-guitar-hero-3-in-stereo%2F&amp;for=wpcom-auto-discovery\" /><link rel=\"alternate\" type=\"application/xml+oembed\" href=\"http://public-api.wordpress.com/oembed/1.0/?format=xml&amp;url=http%3A%2F%2Ftechcrunch.com%2F2007%2F12%2F07%2Fask-and-wii-shall-receive-guitar-hero-3-in-stereo%2F&amp;for=wpcom-auto-discovery\" /><link rel='openid.server' href='http://tctechcrunch2011.wordpress.com/?openidserver=1' />\n" +
            "<link rel='openid.delegate' href='http://tctechcrunch2011.wordpress.com/' />\n" +
            "<link rel=\"search\" type=\"application/opensearchdescription+xml\" href=\"http://techcrunch.com/osd.xml\" title=\"TechCrunch\" />\n" +
            "<link rel=\"search\" type=\"application/opensearchdescription+xml\" href=\"http://wordpress.com/opensearch.xml\" title=\"WordPress.com\" />\n" +
            "\t\t<style>\n" +
            "\t\t/* <![CDATA[ */\n" +
            "\t\t/* Block: reblog */\n" +
            "\t\t\n" +
            "\t\t.reblog-from img                   { margin: 0 10px 0 0; vertical-align: middle; padding: 0; border: 0; }\n" +
            "\t\t.reblogger-note img.avatar         { float: left; padding: 0; border: 0; }\n" +
            "\t\t.reblogger-note-content            { margin: 0 0 20px; }\n" +
            "\t\t.reblog-post .wpcom-enhanced-excerpt-content { border-left: 3px solid #eee; padding-left: 15px; }\n" +
            "\t\t.reblog-post ul.thumb-list         { display: block; list-style: none; margin: 2px 0; padding: 0; clear: both; }\n" +
            "\t\t.reblog-post ul.thumb-list li      { display: inline; margin: 0; padding: 0 1px; border: 0; }\n" +
            "\t\t.reblog-post ul.thumb-list li a    { margin: 0; padding: 0; border: 0; }\n" +
            "\t\t.reblog-post ul.thumb-list li img  { margin: 0; padding: 0; border: 0; }\n" +
            "\t\t\n" +
            "\t\t.reblog-post .wpcom-enhanced-excerpt { clear: both; }\n" +
            "\t\t\n" +
            "\t\t.reblog-post .wpcom-enhanced-excerpt address,\n" +
            "\t\t.reblog-post .wpcom-enhanced-excerpt li,\n" +
            "\t\t.reblog-post .wpcom-enhanced-excerpt h1,\n" +
            "\t\t.reblog-post .wpcom-enhanced-excerpt h2,\n" +
            "\t\t.reblog-post .wpcom-enhanced-excerpt h3,\n" +
            "\t\t.reblog-post .wpcom-enhanced-excerpt h4,\n" +
            "\t\t.reblog-post .wpcom-enhanced-excerpt h5,\n" +
            "\t\t.reblog-post .wpcom-enhanced-excerpt h6,\n" +
            "\t\t.reblog-post .wpcom-enhanced-excerpt p { font-size: 100% !important; }\n" +
            "\t\t\n" +
            "\t\t.reblog-post .wpcom-enhanced-excerpt blockquote,\n" +
            "\t\t.reblog-post .wpcom-enhanced-excerpt pre,\n" +
            "\t\t.reblog-post .wpcom-enhanced-excerpt code,\n" +
            "\t\t.reblog-post .wpcom-enhanced-excerpt q { font-size: 98% !important; }\n" +
            "\t\t\n" +
            "\n" +
            "\t\t/* ]]> */\n" +
            "\t\t</style>\n" +
            "\t\t<!-- BEGIN Sailthru Horizon Meta Information -->\n" +
            "\n" +
            "<meta name='sailthru.date' content='2007-12-07 18:28:47' />\n" +
            "<meta name='sailthru.title' content='Ask and Wii shall receive: Guitar Hero 3 in&nbsp;Stereo' />\n" +
            "<meta name='sailthru.tags' content='Activision, Nintendo, Wii, Guitar Hero, CrunchArcade, Headline' />\n" +
            "<meta name='sailthru.author' content='&lt;a href=&quot;/author/devin-coldewey/&quot; title=&quot;Posts by Devin Coldewey&quot; onclick=&quot;s_objectID=\\&#039;river_author\\&#039;;&quot; rel=&quot;author&quot;&gt;Devin Coldewey&lt;/a&gt;' />\n" +
            "<meta name='sailthru.description' content='It&#8217;s a good month for customers, at least if you believe what you read.' />\n" +
            "\n" +
            "\n" +
            "<!-- END Sailthru Horizon Meta Information -->\t<meta property=\"og:site_name\" content=\"TechCrunch\" />\n" +
            "\t<meta property=\"og:url\" content=\"http://techcrunch.com/2007/12/07/ask-and-wii-shall-receive-guitar-hero-3-in-stereo/\" />\n" +
            "\t<meta property=\"og:app_id\" content=\"187288694643718\" />\n" +
            "\t<meta property=\"og:title\" content=\"Ask and Wii shall receive: Guitar Hero 3 in Stereo | TechCrunch\" />\n" +
            "\t<meta property=\"og:type\" content=\"article\" />\n" +
            "\t<meta property=\"og:image\" content=\"http://i1.wp.com/old.crunchgear.com/wp-content/uploads/2007/12/guitar-hero-3-wii-a.jpg?resize=150%2C150\" />\n" +
            "\t<meta property=\"og:description\" content=\"TechCrunch is a leading technology media property, dedicated to obsessively profiling startups, reviewing new Internet products, and breaking tech news.\" />\n" +
            "\t<meta property=\"fb:admins\" content=\"605136095,1076790301,500065899,543710097,216760,500024101,771265067,1661021707,1550970059,579140017,1060873546,663677613\" />\n" +
            "\t<meta property=\"fb:app_id\" content=\"187288694643718\" />\n" +
            "<meta name='twitter:card' content='summary' />\n" +
            "<meta name='twitter:site' content='@techcrunch' />\n" +
            "<meta name='twitter:url' content='http://techcrunch.com/2007/12/07/ask-and-wii-shall-receive-guitar-hero-3-in-stereo/' />\n" +
            "<meta name='twitter:description' content='' />\n" +
            "<meta name='twitter:title' content='|  Ask and Wii shall receive: Guitar Hero 3 in StereoTechCrunch' />\n" +
            "\t<meta name=\"description\" content=\"TechCrunch is a leading technology media property, dedicated to obsessively profiling startups, reviewing new Internet products, and breaking tech news.\" />\n" +
            "\t<script type=\"text/javascript\">var ajaxurl = \"http://techcrunch.com/wp-admin/admin-ajax.php\"</script><meta name=\"application-name\" content=\"TechCrunch\" /><meta name=\"msapplication-window\" content=\"width=device-width;height=device-height\" /><meta name=\"msapplication-tooltip\" content=\"Startup and Technology News\" /><meta name=\"msapplication-task\" content=\"name=Subscribe;action-uri=http://techcrunch.com/feed/;icon-uri=http://0.gravatar.com/blavatar/ad5b2a2b10ddd8fc4e4588abd4e12a84?s=16\" />\n" +
            "\t<style type=\"text/css\">#site-logo { background: url(http://s1.wp.com/wp-content/themes/vip/techcrunch-2013/images/logos/green.png) no-repeat !important; }</style>\n" +
            "<style id=\"syntaxhighlighteranchor\"></style>\n" +
            "\t<!--[if lte IE 8 ]>\n" +
            "\t<script src=\"http://s1.wp.com/wp-content/themes/vip/techcrunch-2013/assets/js/respond.min.js?m=1381204869g\"></script>\n" +
            "\t<![endif]-->\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "<header class=\"header cf\" role=\"banner\">\n" +
            "\t<div class=\"nav-bar\">\n" +
            "\t\t<div class=\"lc\">\n" +
            "\t\t\t<div class=\"header-logo-bar\">\n" +
            "\t\t\t\t<div class=\"header-ad\">\n" +
            "\t\t\t\t\t<a href=\"\" target=\"_blank\"><div id=\"adsDiv9084fb9b99\"></div>\n" +
            "<script>\n" +
            "if (!ads.isMobile()) {\n" +
            "\t(function(window,$){\n" +
            "\t\twindow.TechCrunch.loader.on('tc_ads_wrapper_omniture', function(){\n" +
            "\t\t\tif ( !window.ads.isAdPageSet ){\n" +
            "\t\t\t\tif ( typeof window.adSetAdURL == 'function' ) {\n" +
            "\t\t\t\t\twindow.adSetAdURL('/wp-content/themes/vip/techcrunch-2013/_uac/adpage.html');\n" +
            "\t\t\t\t\twindow.ads.isAdPageSet = true;\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}\n" +
            "\t\t\twindow.htmlAdWH('93311079', \"LB\", \"LB\", 'f', 'adsDiv9084fb9b99');\n" +
            "\t\t});\n" +
            "\t}(this,this.jQuery));\n" +
            "}\n" +
            "</script></a>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t\t<a href=\"/\" class=\"logo-link\" title=\"TechCrunch\" data-omni-sm=\"gbl_topnav\"><img src=\"http://s1.wp.com/wp-content/themes/vip/techcrunch-2013/assets/images/logo.svg\" alt=\"TechCrunch\" class=\"logo\"></a>\n" +
            "\t\t\t\t<div class=\"header-tip\">\n" +
            "\t\t\t\t\t<a href=\"/got-a-tip/\" data-omni-sm=\"gbl_topnav\">\n" +
            "\t\t\t\t\t\tGot a tip? <span>Let us know.</span>\n" +
            "\t\t\t\t\t</a>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\t\t\t<a href=\"#\" class=\"toggle-link nav-toggle icon-hamburger\"><span class=\"is-vishidden\">Menu</span></a>\n" +
            "\t\t\t<a href=\"#\" class=\"toggle-link search-form-toggle icon-mag\"><span class=\"is-vishidden\">Search</span></a>\n" +
            "\t\t\t<nav class=\"nav-primary\">\n" +
            "\t<ul class=\"nav\" id=\"nav\">\n" +
            "\t\t<li class=\"nav-level1 nav-news\">\n" +
            "\t\t\t<a href=\"/\" class=\"nav-parent icon-caret-down\">News</a>\n" +
            "\t\t\t<div class=\"nav-subnav\">\n" +
            "\t\t\t\t<ul class=\"nav-col\">\n" +
            "\t\t\t\t\t<li class=\"subnav-title\">Channels</li>\n" +
            "\t\t\t\t\t<ul class=\"subnav-channel\" data-omni-sm-delegate=\"gbl_mainnav\"><li id=\"menu-item-899745\" class=\"menu-item menu-item-type-taxonomy menu-item-object-category menu-item-899745\"><a href=\"http://techcrunch.com/startups/\">Startups</a></li>\n" +
            "<li id=\"menu-item-899746\" class=\"menu-item menu-item-type-taxonomy menu-item-object-category menu-item-899746\"><a href=\"http://techcrunch.com/mobile/\">Mobile</a></li>\n" +
            "<li id=\"menu-item-899747\" class=\"menu-item menu-item-type-taxonomy menu-item-object-category current-post-ancestor current-menu-parent current-post-parent menu-item-899747\"><a href=\"http://techcrunch.com/gadgets/\">Gadgets</a></li>\n" +
            "<li id=\"menu-item-899748\" class=\"menu-item menu-item-type-taxonomy menu-item-object-category menu-item-899748\"><a href=\"http://techcrunch.com/enterprise/\">Enterprise</a></li>\n" +
            "<li id=\"menu-item-899749\" class=\"menu-item menu-item-type-taxonomy menu-item-object-category menu-item-899749\"><a href=\"http://techcrunch.com/social/\">Social</a></li>\n" +
            "<li id=\"menu-item-899750\" class=\"menu-item menu-item-type-taxonomy menu-item-object-category menu-item-899750\"><a href=\"http://techcrunch.com/europe/\">Europe</a></li>\n" +
            "<li id=\"menu-item-901944\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-901944\"><a href=\"/category/asia/\">Asia</a></li>\n" +
            "<li id=\"menu-item-899752\" class=\"menu-item menu-item-type-post_type menu-item-object-page menu-item-899752\"><a href=\"http://techcrunch.com/crunchgov/\">CrunchGov</a></li>\n" +
            "<li id=\"menu-item-899786\" class=\"menu-item menu-item-type-post_type menu-item-object-page menu-item-899786\"><a href=\"http://techcrunch.com/crunchu-course-listing/\">CrunchU</a></li>\n" +
            "</ul>\t\t\t\t</ul>\n" +
            "\t\t\t\t<ul class=\"nav-col\">\n" +
            "\t\t\t\t\t<li class=\"subnav-title\">Trending</li>\n" +
            "\t\t\t\t\t<ul class=\"subnav-news-trending\" data-omni-sm-delegate=\"gbl_mainnav\"><li id=\"menu-item-899775\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-899775\"><a href=\"http://techcrunch.com/tag/yahoo\">Yahoo</a></li>\n" +
            "<li id=\"menu-item-899776\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-899776\"><a href=\"http://techcrunch.com/tag/apple\">Apple</a></li>\n" +
            "<li id=\"menu-item-899777\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-899777\"><a href=\"http://techcrunch.com/tag/facebook\">Facebook</a></li>\n" +
            "<li id=\"menu-item-899778\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-899778\"><a href=\"http://techcrunch.com/tag/twitter\">Twitter</a></li>\n" +
            "<li id=\"menu-item-899779\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-899779\"><a href=\"http://techcrunch.com/tag/google\">Google</a></li>\n" +
            "<li id=\"menu-item-899780\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-899780\"><a href=\"http://techcrunch.com/tag/microsoft\">Microsoft</a></li>\n" +
            "<li id=\"menu-item-899781\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-899781\"><a href=\"http://techcrunch.com/tag/nsa\">NSA</a></li>\n" +
            "</ul>\t\t\t\t</ul>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t</li>\n" +
            "\t\t<li class=\"nav-level1 nav-shows\">\n" +
            "\t\t\t<a href=\"/video\" class=\"nav-parent icon-caret-down\">TCTV</a>\n" +
            "\t \t\t<div  class=\"nav-subnav\">\n" +
            "\t\t\t\t<div class=\"subnav-title\">Shows</div>\n" +
            "\t\t\t\t<ul class=\"nav-col\">\n" +
            "\t\t\t\t\t<ul class=\"subnav-tctv-shows-left\" data-omni-sm-delegate=\"gbl_mainnav\"><li id=\"menu-item-899753\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-899753\"><a href=\"http://techcrunch.com/video/tctv-news/\">TCTV News</a></li>\n" +
            "<li id=\"menu-item-899754\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-899754\"><a href=\"http://techcrunch.com/video/ask-a-vc/\">Ask A VC</a></li>\n" +
            "<li id=\"menu-item-899755\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-899755\"><a href=\"http://techcrunch.com/video/crunchweek/\">CrunchWeek</a></li>\n" +
            "<li id=\"menu-item-899756\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-899756\"><a href=\"http://techcrunch.com/video/fly-or-die/\">Fly Or Die</a></li>\n" +
            "<li id=\"menu-item-899757\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-899757\"><a href=\"http://techcrunch.com/video/foundation/\">Foundation</a></li>\n" +
            "<li id=\"menu-item-899758\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-899758\"><a href=\"http://techcrunch.com/video/founder-stories/\">Founder Stories</a></li>\n" +
            "</ul>\t\t\t\t</ul>\n" +
            "\t\t\t\t<ul class=\"nav-col\">\n" +
            "\t\t\t\t\t<ul class=\"subnav-tctv-shows-left\" data-omni-sm-delegate=\"gbl_mainnav\"><li id=\"menu-item-899759\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-899759\"><a href=\"http://techcrunch.com/video/techcrunch-gadgets/\">TechCrunch Gadgets</a></li>\n" +
            "<li id=\"menu-item-899760\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-899760\"><a href=\"http://techcrunch.com/video/gillmor-gang/\">Gillmor Gang</a></li>\n" +
            "<li id=\"menu-item-899761\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-899761\"><a href=\"http://techcrunch.com/video/keen-on/\">Keen On</a></li>\n" +
            "<li id=\"menu-item-899762\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-899762\"><a href=\"http://techcrunch.com/video/tc-cribs/\">TC Cribs</a></li>\n" +
            "<li id=\"menu-item-899763\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-899763\"><a href=\"http://techcrunch.com/video/techcrunch-makers/\">TechCrunch Makers</a></li>\n" +
            "</ul>\t\t\t\t</ul>\n" +
            "\t\t\t\t<a href=\"/video\" class=\"subnav-secondary-link\">All Shows</a>\n" +
            "\t\t\t</div>\n" +
            "\t\t</li>\n" +
            "\t\t<li class=\"nav-level1 nav-events\">\n" +
            "\t\t\t<a href=\"/events\" class=\"nav-parent icon-caret-down\">Events</a>\n" +
            "\t\t\t<div class=\"nav-subnav\">\n" +
            "\t\t\t\t<ul class=\"nav-col\">\n" +
            "\t\t\t\t\t<li class=\"subnav-title\">TechCrunch Events</li>\n" +
            "\t\t\t\t\t<ul class=\"subnav-events-tc-events\" data-omni-sm-delegate=\"gbl_mainnav\"><li id=\"menu-item-899739\" class=\"menu-item menu-item-type-taxonomy menu-item-object-tc_event menu-item-899739\"><a href=\"http://techcrunch.com/event-type/disrupt/\">Disrupt</a></li>\n" +
            "<li id=\"menu-item-899740\" class=\"menu-item menu-item-type-taxonomy menu-item-object-tc_event menu-item-899740\"><a href=\"http://techcrunch.com/event-type/crunchies-23/\">Crunchies</a></li>\n" +
            "<li id=\"menu-item-899741\" class=\"menu-item menu-item-type-taxonomy menu-item-object-tc_event menu-item-899741\"><a href=\"http://techcrunch.com/event-type/meetups/\">Meetups</a></li>\n" +
            "<li id=\"menu-item-899742\" class=\"menu-item menu-item-type-taxonomy menu-item-object-tc_event menu-item-899742\"><a href=\"http://techcrunch.com/event-type/international-city/\">International City Events</a></li>\n" +
            "<li id=\"menu-item-899744\" class=\"menu-item menu-item-type-taxonomy menu-item-object-tc_event menu-item-899744\"><a href=\"http://techcrunch.com/event-type/hackathon-2/\">Hackathon</a></li>\n" +
            "</ul>\t\t\t\t</ul>\n" +
            "\t\t\t\t<ul class=\"nav-col\">\n" +
            "\t\t\t\t\t<li class=\"subnav-title\">News About</li>\n" +
            "\t\t\t\t\t<ul class=\"subnav-events-news-about\" data-omni-sm-delegate=\"gbl_mainnav\"><li id=\"menu-item-899736\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-899736\"><a href=\"http://techcrunch.com/event-type/sxsw-2/\">SXSW</a></li>\n" +
            "<li id=\"menu-item-899737\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-899737\"><a href=\"http://techcrunch.com/event-type/ces/\">CES</a></li>\n" +
            "<li id=\"menu-item-899738\" class=\"menu-item menu-item-type-custom menu-item-object-custom menu-item-899738\"><a href=\"http://techcrunch.com/event-type/e3-2/\">E3</a></li>\n" +
            "</ul>\t\t\t\t</ul>\n" +
            "\t\t\t\t<a href=\"/events\" class=\"subnav-secondary-link\">All Events</a>\n" +
            "\t\t\t</div>\n" +
            "\t\t</li>\n" +
            "\t</ul>\n" +
            "</nav>\t\t\t<form action=\"/\" method=\"get\" class=\"search-form\">\n" +
            "\t\t\t    <fieldset>\n" +
            "\t\t\t        <legend>Search TechCrunch</legend>\n" +
            "\t\t\t        <label for=\"s\">Search TechCrunch</label>\n" +
            "\t\t\t        <input type=\"search\" placeholder=\"Search\" class=\"search-field\" name=\"s\" value=\"\" />\n" +
            "\t\t\t        \t\t\t        <button class=\"search-submit\">\n" +
            "\t\t\t            <span class=\"icon-mag\" aria-hidden=\"true\"></span>\n" +
            "\t\t\t            <span class=\"is-vishidden\">Search</span>\n" +
            "\t\t\t        </button>\n" +
            "\t\t\t    </fieldset>\n" +
            "\t\t\t</form>\n" +
            "\n" +
            "\t\t\t<div class=\"header-crunchbase\">\n" +
            "\t\t\t\t<a href=\"http://crunchbase.com\" rel=\"external\" target=\"_blank\">CrunchBase</a>\n" +
            "\t\t\t</div>\n" +
            "\t\t\t<div class=\"header-social\">\n" +
            "\t\t\t\t<ul class=\"inline-list social-list sprite-social\">\n" +
            "\t\t\t\t\t<li class=\"nav-followus\">Follow Us</li>\n" +
            "\t\t\t\t\t<li><a href=\"https://www.facebook.com/techcrunch\" rel=\"external\" class=\"spricon i-facebook\" target=\"_blank\"><span class=\"is-vishidden\">Facebook</span></a></li>\n" +
            "\t\t\t\t\t<li><a href=\"https://twitter.com/techcrunch\" rel=\"external\" class=\"spricon i-twitter\" target=\"_blank\"><span class=\"is-vishidden\">Twitter</span></a></li>\n" +
            "\t\t\t\t\t<li><a href=\"https://plus.google.com/+TechCrunch\" rel=\"external\" class=\"spricon i-google-plus\" target=\"_blank\"><span class=\"is-vishidden\">Google+</span></a></li>\n" +
            "\t\t\t\t\t<li class=\"nav-aux linkedin\"><a href=\"http://www.linkedin.com/company/techcrunch\" rel=\"external\" class=\"spricon i-linkedin\" target=\"_blank\"><span class=\"is-vishidden\">LinkedIn</span></a></li>\n" +
            "\t\t\t\t\t<li class=\"nav-aux youtube\"><a href=\"http://www.youtube.com/user/techcrunch\" rel=\"external\" class=\"spricon i-youtube\" target=\"_blank\"><span class=\"is-vishidden\">Youtube</span></a></li>\n" +
            "\t\t\t\t\t<li class=\"nav-aux rss\"><a href=\"/rssfeeds/\" class=\"spricon i-feed\"><span class=\"is-vishidden\">RSS</span></a></li>\n" +
            "\t\t\t\t\t<li class=\"nav-aux email\"><a href=\"/crunch-daily/\" class=\"spricon i-envelope\"><span class=\"is-vishidden\">Email</span></a></li>\n" +
            "\t\t\t\t\t<li class=\"nav-social-more\">\n" +
            "\t\t\t\t\t\t<a href=\"#\" class=\"icon-caret-down\"><span class=\"is-vishidden\">More</span></a>\n" +
            "\t\t\t\t\t\t<ul>\n" +
            "\t\t\t\t\t\t\t<li><a href=\"http://www.linkedin.com/company/techcrunch\" class=\"spricon i-linkedin\" rel=\"external\" target=\"_blank\"><span class=\"is-vishidden\">LinkedIn</span></a></li>\n" +
            "\t\t\t\t\t\t\t<li><a href=\"http://www.youtube.com/user/techcrunch\" class=\"spricon i-youtube\" rel=\"external\" target=\"_blank\"><span class=\"is-vishidden\">Youtube</span></a></li>\n" +
            "\t\t\t\t\t\t\t<li><a href=\"/rssfeeds/\" class=\"spricon i-feed\"><span class=\"is-vishidden\" target=\"_blank\">RSS</span></a></li>\n" +
            "\t\t\t\t\t\t\t<li><a href=\"#\" class=\"spricon i-envelope\"><span class=\"is-vishidden\">Email</span></a></li>\n" +
            "\t\t\t\t\t\t</ul>\n" +
            "\t\t\t\t\t</li>\n" +
            "\t\t\t\t</ul>\n" +
            "\t\t\t</div>\n" +
            "\t\t</div>\n" +
            "\t</div>\n" +
            "</header>\n" +
            "\t\t<div class=\"announcement\">\n" +
            "\t\t\t<div class=\"lc\">\n" +
            "\t\t\t\t<a href=\"http://techcrunch.com/events/disrupt-europe-berlin-2013/purchase-tickets/?ncid=101613\">\n" +
            "\t\t\t\t\t<strong>Disrupt Europe News</strong>&nbsp;Limited tickets are still available. Get yours today!\t\t\t\t</a>\n" +
            "\t\t\t</div>\n" +
            "\t\t</div>\n" +
            "\t\t\n" +
            "<!-- Begin: Article Body Content - Main -->\n" +
            "<div role=\"main\" class=\"fluid\" style=\"\">\n" +
            "\t<!-- Begin: Article - Primary -->\n" +
            "\t<article class=\"article lc\">\n" +
            "\t\t<div class=\"l-two-col-expose\">\n" +
            "\n" +
            "\t\t\t<!-- Begin: Article Content - Body Right Column -->\n" +
            "\t\t\t<div class=\"l-main-container\">\n" +
            "\t\t\t\t<div class=\"l-main\">\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t<!-- Begin: Article Header -->\n" +
            "\t\t\t\t<header class=\"article-header page-title\">\n" +
            "\t\t\t\t\t<!-- Begin: Article Eyebrows -->\n" +
            "<div class=\"tags\">\n" +
            "\t\n" +
            "\t\t\t<div class=\"tag-item\">\n" +
            "\t\t\t\t<a href=\"http://techcrunch.com/gadgets/\" class=\"tag\" data-omni-sm=\"art_articlecategory\">Gadgets</a>\n" +
            "\n" +
            "\t\t\t\t<div class=\"links\" id=\"tc-tag-item-gadgets\">\n" +
            "\t\t\t\t\t<ul class=\"recirc-river river-small g g-1-2-1\">\n" +
            "\t\t\t<li>\n" +
            "\t\t\t<div class=\"block-small\">\n" +
            "\t\t\t\t<a class=\"block-wrapper\" href=\"http://techcrunch.com/2013/10/26/this-week-on-the-tc-gadgets-podcast-apples-new-ipad-air-and-traveling-with-gadgets/\">\n" +
            "\t\t\t\t\t<img src=\"http://tctechcrunch2011.files.wordpress.com/2013/10/gadgets130412.jpg?w=150\" alt=\"This Week On The TC Gadgets Podcast: Apple&#8217;s New iPad Air And Traveling With&nbsp;Gadgets\" />\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"block-content\">\n" +
            "\t\t\t\t\t\t<h3>This Week On The TC Gadgets Podcast: Apple&#8217;s New iPad Air And Traveling With&nbsp;Gadgets</h3>\n" +
            "\n" +
            "\t\t\t\t\t\t<div class=\"block-meta\">\n" +
            "\t\t\t\t\t\t\t<div class=\"byline\">\n" +
            "\t\t\t\t\t\t\t\t<time datetime=\"2013-10-26 10:00:10\"></time>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</a>\n" +
            "\t\t\t</div>\n" +
            "\t\t</li>\n" +
            "\t\t\t<li>\n" +
            "\t\t\t<div class=\"block-small\">\n" +
            "\t\t\t\t<a class=\"block-wrapper\" href=\"http://techcrunch.com/2013/10/26/moniker-guitars-on-building-a-business-through-kickstarter/\">\n" +
            "\t\t\t\t\t<img src=\"http://tctechcrunch2011.files.wordpress.com/2013/10/mon2.jpg?w=150\" alt=\"Moniker Guitars On Building A Business Through&nbsp;Kickstarter\" />\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"block-content\">\n" +
            "\t\t\t\t\t\t<h3>Moniker Guitars On Building A Business Through&nbsp;Kickstarter</h3>\n" +
            "\n" +
            "\t\t\t\t\t\t<div class=\"block-meta\">\n" +
            "\t\t\t\t\t\t\t<div class=\"byline\">\n" +
            "\t\t\t\t\t\t\t\t<time datetime=\"2013-10-26 07:55:55\"></time>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</a>\n" +
            "\t\t\t</div>\n" +
            "\t\t</li>\n" +
            "\t\t\t<li>\n" +
            "\t\t\t<div class=\"block-small\">\n" +
            "\t\t\t\t<a class=\"block-wrapper\" href=\"http://techcrunch.com/2013/10/26/h2o-pal-helps-you-get-your-two-gallons-of-water-a-day/\">\n" +
            "\t\t\t\t\t<img src=\"http://tctechcrunch2011.files.wordpress.com/2013/10/3f4dae29fbab5a461fbd9c4e13b85db2_large.png?w=150\" alt=\"H2O-Pal Helps You Get Your Two Gallons Of Water A&nbsp;Day\" />\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"block-content\">\n" +
            "\t\t\t\t\t\t<h3>H2O-Pal Helps You Get Your Two Gallons Of Water A&nbsp;Day</h3>\n" +
            "\n" +
            "\t\t\t\t\t\t<div class=\"block-meta\">\n" +
            "\t\t\t\t\t\t\t<div class=\"byline\">\n" +
            "\t\t\t\t\t\t\t\t<time datetime=\"2013-10-26 04:26:05\"></time>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</a>\n" +
            "\t\t\t</div>\n" +
            "\t\t</li>\n" +
            "\t\n" +
            "\t<li>\n" +
            "\t\t<div class=\"block-small\">\n" +
            "\t\t\t<a class=\"block-wrapper\" href=\"http://techcrunch.com/gadgets/\">\n" +
            "\t\t\t\t<div class=\"block-content\">\n" +
            "\t\t\t\t\t<h3>Browse more...</h3>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</a>\n" +
            "\t\t</div>\n" +
            "\t</li>\n" +
            "</ul>\n" +
            "\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\t\t\n" +
            "\t\t\t<div class=\"tag-item\">\n" +
            "\t\t\t\t<a href=\"http://techcrunch.com/tag/guitar-hero/\" class=\"tag\" data-omni-sm=\"art_articlecategory\">Guitar Hero</a>\n" +
            "\n" +
            "\t\t\t\t<div class=\"links\" id=\"tc-tag-item-guitar-hero\">\n" +
            "\t\t\t\t\t<ul class=\"recirc-river river-small g g-1-2-1\">\n" +
            "\t\t\t<li>\n" +
            "\t\t\t<div class=\"block-small\">\n" +
            "\t\t\t\t<a class=\"block-wrapper\" href=\"http://techcrunch.com/2012/12/30/how-the-huang-brothers-bootstrapped-guitar-hero-to-a-billion-dollar-business/\">\n" +
            "\t\t\t\t\t<img src=\"http://tctechcrunch2011.files.wordpress.com/2012/12/screen-shot-2012-12-30-at-12-50-24-am1.png?w=150\" alt=\"How The Huang Brothers Bootstrapped Guitar Hero To A Billion Dollar&nbsp;Business\" />\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"block-content\">\n" +
            "\t\t\t\t\t\t<h3>How The Huang Brothers Bootstrapped Guitar Hero To A Billion Dollar&nbsp;Business</h3>\n" +
            "\n" +
            "\t\t\t\t\t\t<div class=\"block-meta\">\n" +
            "\t\t\t\t\t\t\t<div class=\"byline\">\n" +
            "\t\t\t\t\t\t\t\t<time datetime=\"2012-12-30 11:45:28\"></time>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</a>\n" +
            "\t\t\t</div>\n" +
            "\t\t</li>\n" +
            "\t\t\t<li>\n" +
            "\t\t\t<div class=\"block-small\">\n" +
            "\t\t\t\t<a class=\"block-wrapper\" href=\"http://techcrunch.com/2011/07/22/activision-ceo-spills-the-beans-guitar-hero-isnt-dead-just-resting/\">\n" +
            "\t\t\t\t\t<img src=\"http://tctechcrunch2011.files.wordpress.com/2011/07/guitarhero.jpg?w=150\" alt=\"Activision CEO Spills The Beans: Guitar Hero Isn&#8217;t Dead, Just&nbsp;Resting\" />\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"block-content\">\n" +
            "\t\t\t\t\t\t<h3>Activision CEO Spills The Beans: Guitar Hero Isn&#8217;t Dead, Just&nbsp;Resting</h3>\n" +
            "\n" +
            "\t\t\t\t\t\t<div class=\"block-meta\">\n" +
            "\t\t\t\t\t\t\t<div class=\"byline\">\n" +
            "\t\t\t\t\t\t\t\t<time datetime=\"2011-07-22 07:39:25\"></time>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</a>\n" +
            "\t\t\t</div>\n" +
            "\t\t</li>\n" +
            "\t\t\t<li>\n" +
            "\t\t\t<div class=\"block-small\">\n" +
            "\t\t\t\t<a class=\"block-wrapper\" href=\"http://techcrunch.com/2011/04/12/activision-guitar-hero-not-dead-merely-placed-on-hiatus/\">\n" +
            "\t\t\t\t\t<img src=\"http://i0.wp.com/tctechcrunch2011.files.wordpress.com/2011/04/guitarheroact.jpg?resize=150%2C150\" alt=\"Activision: Guitar Hero Not Dead, Merely Placed On&nbsp;Hiatus\" />\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"block-content\">\n" +
            "\t\t\t\t\t\t<h3>Activision: Guitar Hero Not Dead, Merely Placed On&nbsp;Hiatus</h3>\n" +
            "\n" +
            "\t\t\t\t\t\t<div class=\"block-meta\">\n" +
            "\t\t\t\t\t\t\t<div class=\"byline\">\n" +
            "\t\t\t\t\t\t\t\t<time datetime=\"2011-04-12 11:45:38\"></time>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</a>\n" +
            "\t\t\t</div>\n" +
            "\t\t</li>\n" +
            "\t\n" +
            "\t<li>\n" +
            "\t\t<div class=\"block-small\">\n" +
            "\t\t\t<a class=\"block-wrapper\" href=\"http://techcrunch.com/tag/guitar-hero/\">\n" +
            "\t\t\t\t<div class=\"block-content\">\n" +
            "\t\t\t\t\t<h3>Browse more...</h3>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</a>\n" +
            "\t\t</div>\n" +
            "\t</li>\n" +
            "</ul>\n" +
            "\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\t\t\n" +
            "\t\t\t<div class=\"tag-item\">\n" +
            "\t\t\t\t<a href=\"http://techcrunch.com/tag/wii/\" class=\"tag\" data-omni-sm=\"art_articlecategory\">Wii</a>\n" +
            "\n" +
            "\t\t\t\t<div class=\"links\" id=\"tc-tag-item-wii\">\n" +
            "\t\t\t\t\t<ul class=\"recirc-river river-small g g-1-2-1\">\n" +
            "\t\t\t<li>\n" +
            "\t\t\t<div class=\"block-small\">\n" +
            "\t\t\t\t<a class=\"block-wrapper\" href=\"http://techcrunch.com/2013/05/31/hulu-plus-upgrades-its-living-room-experience-with-a-new-look-search-controls-added-kids-section/\">\n" +
            "\t\t\t\t\t<img src=\"http://tctechcrunch2011.files.wordpress.com/2013/05/samsung_blog.jpg?w=150\" alt=\"Hulu Plus Upgrades Its Living Room Experience With A New Look, Search, Controls &amp; Added Kids&nbsp;Section\" />\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"block-content\">\n" +
            "\t\t\t\t\t\t<h3>Hulu Plus Upgrades Its Living Room Experience With A New Look, Search, Controls &amp; Added Kids&nbsp;Section</h3>\n" +
            "\n" +
            "\t\t\t\t\t\t<div class=\"block-meta\">\n" +
            "\t\t\t\t\t\t\t<div class=\"byline\">\n" +
            "\t\t\t\t\t\t\t\t<time datetime=\"2013-05-31 09:45:03\"></time>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</a>\n" +
            "\t\t\t</div>\n" +
            "\t\t</li>\n" +
            "\t\t\t<li>\n" +
            "\t\t\t<div class=\"block-small\">\n" +
            "\t\t\t\t<a class=\"block-wrapper\" href=\"http://techcrunch.com/2013/03/25/nintendo-amazingly-gets-worse-at-marketing-just-in-time-for-plummeting-wii-u-sales/\">\n" +
            "\t\t\t\t\t<img src=\"http://tctechcrunch2011.files.wordpress.com/2013/03/wiiuvswii.jpg?w=112\" alt=\"Nintendo Amazingly Gets Worse At Marketing Just In Time For Plummeting Wii U&nbsp;Sales\" />\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"block-content\">\n" +
            "\t\t\t\t\t\t<h3>Nintendo Amazingly Gets Worse At Marketing Just In Time For Plummeting Wii U&nbsp;Sales</h3>\n" +
            "\n" +
            "\t\t\t\t\t\t<div class=\"block-meta\">\n" +
            "\t\t\t\t\t\t\t<div class=\"byline\">\n" +
            "\t\t\t\t\t\t\t\t<time datetime=\"2013-03-25 07:33:50\"></time>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</a>\n" +
            "\t\t\t</div>\n" +
            "\t\t</li>\n" +
            "\t\t\t<li>\n" +
            "\t\t\t<div class=\"block-small\">\n" +
            "\t\t\t\t<a class=\"block-wrapper\" href=\"http://techcrunch.com/2013/02/14/death-by-apps/\">\n" +
            "\t\t\t\t\t<img src=\"http://tctechcrunch2011.files.wordpress.com/2013/02/ios-soul.jpg?w=150\" alt=\"The Fall TV Lineup May Include Apple Dominating&nbsp;Gaming\" />\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"block-content\">\n" +
            "\t\t\t\t\t\t<h3>The Fall TV Lineup May Include Apple Dominating&nbsp;Gaming</h3>\n" +
            "\n" +
            "\t\t\t\t\t\t<div class=\"block-meta\">\n" +
            "\t\t\t\t\t\t\t<div class=\"byline\">\n" +
            "\t\t\t\t\t\t\t\t<time datetime=\"2013-02-14 20:36:05\"></time>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</a>\n" +
            "\t\t\t</div>\n" +
            "\t\t</li>\n" +
            "\t\n" +
            "\t<li>\n" +
            "\t\t<div class=\"block-small\">\n" +
            "\t\t\t<a class=\"block-wrapper\" href=\"http://techcrunch.com/tag/wii/\">\n" +
            "\t\t\t\t<div class=\"block-content\">\n" +
            "\t\t\t\t\t<h3>Browse more...</h3>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</a>\n" +
            "\t\t</div>\n" +
            "\t</li>\n" +
            "</ul>\n" +
            "\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\t\t\n" +
            "\t\t\t<div class=\"tag-item\">\n" +
            "\t\t\t\t<a href=\"http://techcrunch.com/tag/nintendo/\" class=\"tag\" data-omni-sm=\"art_articlecategory\">Nintendo</a>\n" +
            "\n" +
            "\t\t\t\t<div class=\"links\" id=\"tc-tag-item-nintendo\">\n" +
            "\t\t\t\t\t<ul class=\"recirc-river river-small g g-1-2-1\">\n" +
            "\t\t\t<li>\n" +
            "\t\t\t<div class=\"block-small\">\n" +
            "\t\t\t\t<a class=\"block-wrapper\" href=\"http://techcrunch.com/2013/10/17/hulu-plus-lands-on-the-nintendo-3ds/\">\n" +
            "\t\t\t\t\t<img src=\"http://tctechcrunch2011.files.wordpress.com/2013/10/hulu-logo.png?w=150\" alt=\"Hulu Plus Lands On The Nintendo&nbsp;3DS\" />\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"block-content\">\n" +
            "\t\t\t\t\t\t<h3>Hulu Plus Lands On The Nintendo&nbsp;3DS</h3>\n" +
            "\n" +
            "\t\t\t\t\t\t<div class=\"block-meta\">\n" +
            "\t\t\t\t\t\t\t<div class=\"byline\">\n" +
            "\t\t\t\t\t\t\t\t<time datetime=\"2013-10-17 07:12:31\"></time>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</a>\n" +
            "\t\t\t</div>\n" +
            "\t\t</li>\n" +
            "\t\t\t<li>\n" +
            "\t\t\t<div class=\"block-small\">\n" +
            "\t\t\t\t<a class=\"block-wrapper\" href=\"http://techcrunch.com/2013/10/14/nintendo-2ds-review/\">\n" +
            "\t\t\t\t\t<img src=\"http://tctechcrunch2011.files.wordpress.com/2013/10/nintendo2ds.png?w=150\" alt=\"Nintendo 2DS Review: Back To Basics Proves Brilliant For Mobile Console&nbsp;Gaming\" />\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"block-content\">\n" +
            "\t\t\t\t\t\t<h3>Nintendo 2DS Review: Back To Basics Proves Brilliant For Mobile Console&nbsp;Gaming</h3>\n" +
            "\n" +
            "\t\t\t\t\t\t<div class=\"block-meta\">\n" +
            "\t\t\t\t\t\t\t<div class=\"byline\">\n" +
            "\t\t\t\t\t\t\t\t<time datetime=\"2013-10-14 03:50:38\"></time>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</a>\n" +
            "\t\t\t</div>\n" +
            "\t\t</li>\n" +
            "\t\t\t<li>\n" +
            "\t\t\t<div class=\"block-small\">\n" +
            "\t\t\t\t<a class=\"block-wrapper\" href=\"http://techcrunch.com/2013/10/13/what-games-are-the-nintendo-difference-still-exists/\">\n" +
            "\t\t\t\t\t<img src=\"http://tctechcrunch2011.files.wordpress.com/2013/10/screenshot-2013-10-13-09-33-25.png?w=150\" alt=\"What Games Are: The Nintendo Difference Still&nbsp;Exists\" />\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"block-content\">\n" +
            "\t\t\t\t\t\t<h3>What Games Are: The Nintendo Difference Still&nbsp;Exists</h3>\n" +
            "\n" +
            "\t\t\t\t\t\t<div class=\"block-meta\">\n" +
            "\t\t\t\t\t\t\t<div class=\"byline\">\n" +
            "\t\t\t\t\t\t\t\t<time datetime=\"2013-10-13 14:00:24\"></time>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</a>\n" +
            "\t\t\t</div>\n" +
            "\t\t</li>\n" +
            "\t\n" +
            "\t<li>\n" +
            "\t\t<div class=\"block-small\">\n" +
            "\t\t\t<a class=\"block-wrapper\" href=\"http://techcrunch.com/tag/nintendo/\">\n" +
            "\t\t\t\t<div class=\"block-content\">\n" +
            "\t\t\t\t\t<h3>Browse more...</h3>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</a>\n" +
            "\t\t</div>\n" +
            "\t</li>\n" +
            "</ul>\n" +
            "\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\t\t\n" +
            "\t\t\t<div class=\"tag-item\">\n" +
            "\t\t\t\t<a href=\"http://techcrunch.com/tag/activision/\" class=\"tag\" data-omni-sm=\"art_articlecategory\">Activision</a>\n" +
            "\n" +
            "\t\t\t\t<div class=\"links\" id=\"tc-tag-item-activision\">\n" +
            "\t\t\t\t\t<ul class=\"recirc-river river-small g g-1-2-1\">\n" +
            "\t\t\t<li>\n" +
            "\t\t\t<div class=\"block-small\">\n" +
            "\t\t\t\t<a class=\"block-wrapper\" href=\"http://techcrunch.com/2012/09/06/welcome-to-the-game-amazons-working-with-activision-on-in-app-offers/\">\n" +
            "\t\t\t\t\t<img src=\"http://tctechcrunch2011.files.wordpress.com/2012/09/3404352809_a8293a1a39_z.jpeg?w=150\" alt=\"Welcome To The Game: Amazon&#8217;s Working With Activision On In-App&nbsp;Offers\" />\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"block-content\">\n" +
            "\t\t\t\t\t\t<h3>Welcome To The Game: Amazon&#8217;s Working With Activision On In-App&nbsp;Offers</h3>\n" +
            "\n" +
            "\t\t\t\t\t\t<div class=\"block-meta\">\n" +
            "\t\t\t\t\t\t\t<div class=\"byline\">\n" +
            "\t\t\t\t\t\t\t\t<time datetime=\"2012-09-06 16:51:38\"></time>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</a>\n" +
            "\t\t\t</div>\n" +
            "\t\t</li>\n" +
            "\t\t\t<li>\n" +
            "\t\t\t<div class=\"block-small\">\n" +
            "\t\t\t\t<a class=\"block-wrapper\" href=\"http://techcrunch.com/2012/08/02/activision-q2-2012-earnings/\">\n" +
            "\t\t\t\t\t<img src=\"http://tctechcrunch2011.files.wordpress.com/2012/08/activision.jpeg?w=150\" alt=\"Activision Q2 Surprises The Street Again On $1.08B Revenue And $0.16 EPS, Raises&nbsp;Outlook\" />\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"block-content\">\n" +
            "\t\t\t\t\t\t<h3>Activision Q2 Surprises The Street Again On $1.08B Revenue And $0.16 EPS, Raises&nbsp;Outlook</h3>\n" +
            "\n" +
            "\t\t\t\t\t\t<div class=\"block-meta\">\n" +
            "\t\t\t\t\t\t\t<div class=\"byline\">\n" +
            "\t\t\t\t\t\t\t\t<time datetime=\"2012-08-02 13:36:52\"></time>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</a>\n" +
            "\t\t\t</div>\n" +
            "\t\t</li>\n" +
            "\t\t\t<li>\n" +
            "\t\t\t<div class=\"block-small\">\n" +
            "\t\t\t\t<a class=\"block-wrapper\" href=\"http://techcrunch.com/2011/08/18/activision-social-game-company-valuations-out-of-whack/\">\n" +
            "\t\t\t\t\t<img src=\"http://tctechcrunch2011.files.wordpress.com/2011/08/activision-logo.png?w=150\" alt=\"Activision: Social Game Company Valuations &#8220;Out Of&nbsp;Whack&#8221;\" />\n" +
            "\n" +
            "\t\t\t\t\t<div class=\"block-content\">\n" +
            "\t\t\t\t\t\t<h3>Activision: Social Game Company Valuations &#8220;Out Of&nbsp;Whack&#8221;</h3>\n" +
            "\n" +
            "\t\t\t\t\t\t<div class=\"block-meta\">\n" +
            "\t\t\t\t\t\t\t<div class=\"byline\">\n" +
            "\t\t\t\t\t\t\t\t<time datetime=\"2011-08-18 19:05:51\"></time>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t</a>\n" +
            "\t\t\t</div>\n" +
            "\t\t</li>\n" +
            "\t\n" +
            "\t<li>\n" +
            "\t\t<div class=\"block-small\">\n" +
            "\t\t\t<a class=\"block-wrapper\" href=\"http://techcrunch.com/tag/activision/\">\n" +
            "\t\t\t\t<div class=\"block-content\">\n" +
            "\t\t\t\t\t<h3>Browse more...</h3>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</a>\n" +
            "\t\t</div>\n" +
            "\t</li>\n" +
            "</ul>\n" +
            "\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t</div>\n" +
            "\t\t</div>\n" +
            "<!-- End: Article Eyebrows -->\n" +
            "\n" +
            "\t\t\t\t\t<h1 class=\"alpha\">Ask and Wii shall receive: Guitar Hero 3 in Stereo</h1>\n" +
            "\t\t\t\t\t<div class=\"title-left\">\n" +
            "\t\t\t\t\t<div class=\"byline\">\n" +
            "\tPosted <time datetime=\"2007-12-07\" class=\"timestamp\">Dec 7, 2007</time> by <a href=\"/author/devin-coldewey/\" title=\"Posts by Devin Coldewey\" onclick=\"s_objectID=\\'river_author\\';\" rel=\"author\">Devin Coldewey</a></div>\n" +
            "<!-- Begin: Social Share Icons -->\n" +
            "<div class=\"social-share\">\n" +
            "\t<ul class=\"inline-list social-share-list\" id=\"social-share\">\n" +
            "\t\t\t\t<li class=\"comment\"><a href=\"#comments\" rel=\"external\" class=\"icon-comment launch-social-load\"><span class=\"social-count\"></span></a></li>\n" +
            "\t\t\t\t\n" +
            "\t\t<li><a href=\"#\" rel=\"external\" class=\"icon-facebook\"><span class=\"social-count\" id=\"fb-newshare-17708\"></span></a></li>\n" +
            "\t\t<li class=\"twitter\"><a href=\"#\" rel=\"external\" class=\"icon-twitter\"><span class=\"social-count\" id=\"tweet-newshare-17708\"></span></a></li>\n" +
            "\t\t<li><a href=\"#\" rel=\"external\" class=\"icon-linkedin\"><span class=\"social-count\" id=\"linkedin-newshare-17708\"></span></a></li>\t\n" +
            "\t\t<li>\n" +
            "\t\t\t<a href=\"#\" class=\"icon-caret-down social-share-more\"><span class=\"is-vishidden\">More</span></a>\n" +
            "\t\t\t<ul class=\"more-social-share-list\">\n" +
            "\t\t\t\t<li><div class=\"g-plusone\" data-annotation=\"bubble\" data-href=\"http://techcrunch.com/2007/12/07/ask-and-wii-shall-receive-guitar-hero-3-in-stereo/\" data-size=\"medium\" data-width=\"60\"></div></li>\n" +
            "\t\t\t\t<li><su:badge layout=\"1\" type=\"badge\" location=\"http://techcrunch.com/2007/12/07/ask-and-wii-shall-receive-guitar-hero-3-in-stereo/?ncid=su_social_share\"></su:badge></li>\n" +
            "\t\t\t\t<li><a href=\"http://www.reddit.com/submit?title=Ask%20and%20Wii%20shall%20receive%3A%20Guitar%20Hero%203%20in%20Stereo&amp;url=http://techcrunch.com/2007/12/07/ask-and-wii-shall-receive-guitar-hero-3-in-stereo/%3Fncid%3Dreddit_social_share\" target=\"_blank\" class=\"reddit\"><img src=\"http://s1.wp.com/wp-content/themes/vip/techcrunch-2013/assets/images/reddit.gif?m=1381204869g\" alt=\"submit to reddit\" border=\"0\" /></a></li>\n" +
            "\t\t\t</ul>\n" +
            "\t\t</li>\n" +
            "\t</ul>\n" +
            "</div>\n" +
            "<!-- End: Social Share Icons -->\n" +
            "\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t<a href=\"http://techcrunch.com/2007/12/07/zygo-might-be-the-uks-twitter-but-with-revenues/\" class=\"next-link\" data-omni-sm=\"art_nextstory\">\n" +
            "\t\t\t\t\t\t<div class=\"next-story-link\">Next Story</div>\n" +
            "\t\t\t\t\t\t<div class=\"next-story-full\">\n" +
            "\t\t\t\t\t\t\t<h4 class=\"next-title\">Zygo might be the UK&#039;s Twitter, but with&nbsp;revenues</h4>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</a>\n" +
            "\t\t\t\t</header>\n" +
            "\t\t\t\t<!-- End: Article Header -->\n" +
            "\n" +
            "\t\t\t\t<!-- Begin: Article Body -->\n" +
            "\t\t\t\t<div class=\"l-two-col\">\n" +
            "\n" +
            "\t\t\t\t\t<!-- Begin: Article Body - Main -->\n" +
            "\t\t\t\t\t<div class=\"l-main-container\">\n" +
            "\t\t\t\t\t\t<div class=\"l-main\">\n" +
            "\t\t\t\t\t\t\t<div class=\"article-entry text\">\n" +
            "\n" +
            "<!-- Begin: Wordpress Article Content -->\n" +
            "<p><img src=\"http://old.crunchgear.com/wp-content/uploads/2007/12/guitar-hero-3-wii-a.jpg\" alt=\"guitar-hero-3-wii-a.jpg\" class=\"center\" />It&#8217;s a good month for customers, at least if you believe what you read. In another triumph of savvy consumers over corporate retardation, Activison (now Activision-Blizzard, of course) will be correcting that minor oversight by which Wii owners only got mono sound in a music-based game. &#8220;We recently became aware that some consumers have not been able to enjoy the full audio output in the Nintendo Wii version of Guitar Hero III: Legends of Rock,&#8221; said an Activision spokesman. Somebody give this guy a high five.</p>\n" +
            "<p>They&#8217;ve decided to do a new pressing of the game and send out the corrected discs free of charge to anybody who wants one (and owns the original, of course.) So keep an eye out for a follow-up, kids, and maybe you too will finally be able to live out your &#8220;Freebird&#8221; fantasies with both channels.</p>\n" +
            "<p><a href=\"http://www.shacknews.com/onearticle.x/50257\">Guitar Hero 3 Wii Mono Sound to be Remedied with Free Remastered Game Discs</a> [Shack News]</p>\n" +
            "<div id=\"jp-post-flair\" class=\"sharedaddy sd-like-enabled\"></div><!-- End: Wordpress Article Content -->\n" +
            "\n" +
            "\t\t\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "<div class=\"ad-cluster-container ad-cluster-no-ad\" style=\"display: none;\">\n" +
            "\t<small class=\"advertise-here\"><a href=\"https://www.buyads.com/website/techcrunch\" title=\"Advertise on TechCrunch\">Advertisement</a></small>\n" +
            "\t<ul class=\"ad-cluster\" data-adcount=\"4\">\n" +
            "\t</ul>\n" +
            "</div>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t<!-- End: Article Body - Main -->\n" +
            "\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";

    private final ArticleExtractor extractor = ArticleExtractorFactory.newExtractor("techcrunch", html);

}
