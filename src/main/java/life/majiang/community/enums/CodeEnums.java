package life.majiang.community.enums;

public enum CodeEnums {
    OK(2000,"请求成功"),

    USER_NOT_LOGIN(3000,"用户未登录"),

    QUESTION_IS_NOT_EXIST(6000,"问题不存在"),

    COMMENT_IS_NOT_EXIST(7000,"评论不存在"),
    COMMENT_CONTENT_IS_NULL(7001,"评论内容不能为空");
    private Integer code;
    private String message;

    CodeEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
