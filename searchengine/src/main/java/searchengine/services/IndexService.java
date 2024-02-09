package searchengine.services;


import searchengine.Responses.IndexResponse;

import javax.persistence.criteria.CriteriaBuilder;

public interface IndexService {
    IndexResponse startIndexing();

    IndexResponse stopIndexing() throws InterruptedException;

    void indexPage(String url);

    boolean isIndexing();
}
