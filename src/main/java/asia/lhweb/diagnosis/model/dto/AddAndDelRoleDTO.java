package asia.lhweb.diagnosis.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author :罗汉
 * @date : 2024/5/7
 */
@Data
public class AddAndDelRoleDTO {
    private Integer roleId;
    private List<Integer> menuIds;
}
