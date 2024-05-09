package asia.lhweb.diagnosis.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

import org.springframework.stereotype.Component;

/**
 * 支付宝配置
 *
 * @author 罗汉
 * @date 2024/05/09
 */
@Configuration
@Component
@Data
public class AlipayConfig {

    /**
     * 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
     */
    private String appId = "9021000122684299";

    /**
     * 商户私钥，您的PKCS8格式RSA2私钥
     */
    private String privateKey ="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCqkrN9tku0G30YZYQDg6JLhtA2TfD2zSk9I2oFqqQiWd4C6jg9wRy0dCHsiHtoFIsqqvvtGz5rn5AWACqmF9oNcKwjJ+vh+9mQBgcsXbDJW052wn+iRBh2P4bpSDlGHCNTOO/RVXjlgBl2JCYATTzs8uBh1n4F7KQZz1zDkLcPDnNbqcW1Pyimlr/FajZ/U+eGKsFXSxyiozq0ZPucQsp7vtvVyVn8u4TcK6rOTKlB3nSHvvo/pERs2L7KgNAKFagt5VwqOln0ii2AOt/QZULXp94jk9q3oAuGLGA33yTC5J5PdblBCN1b/AoQ/0S3SsUPabf9ZQpH28DIkwmLQbadAgMBAAECggEAaHjpzrl0rJ+ZgvPKY4ygProXlOswBzmpZHToXwgg95krTYW/ZLN+rVfj30fIrfrHMh7/pj3lgW19hrwbYxeAynKUPAQbiZIDsx6+DgJkYS+3Yy3FltE2WlaWfxNpYPOi/zoESKadIZPQBHgnfk/QXSB1JfeFwIMsD1LecjmUpXcn2a7LGYnYodJqaBnKbrrdbmrZZ9iZ3scqVbxayFbftHuOXP8170D0Qca6R7GhCk5OHrC/tjLJYnM0l73ihykXeuWWkLCYyi+mrqDpDSAmaB+O4hzLugmnZ4fVx5KZ8Ds/DSspgVeYgy/U8Aif9boz46t2I0j9D75l0ilOEWn20QKBgQDesBKbM2ljYBae0ZviZpcLCirnfXh2LlBhH2WjV0WN3r8xbitcF+DcK+m+cQFvfRld2F7hw9BwzRlAqGEjqtxHHzdc6XmoxnEldWOGbQlQEUeaXFBXKs6eMuqmfuj5+bZCOhikzQUB+JipEwES5xV4JeHWSLYKWO4mBqoy8DPZLwKBgQDEFt8m/A0DUc7NY4Qgj8m3txsfgOE0tMHQfpT2OjaPHnhjwxaqDM+drQy1JNBPdAHTsAU2Dq07SqqA5SmvP33Bqp6ua6Q8/I2cHhQxu0Ls4VQNLL9UxVdMTorHoS7ksJtSDhq8KErQ4rXAjl+lQoIGvu6aDd/mzPOJ3aUus8yh8wKBgFb/DFXLkPl8uXmUQ8T/9TQf5cEH+H6a+1XmESvt1UNmaOUjuBbDUVpSYX+/4CKxCa7q765ddFoNFjd3UHPBtUqnvFY09A1mcR+EK7yBbmj0RzfbpptXIt8U3yzqWZQG5RgcYDJJ0hICD4nF3d6HnpCQpBi10UBbm7YJVoaN5oOLAoGBAKjZjQRzy/zMyAgCJncneapxyLBC+Sm6cJ5JMZopHGv27/Hvvqdlaq26opOlBJ82JJCVJJec4byJmfJt4oweXvqSIhvNYtqFGqeRGxCL0KBLk2XY31RurbJue4BVIh0YmlZ5ALp+85WVcq5BqsiSUYbfiHQteFy0pQ1VR3TErHPTAoGBAI8458fo9fou7dNgk0+gLuXcAU905W+l0pNjavZTNQJkPW6ynQrB6yJcd04+wf0YkAJEHXYYoqWwooow2Aq6PgsMSPHjEvxtXGJ5C3/sR8PzJlcq605f15TG1Vb5FxipctDe+Kd2xzerAgvfpMzgT5sfeFxPnXykygehhUsvXmFl";
    /**
     * 支付宝公钥,
     */
    private String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqXa4cvoYDmFTikY1HNN4oVikXob5kpIay0kjQaXizMIxtNDNAT3hcC7nhOEIyTFxARQzdqnjW6rrj5tKRXV7ZlpNUz4jEW2KoduVHGOm8MMEOuoxENQHftl2VGvMF73k/oaAd0gJOcYQWyklGXcnwSn4nw2nzoEO4WpH1YjapSa710M0brlPxHORsYW/RIH8z6qZX96oKNGqQlpUkmc0iAnV6NOxJzodYlCAZOVEO/X66hma0lowRRM4gVG1H1CCv6BIfT+IqctZZwCdFszQ2/mw8bXNRFa6B1mD03rRjD/y6yMKalXWYjdYMI8ejLhgVr/x6YtM15l4yj3nHn9htQIDAQAB";
    /**
     * 服务器异步通知页面路径需http://格式的完整路径，不能加?id=123这类自定义参数
     */
    private String notifyUrl = "http://localhost:8092/#/success";

    /**
     * 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数
     */
    private String returnUrl = "http://localhost:8092/#/success";

    /**
     * 签名方式
     */
    private String signType = "RSA2";

    /**
     * 字符编码格式
     */
    private String charset = "utf-8";

    /**
     * 支付宝网关
     */
    private String gatewayUrl = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";

    /**
     * 支付宝网关
     */
    private String logPath = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
}
