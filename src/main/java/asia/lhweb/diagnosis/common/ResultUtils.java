package asia.lhweb.diagnosis.common;

import asia.lhweb.diagnosis.common.enums.ErrorCode;

/**
 * 提供用于创建操作结果的工具方法。
 *
 * @author yupi
 */
public class ResultUtils {

    /**
     * 构建一个表示操作成功的响应对象。
     *
     * @param data 操作成功时返回的数据。
     * @param <T>  数据类型。
     * @return 包含成功状态、数据和消息的{@link BaseResponse}对象。
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, data, "ok");
    }

    /**
     * 构建一个表示操作成功的响应对象，可以自定义成功消息。
     *
     * @param data     操作成功时返回的数据。
     * @param message  成功时的自定义消息。
     * @param <T>      数据类型。
     * @return 包含成功状态、数据和自定义消息的{@link BaseResponse}对象。
     */
    public static <T> BaseResponse<T> success(T data, String message) {
        return new BaseResponse<>(200, data, message);
    }

    /**
     * 构建一个表示操作失败的响应对象，基于错误代码。
     *
     * @param errorCode 错误代码，用于标识错误类型。
     * @return 包含错误状态和错误信息的{@link BaseResponse}对象。
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 构建一个自定义状态码和消息的失败响应对象。
     *
     * @param code        操作失败时的状态码。
     * @param message     失败时的自定义消息。
     * @param description 对错误的详细描述。
     * @return 包含自定义状态码、消息和描述的{@link BaseResponse}对象。
     */
    public static BaseResponse error(int code, String message, String description) {
        return new BaseResponse(code, null, message, description);
    }

    /**
     * 构建一个基于错误代码和自定义消息的失败响应对象。
     *
     * @param errorCode   错误代码，用于标识错误类型。
     * @param message     失败时的自定义消息。
     * @param description 对错误的详细描述。
     * @return 包含错误状态码、自定义消息和描述的{@link BaseResponse}对象。
     */
    public static BaseResponse error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse(errorCode.getCode(), null, message, description);
    }

    /**
     * 构建一个基于错误代码和描述的失败响应对象。
     *
     * @param errorCode   错误代码，用于标识错误类型。
     * @param description 对错误的详细描述。
     * @return 包含错误状态码和描述的{@link BaseResponse}对象。
     */
    public static BaseResponse error(ErrorCode errorCode, String description) {
        return new BaseResponse(errorCode.getCode(), errorCode.getMessage(), description);
    }
}
