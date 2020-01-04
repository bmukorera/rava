package zw.co.cryptosine.rava.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zw.co.cryptosine.rava.model.RavaQuestion;
import zw.co.cryptosine.rava.service.RavaService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/ravaapi/v1/search")
public class RavaSearchApi  {

    @Autowired
    RavaService ravaService;

    @GetMapping(value = "/subject/{subject}", produces = "application/json")
    @ResponseBody
    public List<RavaQuestion> searhQuestionsForSubject(@PathVariable(name = "subject") String subject){
        return ravaService.search(subject);
    }
}
