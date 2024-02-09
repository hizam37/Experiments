package searchengine.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import searchengine.model.IndexTable;
import searchengine.model.Page;
import java.util.List;


@Repository
public interface IndexRepository extends JpaRepository<IndexTable,Integer> {

    @Query(value = "select * from index_table where index_table.lemma_id in :lemmas and index_table.page_id in :pages",nativeQuery = true)
    List<IndexTable> findByPagesAndLemmas(@Param("lemmas")List<IndexTable>lemmaList,
                                          @Param("pages") List<Page> pageList);

}
