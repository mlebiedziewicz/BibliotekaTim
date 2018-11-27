package pl.wat.magda.biblioteka.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of KsiazkaSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class KsiazkaSearchRepositoryMockConfiguration {

    @MockBean
    private KsiazkaSearchRepository mockKsiazkaSearchRepository;

}
