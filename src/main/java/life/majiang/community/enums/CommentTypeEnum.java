package life.majiang.community.enums;

public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;

    CommentTypeEnum(int type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
