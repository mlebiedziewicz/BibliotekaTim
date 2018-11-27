package pl.wat.magda.biblioteka.repository.search;

import pl.wat.magda.biblioteka.domain.Wypozyczone;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Wypozyczone entity.
 */
public interface WypozyczoneSearchRepository extends ElasticsearchRepository<Wypozyczone, Long> {
}
