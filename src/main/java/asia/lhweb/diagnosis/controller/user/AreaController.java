package asia.lhweb.diagnosis.controller.user;

import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.model.domain.Area;
import asia.lhweb.diagnosis.service.AreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :罗汉
 * @date : 2024/5/13
 */
@Api("领域相关接口")
@RestController("userAreaController")
@RequestMapping("/user/area")
public class AreaController {
    @Resource
    private AreaService areaService;
    @GetMapping("/getAreaList")
    @ApiOperation("获取领域列表")
    public BaseResponse<List<Area>> getAreaList() {
        return areaService.getAreaList();
    }

}
