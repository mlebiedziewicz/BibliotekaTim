package pl.wat.magda.biblioteka.repository.search;

import pl.wat.magda.biblioteka.domain.Wydawnictwo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Wydawnictwo entity.
 */
public interface WydawnictwoSearchRepository extends ElasticsearchRepository<Wydawnictwo, Long> {
}
