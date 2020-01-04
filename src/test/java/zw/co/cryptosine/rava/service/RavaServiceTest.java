package zw.co.cryptosine.rava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import zw.co.cryptosine.rava.enums.RavaStudentCategory;
import zw.co.cryptosine.rava.enums.RavaSubject;
import zw.co.cryptosine.rava.exception.RavaException;
import zw.co.cryptosine.rava.model.RavaAnswer;
import zw.co.cryptosine.rava.model.RavaQuestion;
import zw.co.cryptosine.rava.repository.RavaQuestionRepository;
import zw.co.cryptosine.rava.repository.RavaQuestionRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class RavaServiceTest {

    RavaServiceImpl ravaService;
    RavaQuestion ravaQuestion;
    RavaQuestionRepository ravaQuestionRepository;

    @Before
    public void setup(){
        ravaQuestionRepository = new RavaQuestionRepositoryImpl();
        ravaService = new RavaServiceImpl();
        ravaService.setRavaQuestionRepository(ravaQuestionRepository);

        ravaQuestion=new RavaQuestion();
        ravaQuestion.setQuestion("test question");
        ravaQuestion.setSubject(RavaSubject.ENGLISH);
        List<RavaAnswer> ravaAnswers = new ArrayList<>();
        ravaAnswers.add(new RavaAnswer("test answer",true));
        ravaQuestion.setRavaAnswers(ravaAnswers);
        ravaQuestion.setMultipleChoice(false);
        ravaQuestion.setStudentCategory(RavaStudentCategory.GRADE7);

    }

    @Test(expected = RavaException.class)
    public void shouldThrowExceptionIfQuestionOIsNull(){
        ravaQuestion.setQuestion(null);
        ravaService.saveRavaQuestion(ravaQuestion);
    }

    @Test(expected = RavaException.class)
    public void shouldThrowExceptionIfQuestionOIsEmpty(){
        ravaQuestion.setQuestion(" ");
        ravaService.saveRavaQuestion(ravaQuestion);
    }

    @Test(expected = RavaException.class)
    public void shouldThrowExceptionWhenSubjectIsNull(){
        ravaQuestion.setSubject(null);
        ravaService.saveRavaQuestion(ravaQuestion);
    }

    @Test(expected = RavaException.class)
    public void shouldThrowExceptionWhenStudentCategoryIsNull(){
        ravaQuestion.setStudentCategory(null);
        ravaService.saveRavaQuestion(ravaQuestion);
    }

    @Test(expected = RavaException.class)
    public void shouldThrowExceptionIfIsMultipleChoiceAndHasLessThan2Answers(){
        ravaQuestion.setMultipleChoice(true);
        ravaService.saveRavaQuestion(ravaQuestion);
    }

    @Test
    public void shouldReturnSuccessIfInfomationIsValid(){
        ravaService.saveRavaQuestion(ravaQuestion);
    }

    @Test
    public void shouldReturnREsult(){
        assertNotNull(ravaService.search("GRADE7"));
    }
}
