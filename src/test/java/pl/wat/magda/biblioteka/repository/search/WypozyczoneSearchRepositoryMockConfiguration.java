package pl.wat.magda.biblioteka.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of WypozyczoneSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class WypozyczoneSearchRepositoryMockConfiguration {

    @MockBean
    private WypozyczoneSearchRepository mockWypozyczoneSearchRepository;

}
