package pl.wat.magda.biblioteka.repository.search;

import pl.wat.magda.biblioteka.domain.Gatunek;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Gatunek entity.
 */
public interface GatunekSearchRepository extends ElasticsearchRepository<Gatunek, Long> {
}
