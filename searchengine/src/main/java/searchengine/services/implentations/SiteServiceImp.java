//package searchengine.services.implentations;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import searchengine.model.Site;
//import searchengine.repository.SiteRepository;
//import searchengine.services.SiteService;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class SiteServiceImp implements SiteService {
//
//    @Autowired
//    private SiteRepository siteRepository;
//    @Override
//    public void saveSite(Site site) {
//        siteRepository.save(site);
//    }
//
//
//    @Override
//    public Optional<Site> getSiteById(Long siteId) {
//        return siteRepository.findById(siteId);
//    }
//
//    @Override
//    public List<Site> getSitesList() {
//        List <Site> sites = siteRepository.findAll();
//        return Collections.synchronizedList(sites);
//    }
//
//    @Override
//    public Site updateSite() {
//        return siteRepository.update();
//    }
//
//
//    @Override
//    public void findAllSites() {
//        siteRepository.findAll();
//    }
//
//    @Override
//    public void deleteSiteById(Long siteId) {
//        siteRepository.deleteById(siteId);
//    }
//
//
//}
