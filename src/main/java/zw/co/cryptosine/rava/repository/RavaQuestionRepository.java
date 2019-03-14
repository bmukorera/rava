package zw.co.cryptosine.rava.repository;

import zw.co.cryptosine.rava.model.RavaQuestion;

import java.util.List;

public interface RavaQuestionRepository {
    RavaQuestion save(RavaQuestion ravaQuestion);
    List<RavaQuestion> getWithSearchKey(String searchKey);


}
