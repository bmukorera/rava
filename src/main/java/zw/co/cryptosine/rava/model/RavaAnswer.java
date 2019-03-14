package zw.co.cryptosine.rava.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class RavaAnswer {
    private String answer;
    private boolean correct;

    public RavaAnswer(String answer, boolean correct) {
        this.answer = answer;
        this.correct = correct;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("answer", answer)
                .append("correct", correct)
                .toString();
    }
}
