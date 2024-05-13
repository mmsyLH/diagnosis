package asia.lhweb.diagnosis.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author :罗汉
 * @date : 2024/5/9
 */
@Builder
@Data
public class SysUserStatisticsVO {

    //日期，以逗号分隔，例如：2022-10-01,2022-10-02,2022-10-03
    private String dateList;

    //用户量，以逗号分隔，例如：20,66,48,95
    private String userList;
    // //日期，以逗号分隔，例如：2022-10-01,2022-10-02,2022-10-03
    // private List<LocalDate> dateList;
    //
    // //用户量，以逗号分隔，例如：20,66,48,95
    // private List<Integer> userList;

}
