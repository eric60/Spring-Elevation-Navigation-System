package com.EleNa;

import com.EleNa.model.DataStructures.Edge;
import com.EleNa.repositories.EdgeRepository;
import com.EleNa.repositories.NodeRepository;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class RepositoryTestConfig {
    @Bean
    @Primary
    public NodeRepository nodeRepository() {
        return Mockito.mock(NodeRepository.class);
    }

    public EdgeRepository edgeRepository() {
        return Mockito.mock(EdgeRepository.class);
    }
}
