package bootstrap.domain;

import java.util.HashMap;
import java.util.Map;

public class ResponseData {

    private String message;
    private int code;
    private final Map<String, Object> data = new HashMap<String, Object>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public ResponseData putDataValue(String key, Object value) {
        data.put(key, value);
        return this;
    }

    private ResponseData(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseData ok() {
        return new ResponseData(1, "Ok");
    }

    public static ResponseData fail(String msg) {
        return new ResponseData(0, msg);
    }


}
