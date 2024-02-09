package searchengine.services.implentations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import searchengine.Responses.IndexResponse;
import searchengine.config.NetworkSettings;
import searchengine.config.SitesList;
import searchengine.model.Page;
import searchengine.model.Site;
import searchengine.config.SiteFromConf;
import searchengine.model.Status;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import searchengine.services.IndexService;
import searchengine.services.crawler.WebCrawlerExecutor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;



@Service
@RequiredArgsConstructor
@Slf4j
public class IndexServiceImp implements IndexService {

//    private ScheduledExecutorService executorService;
    private final NetworkSettings networkSettings;

    private final SitesList sitesList;

    private final SiteRepository siteRepository;

    private final PageRepository pageRepository;

    private final Timer timer = new Timer();

    @Override
    public IndexResponse startIndexing() {
//        if (isIndexing()) {
//            return new IndexResponse(false, "Index already started");
//        }
        Site site = new Site();
        Page page = new Page();
        for (SiteFromConf siteFromConf1 : sitesList.getSites()) {
            site.setName(siteFromConf1.getName());
            site.setUrl(siteFromConf1.getUrl().replace("www.", ""));
            LocalDateTime statusTime = LocalDateTime.now();
            site.setStatusTime(statusTime);
            site.setStatus(Status.INDEXING);
            page.setSite(site);
            WebCrawlerExecutor webCrawler = new WebCrawlerExecutor(networkSettings, page, pageRepository, site, siteRepository);
            timer.schedule(webCrawler,1000L);
        }

        return new IndexResponse(true, "");
    }



    @Override
    public IndexResponse stopIndexing() {
        timer.cancel();
            log.info("SHUTTING");

//            List<Site> perSite = siteRepository.findAll();
//            for (Site site : perSite) {
//                if (site.getStatus().equals(Status.INDEXING)) {
//                    site.setStatus(Status.FAILED);
//                    siteRepository.saveAndFlush(site);
//                }
//            }
//            timer.cancel();
        return new IndexResponse(true, "");


    }

    @Override
    public void indexPage(String url) {

    }

    @Override
    public boolean isIndexing() {
        List<Site> site = siteRepository.findAll();
        for (Site siteUrlInfo : site) {
            if (siteUrlInfo.getStatus().equals(Status.INDEXING)) {
                return true;
            }

        }
        return false;

    }


}
