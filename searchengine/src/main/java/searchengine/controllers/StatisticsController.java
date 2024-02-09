package searchengine.controllers;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import searchengine.Responses.IndexResponse;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.services.IndexService;
import searchengine.services.StatisticsService;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class StatisticsController {

    private final StatisticsService statisticsService;


    private final IndexService indexService;


    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    @GetMapping("/startIndexing")
    public ResponseEntity<IndexResponse> startIndexing(){
        IndexResponse site = indexService.startIndexing();
        return ResponseEntity.ok(site);
    }

    @GetMapping("/stopIndexing")
    public ResponseEntity<IndexResponse> stopIndexing() throws InterruptedException {
        IndexResponse site = indexService.stopIndexing();
        return ResponseEntity.ok(site);
    }
}
