package searchengine.model;
import lombok.*;
import javax.persistence.*;



@Entity
@Getter
@Setter
@Table(name = "index_table")
public class IndexTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="page_id")
    private Page page;


    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="lemma_id")
    private Lemma lemma;


    private float amountOfLemmaInPage;

}
