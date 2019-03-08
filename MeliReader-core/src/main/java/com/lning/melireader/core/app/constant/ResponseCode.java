package com.lning.melireader.core.app.constant;

public enum ResponseCode {
    ERROR(0000, "ERROR"),
    NOT_NEED_SHOW_MESSAGE(1000, "无关紧要的消息"),
    SUCCESS(2000, "SUCCESS"),
    TIME_OUT(3002, "请求超时"),
    INVALID_APP_ID(3003, "无效的APPKEY参数"),
    INVALID_SERVICE_METHOD(3004, "无效的服务方法名"),
    TOKEN_ERROR(4003, "尚未登录或登录令牌失效"),
    DATA_NULL(4004, "数据暂无"),
    ILLEGAL_ARGUMENT(5001, "参数无效"),
    UNKNOW_ERROR(9001, "系统错误"), UNKNOWN_SERVER_HOST(9002, "未知域名");

    private final int status;
    private final String msg;

    private ResponseCode(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
