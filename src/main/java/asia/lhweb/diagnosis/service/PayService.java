package asia.lhweb.diagnosis.service;


import asia.lhweb.diagnosis.model.AlipayBean;
import com.alipay.api.AlipayApiException;

/**
 * 支付服务
 *
 * @author 罗汉
 * @date 2024/05/09
 */
public interface PayService {

    /**
     * 支付宝支付接口
     * @param alipayBean
     * @return
     * @throws AlipayApiException
     */
    String aliPay(AlipayBean alipayBean) throws AlipayApiException;

}

