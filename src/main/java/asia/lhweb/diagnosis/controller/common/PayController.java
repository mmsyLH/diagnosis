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
import java.io.UnsupportedEncodingException;


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

    // @PostMapping("alipay")
    /*可替换为:
    @RequestMapping(value = "pay/alipay" , produces = "text/html;charset=utf-8")
    @ResponseBody
    */
    // public String alipay(String outTradeNo, String subject, String totalAmount, String body) throws AlipayApiException, UnsupportedEncodingException {
    @PostMapping(value = "alipay", produces = "text/html;charset=UTF-8")
    public ResponseEntity<String> alipay(@RequestBody AlipayBean alipayBean) throws AlipayApiException, UnsupportedEncodingException {
        String htmlContent = payService.aliPay(alipayBean);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<>(htmlContent, headers, HttpStatus.OK);
    }
}

