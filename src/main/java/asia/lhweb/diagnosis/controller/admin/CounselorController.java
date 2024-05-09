package asia.lhweb.diagnosis.controller.admin;

import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.Counselor;
import asia.lhweb.diagnosis.model.dto.CounselorDTO;
import asia.lhweb.diagnosis.service.CounselorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author :罗汉
 * @date : 2024/5/6
 */
@Api("咨询师相关接口")
@RestController
@RequestMapping("/admin/counselor")
public class CounselorController {
    @Resource
    private CounselorService counselorService;

    /**
     * 获取分页列表
     *
     * @param counselorDTO 辅导员dto
     * @return {@link BaseResponse}<{@link PageResult}<{@link Counselor}>>
     */
    @GetMapping("/page")
    @ApiOperation("获取分页列表")
    public BaseResponse<PageResult<Counselor>> page(CounselorDTO counselorDTO) {
        PageResult<Counselor> sysAdminPageResult = counselorService.page(counselorDTO);
        return ResultUtils.success(sysAdminPageResult);
    }

    /**
     * 按id删除
     *
     * @param id id
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @GetMapping ("/delete")
    @ApiOperation("删除")
    public BaseResponse<Boolean> deleteById(@RequestParam("id") Integer id) {
        if (id==null||id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "要删除的id有误");
        }
        boolean res = counselorService.deleteById(id);
        return ResultUtils.success(res,"删除成功");
    }
}
