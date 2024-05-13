package asia.lhweb.diagnosis.service.impl;


import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.mapper.AreaMapper;
import asia.lhweb.diagnosis.model.domain.Area;
import asia.lhweb.diagnosis.service.AreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author Administrator
* @description 针对表【area(领域表)】的数据库操作Service实现
* @createDate 2024-04-28 20:11:50
*/
@Service
public class AreaServiceImpl
implements AreaService{
    @Resource
    private AreaMapper areaMapper;

    @Override
    public BaseResponse<List<Area>> getAreaList() {
        List<Area> areaList=areaMapper.getAreaList();
        if (areaList.isEmpty()){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(areaList);
    }
}
