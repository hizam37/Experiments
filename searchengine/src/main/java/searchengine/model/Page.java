package searchengine.model;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.persistence.Index;

@Entity
@Getter
@Setter
@Component
@Table(indexes = @Index(name = "idx_path",columnList = "path",unique = true))
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name="site_id")
    private Site site;


    @Column(columnDefinition = "TEXT NOT NULL")
    private String path;


    private int code;

    @Column(columnDefinition = "MEDIUMTEXT NOT NULL")
    private String content;

}
