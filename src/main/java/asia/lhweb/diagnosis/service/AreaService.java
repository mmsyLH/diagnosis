package asia.lhweb.diagnosis.service;


import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.model.domain.Area;

import java.util.List;

/**
* @author Administrator
* @description 针对表【area(领域表)】的数据库操作Service
* @createDate 2024-04-28 20:11:50
*/
public interface AreaService{

    BaseResponse<List<Area>> getAreaList();
}
