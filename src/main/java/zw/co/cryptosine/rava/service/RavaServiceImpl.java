package zw.co.cryptosine.rava.service;

import zw.co.cryptosine.rava.exception.RavaException;
import zw.co.cryptosine.rava.model.RavaQuestion;
import zw.co.cryptosine.rava.repository.RavaQuestionRepository;

import java.util.List;

public class RavaServiceImpl implements RavaService{

    private RavaQuestionRepository ravaQuestionRepository;

    public void setRavaQuestionRepository(RavaQuestionRepository ravaQuestionRepository) {
        this.ravaQuestionRepository = ravaQuestionRepository;
    }

    @Override
    public void saveRavaQuestion(RavaQuestion ravaQuestion) {
        validate(ravaQuestion);
        ravaQuestionRepository.save(ravaQuestion);
    }

    @Override
    public List<RavaQuestion> search(String search) {
        return ravaQuestionRepository.getWithSearchKey(search);
    }

    private void validate(RavaQuestion ravaQuestion) {
        if(ravaQuestion.getQuestion()==null||ravaQuestion.getQuestion().trim().isEmpty()){
            throw new RavaException("question","",true);
        }
        if(ravaQuestion.getSubject()==null){
            throw new RavaException("subject","",true);
        }
        if(ravaQuestion.getStudentCategory()==null){
            throw new RavaException("studentCategory","",true);
        }
        if(ravaQuestion.isMultipleChoice()&&ravaQuestion.getRavaAnswers().size()<2){
            throw new RavaException("","Multichoice Requires more than 1 answer",false);
        }

    }




}
