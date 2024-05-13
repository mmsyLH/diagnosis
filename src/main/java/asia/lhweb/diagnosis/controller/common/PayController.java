package asia.lhweb.diagnosis.controller.common;

import asia.lhweb.diagnosis.model.AlipayBean;
import asia.lhweb.diagnosis.service.PayService;
import com.alipay.api.AlipayApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 支付控制器
 *
 * @author 罗汉
 * @date 2024/05/09
 */
@RestController
@Api("支付相关接口")
@ApiOperation("沙箱支付相关接口")
@RequestMapping("/pay")
public class PayController {

    @Resource
    private PayService payService;

    /**
     * 处理支付宝支付的请求。
     * 该方法接收一个AlipayBean对象作为请求体，其中包含了进行支付宝支付所需的所有信息。
     * 方法会调用支付服务进行支付处理，并返回一个包含支付页面HTML内容的响应实体。
     *
     * @param alipayBean 包含支付宝支付所需信息的请求体对象。
     * @return 返回一个ResponseEntity对象，其中包含支付页面的HTML内容、响应头信息和状态码。
     * @throws AlipayApiException 如果支付宝接口调用出现异常，则抛出此异常。
     */
    @PostMapping(value = "alipay", produces = "text/html;charset=UTF-8")
    public ResponseEntity<String> alipay(@RequestBody AlipayBean alipayBean) throws AlipayApiException {
        // 调用支付服务进行支付处理，返回支付页面的HTML内容
        String htmlContent = payService.aliPay(alipayBean);

        // 设置响应头，指定返回的内容类型为HTML
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);

        // 构建并返回包含支付页面HTML内容的响应实体
        return new ResponseEntity<>(htmlContent, headers, HttpStatus.OK);
    }
}

