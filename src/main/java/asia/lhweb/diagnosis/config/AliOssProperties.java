package asia.lhweb.diagnosis.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class AliOssProperties {
    @Value("oss-cn-beijing.aliyuncs.com")
    private String endpoint;
    @Value("LTAI5tEgoRKAWTTF77dCnUVw")
    private String accessKeyId;
    @Value("mpuzpVeQuMErqWlJtGgQKyFO0xzM0t")
    private String accessKeySecret;
    @Value("lhwaimai")
    private String bucketName;
}
