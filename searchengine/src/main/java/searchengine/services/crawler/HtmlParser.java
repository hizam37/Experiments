package searchengine.services.crawler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import searchengine.config.NetworkSettings;
import searchengine.model.Site;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ConcurrentSkipListSet;
import static java.lang.Thread.sleep;


@RequiredArgsConstructor
@Service
@Slf4j
public class HtmlParser {

    private final NetworkSettings networkSettings;

    private final Site site;
    public ConcurrentSkipListSet<String> getUrls(String url) {

        ConcurrentSkipListSet<String> urls = new ConcurrentSkipListSet<>();

        try {
            sleep(5000);
            Connection.Response connection = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .userAgent(networkSettings.getUserAgents().get(new Random().nextInt(6)).toString())
                    .referrer(networkSettings.getReferrer())
                    .timeout(networkSettings.getTimeout())
                    .followRedirects(false)
                    .execute();

             Document document = connection.parse();
            Elements elements = document.select("body").select("a");

            for (Element perElement : elements) {
                String link = perElement.absUrl("href");

                if (isLink(link) && !isFile(link)) {
                    urls.add(link);
                }
            }

        } catch (InterruptedException | IOException e) {

            System.out.println(e + "  " + url);

        }

        return urls;

    }

    private boolean isLink(String link) {

        return link.startsWith(site.getUrl());

    }

    private boolean isFile(String link) {

        return link.contains(".pdf")
                || link.contains(".png")
                || link.contains(".gif")
                || link.contains(".eps")
                || link.contains(".webp")
                || link.contains(".doc")
                || link.contains(".pptx")
                || link.contains("?_ga")
                || link.contains(".xlsx")
                || link.contains(".docx")
                || link.contains(".jpg")
                || link.contains("#");

    }

}

