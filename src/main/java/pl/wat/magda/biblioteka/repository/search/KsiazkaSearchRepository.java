package pl.wat.magda.biblioteka.repository.search;

import pl.wat.magda.biblioteka.domain.Ksiazka;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Ksiazka entity.
 */
public interface KsiazkaSearchRepository extends ElasticsearchRepository<Ksiazka, Long> {
}
