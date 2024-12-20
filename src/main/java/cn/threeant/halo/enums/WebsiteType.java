package cn.threeant.halo.enums;

public enum WebsiteType {
    CSDN(1, "CSDN"),
    JIANSHU(2, "简书"),
    ZHIHU(3, "知乎"),
    WECHAT(4, "微信公众号"),
    CNBLOGS(5, "博客园"),
    ALICLOUD(6, "阿里云"),
    JUEJIN(7, "掘金"),
    OSCHINA(8, "开源中国"),
    HUAWEICLOUD(9, "华为云"),
    TENCENTCLOUD(10, "腾讯云"),
    OTHER(0, "其他");

    private final int code;
    private final String type;

    WebsiteType(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public static WebsiteType fromCode(int code) {
        for (WebsiteType type : WebsiteType.values()) {
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }
}
