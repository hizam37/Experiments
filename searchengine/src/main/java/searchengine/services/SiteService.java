package searchengine.services;

import searchengine.model.Site;

import java.util.List;
import java.util.Optional;

public interface SiteService {
    void saveSite(Site site);

    Optional<Site> getSiteById(Long siteId);

    List<Site> getSitesList();

    Site updateSite();

    void findAllSites();

    void deleteSiteById(Long siteId);

}
