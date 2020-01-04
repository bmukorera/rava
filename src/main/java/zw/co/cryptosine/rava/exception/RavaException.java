package zw.co.cryptosine.rava.exception;

public class RavaException extends RuntimeException{

    private final String field;
    private final String message;
    private final boolean requiredMissing;

    public RavaException(String message) {
        super(message);
        this.field="";
        this.message=message;
        this.requiredMissing=false;

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



    @Override
    public String getMessage() {
        return message;
    }



    public boolean isRequiredMissing() {
        return requiredMissing;
    }

}
