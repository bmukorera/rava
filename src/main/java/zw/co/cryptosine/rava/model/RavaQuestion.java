package zw.co.cryptosine.rava.model;

import zw.co.cryptosine.rava.enums.RavaStudentCategory;
import zw.co.cryptosine.rava.enums.RavaSubject;

import java.io.Serializable;
import java.util.List;

public class RavaQuestion implements Serializable {

    private String question;
    private RavaSubject subject;
    private RavaStudentCategory studentCategory;
    private List<RavaAnswer> ravaAnswers;
    private boolean multipleChoice;

    public boolean isMultipleChoice() {
        return multipleChoice;
    }

    public void setMultipleChoice(boolean multipleChoice) {
        this.multipleChoice = multipleChoice;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public RavaSubject getSubject() {
        return subject;
    }

    public void setSubject(RavaSubject subject) {
        this.subject = subject;
    }

    public RavaStudentCategory getStudentCategory() {
        return studentCategory;
    }

    public void setStudentCategory(RavaStudentCategory studentCategory) {
        this.studentCategory = studentCategory;
    }

    public List<RavaAnswer> getRavaAnswers() {
        return ravaAnswers;
    }

    public void setRavaAnswers(List<RavaAnswer> ravaAnswers) {
        this.ravaAnswers = ravaAnswers;
    }

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this)
                .append("question", question)
                .append("subject", subject)
                .append("studentCategory", studentCategory)
                .append("ravaAnswers", ravaAnswers)
                .append("multipleChoice", multipleChoice)
                .toString();
    }
}
