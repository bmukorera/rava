package zw.co.cryptosine.rava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zw.co.cryptosine.rava.repository.RavaQuestionRepository;
import zw.co.cryptosine.rava.repository.RavaQuestionRepositoryImpl;
import zw.co.cryptosine.rava.service.RavaService;
import zw.co.cryptosine.rava.service.RavaServiceImpl;

@Configuration
public class ServiceConfiguration {

    @Bean
    RavaService ravaService(){
        RavaServiceImpl ravaService = new RavaServiceImpl();
        ravaService.setRavaQuestionRepository(ravaQuestionRepository());
        return ravaService;
    }

    RavaQuestionRepository ravaQuestionRepository(){
        RavaQuestionRepositoryImpl ravaQuestionRepository=new RavaQuestionRepositoryImpl();
        return ravaQuestionRepository;
    }
}
