package zw.co.cryptosine.rava.exception;

public class RavaException extends RuntimeException{

    private String field;
    private String message;
    private boolean requiredMissing;

    public RavaException(String message) {
        super(message);
    }

    public RavaException(String field, String message, boolean requiredMissing) {
        this.field = field;
        this.requiredMissing = requiredMissing;
        if(requiredMissing){
            this.message = this.field+" : Required is Missing";
        }else{
            this.message=message;
        }
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRequiredMissing() {
        return requiredMissing;
    }

    public void setRequiredMissing(boolean requiredMissing) {
        this.requiredMissing = requiredMissing;
    }
}
