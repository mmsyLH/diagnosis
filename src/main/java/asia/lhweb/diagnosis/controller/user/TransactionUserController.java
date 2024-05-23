package asia.lhweb.diagnosis.controller.user;

import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.TransactionUser;
import asia.lhweb.diagnosis.service.TransactionUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author :罗汉
 * @date : 2024/5/11
 */
@Api("流水相关接口")
@RestController("userTransactionUserController")
@RequestMapping("/user/transaction")
public class TransactionUserController {
    @Resource
    private TransactionUserService transactionUserService;

    /**
     * 获取预约分页列表
     *
     * @param transactionUser 交易用户
     * @return {@link BaseResponse}<{@link PageResult}<{@link TransactionUser}>>
     */
    @GetMapping("/transactionUserPage")
    @ApiOperation("获取预约分页列表")
    public BaseResponse<PageResult<TransactionUser>> getAppointmentList(TransactionUser transactionUser) {
        return transactionUserService.transactionUserPage(transactionUser);
    }
}
