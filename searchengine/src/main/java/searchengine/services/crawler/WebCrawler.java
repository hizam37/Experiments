package searchengine.services.crawler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.NetworkSettings;
import searchengine.model.Page;
import searchengine.model.Site;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.RecursiveAction;

import static java.lang.Thread.sleep;


@Slf4j
@RequiredArgsConstructor
@Service
public class WebCrawler extends RecursiveAction {

    @Getter
    private final CopyOnWriteArrayList<Page> siteMapChildren =new CopyOnWriteArrayList<>();

    private final NetworkSettings networkSettings;

    private PageRepository pageRepository;

    private SiteRepository siteRepository;
    private Site site;

    private final Page page;
    private final static CopyOnWriteArrayList <String> linksVisited = new CopyOnWriteArrayList<>();


    @Autowired
    public WebCrawler(NetworkSettings networkSettings,Page page,PageRepository pageRepository,Site site,SiteRepository siteRepository) {
        this.page=page;
        this.site=site;
        this.pageRepository=pageRepository;
        this.siteRepository=siteRepository;
        this.networkSettings =networkSettings;
    }

    @Override
    protected void compute() {

        linksVisited.add(site.getUrl());

        HtmlParser htmlParser = new HtmlParser(networkSettings,site);

        siteRepository.save(site);

        ConcurrentSkipListSet<String> links = htmlParser.getUrls(site.getUrl());

        for (String perLink : links) {

            if (!linksVisited.contains(perLink)) {

                Page path = new Page();

                linksVisited.add(perLink);

                path.setPath(perLink.replace(site.getUrl(),""));

                addChildren(path);

                path.setSite(site);

                try {

                    sleep(5000);

                    Connection.Response connection = Jsoup.connect(perLink)

                            .ignoreContentType(true)

                            .userAgent(networkSettings.getUserAgents().get(new Random().nextInt(6)).toString())

                            .referrer(networkSettings.getReferrer())

                            .timeout(networkSettings.getTimeout())

                            .followRedirects(false)

                            .execute();

                    Document document = connection.parse();

                    String content=document.html();

                    path.setContent(content);

                    pageRepository.save(path);

                } catch (InterruptedException | IOException e) {

                   log.info(e + " " + perLink);

                }


            }

        }

        List<WebCrawler> mapOfSiteTasks = new ArrayList<>();

        for (Page child : getSiteMapChildren()) {

            WebCrawler webCrawlerTask = new WebCrawler(networkSettings,child,pageRepository,site,siteRepository);


            webCrawlerTask.fork();

            mapOfSiteTasks.add(webCrawlerTask);

        }

        for (WebCrawler perTask : mapOfSiteTasks) {

            perTask.join();

        }
    }
    public void addChildren(Page children)
    {
        siteMapChildren.add(children);
    }


}