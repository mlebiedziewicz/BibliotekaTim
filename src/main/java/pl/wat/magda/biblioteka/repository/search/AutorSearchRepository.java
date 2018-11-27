package pl.wat.magda.biblioteka.repository.search;

import pl.wat.magda.biblioteka.domain.Autor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Autor entity.
 */
public interface AutorSearchRepository extends ElasticsearchRepository<Autor, Long> {
}
