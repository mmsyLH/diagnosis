package asia.lhweb.diagnosis.model.dto;

import lombok.Data;

/**
 * @author :罗汉
 * @date : 2024/5/14
 */
@Data
public class QuestionTestDTO {
    private Integer id;
    private Integer userId;
    private String questionsJsonStr;
}
