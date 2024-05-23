package asia.lhweb.diagnosis.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 页面请求
 *
 * @author 罗汉
 * @date 2024/04/25
 */
@Data
public class PageRequest implements Serializable {
    private int pageSize=8;
    private int pageNo=1;
    private static final long serialVersionUID = 1L;
}
