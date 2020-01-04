package zw.co.cryptosine.rava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import zw.co.cryptosine.rava.repository.RavaQuestionRepository;
import zw.co.cryptosine.rava.repository.RavaQuestionRepositoryImpl;
import zw.co.cryptosine.rava.service.RavaService;
import zw.co.cryptosine.rava.service.RavaServiceImpl;

@Configuration
public class ServiceConfiguration {



    @Bean
    RavaService ravaService(RavaQuestionRepository ravaQuestionRepository){
        RavaServiceImpl ravaService = new RavaServiceImpl();
        ravaService.setRavaQuestionRepository(ravaQuestionRepository);
        return ravaService;
    }

    @Bean
    RavaQuestionRepository ravaQuestionRepository(Environment environment){
        RavaQuestionRepositoryImpl ravaQuestionRepository=new RavaQuestionRepositoryImpl();
        ravaQuestionRepository.setEnvironment(environment);
        return ravaQuestionRepository;
    }
}
