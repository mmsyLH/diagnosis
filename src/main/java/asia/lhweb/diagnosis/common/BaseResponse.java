package asia.lhweb.diagnosis.common;

import asia.lhweb.diagnosis.common.enums.ErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类 后端统一返回结果 2.0
 *
 * @param <T>
 * @author yupi
 * @date 2024/03/11
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T data, String message) {
        this(code, data, message, "");
    }

    public BaseResponse(int code, T data) {
        this(code, data, "", "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }
}
