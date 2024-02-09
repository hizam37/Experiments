package searchengine.services.crawler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import searchengine.config.NetworkSettings;
import searchengine.model.Page;
import searchengine.model.Site;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import java.util.TimerTask;
import java.util.concurrent.ForkJoinPool;


@Slf4j
@RequiredArgsConstructor
public class WebCrawlerExecutor extends TimerTask {


    private final NetworkSettings networkSettings;

    public static ForkJoinPool forkJoinPool;

    private PageRepository pageRepository;

    private SiteRepository siteRepository;
    private Site site;

    private final Page page;


    public WebCrawlerExecutor(NetworkSettings networkSettings, Page page, PageRepository pageRepository, Site site, SiteRepository siteRepository) {
        this.page = page;
        this.site = site;
        this.pageRepository = pageRepository;
        this.siteRepository = siteRepository;
        this.networkSettings = networkSettings;
         forkJoinPool = new ForkJoinPool();
    }

    @Override
    public void run() {
        WebCrawler webCrawler = new WebCrawler(networkSettings, page, pageRepository, site, siteRepository);
        forkJoinPool.invoke(webCrawler);
        cancel();
    }
}
