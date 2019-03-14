package zw.co.cryptosine.rava.service;

import zw.co.cryptosine.rava.model.RavaQuestion;

import java.util.List;

public interface RavaService {

    void saveRavaQuestion(RavaQuestion ravaQuestion);

    List<RavaQuestion> search(String searchKey);


}
