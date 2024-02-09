//package searchengine.services;
//
//
//import lombok.RequiredArgsConstructor;
//import org.jsoup.Connection;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import searchengine.config.NetworkSettings;
//import searchengine.model.Site;
//
//import java.io.IOException;
//import java.util.concurrent.ConcurrentSkipListSet;
//
//import static java.lang.Thread.sleep;
//
//@Service
//@RequiredArgsConstructor
//public class HtmlService {
//
//
//    private final NetworkSettings networkSettings;
//
//    private Site site=new Site();
//    public  ConcurrentSkipListSet<String> getUrls(String url) {
//
//        ConcurrentSkipListSet<String> urls = new ConcurrentSkipListSet<>();
//
//        try {
//
//            sleep(150);
//
//            Connection.Response connection = Jsoup.connect(url)
//                    .userAgent(networkSettings.getUserAgent()).
//                    referrer(networkSettings.getReferrer())
//                    .ignoreContentType(true)
//                    .ignoreHttpErrors(true)
//                    .timeout(networkSettings.getTimeout())
//                    .followRedirects(false).execute();
//
//            Document document = connection.parse();
//
//            Elements elements = document.select("body").select("a");
//
//            for (Element perElement : elements) {
//
//                String link = perElement.absUrl("href");
//
//                if (isLink(link) && !isFile(link)) {
//
//                    urls.add(link);
//
//                }
//            }
//
//        } catch (InterruptedException | IOException e) {
//
//            System.out.println(e + "  " + url);
//
//        }
//
//        return urls;
//
//    }
//
//    private boolean isLink(String link) {
//
//        return link.startsWith(site.getUrl());
//
//    }
//
//    private boolean isFile(String link) {
//
//
//        return link.contains(".pdf")
//                || link.contains(".png")
//                || link.contains(".gif")
//                || link.contains(".eps")
//                || link.contains(".webp")
//                || link.contains(".doc")
//                || link.contains(".pptx")
//                || link.contains("?_ga")
//                || link.contains(".xlsx")
//                || link.contains(".docx")
//                || link.contains(".jpg")
//                || link.contains("#");
//
//    }
//
//}
