package asia.lhweb.diagnosis.common;

import java.io.Serializable;
import java.util.Map;

/**
 * 后端统一返回结果 1.0初版
 *
 * @param <T>
 * @author 罗汉
 * @date 2024/02/26
 */
public class Result<T> implements Serializable {

    private int code;//200成功  0失败
    private String msg;
    private Map<String, String> msgMap;
    private T data;

    public Result() {
    }

    /**
     * 成功
     *
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.code = 200;
        return result;
    }

    /**
     * 成功
     *
     * @param msg 味精
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> success(String msg) {
        Result<T> result = new Result<>();
        result.msg=msg;
        result.code = 200;
        return result;
    }

    /**
     * 成功
     *
     * @param object 对象
     * @param msg    味精
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> success(T object,String msg) {
        Result<T> result = new Result<>();
        result.data = object;
        result.code = 200;
        result.msg = msg;
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.msg = msg;
        result.code = 0;
        return result;
    }
    public static <T> Result<T> error(int code,String msg) {
        Result<T> result = new Result<>();
        result.msg = msg;
        result.code = code;
        return result;
    }
    public static <T> Result<T> error(Map<String, String> map) {
        Result<T> result = new Result<>();
        result.msgMap = map;
        result.code = 0;
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, String> getMsgMap() {
        return msgMap;
    }

    public void setMsgMap(Map<String, String> msgMap) {
        this.msgMap = msgMap;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}