package asia.lhweb.diagnosis.controller.admin;

import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.TransactionCounselor;
import asia.lhweb.diagnosis.model.vo.TransactionCounselorVO;
import asia.lhweb.diagnosis.service.TransactionCounselorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/admin/transactionCounselor")
@Api("交易记录相关接口")
public class TransactionCounselorController {
    @Resource
    private TransactionCounselorService transactionCounselorService;

    @ApiOperation("交易记录分页")
    @GetMapping("/page")
    public BaseResponse<PageResult<TransactionCounselorVO>> page(TransactionCounselor transactionCounselor) {
        PageResult<TransactionCounselorVO> transactionCounselorVOPageResult = transactionCounselorService.page(transactionCounselor);
        return ResultUtils.success(transactionCounselorVOPageResult);
    }
}
