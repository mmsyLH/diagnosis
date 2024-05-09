package asia.lhweb.diagnosis.service.impl;


import asia.lhweb.diagnosis.model.AlipayBean;
import asia.lhweb.diagnosis.service.PayService;
import asia.lhweb.diagnosis.utils.Alipay;
import com.alipay.api.AlipayApiException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PayServiceImpl implements PayService {

    @Resource
    private Alipay alipay;

    @Override
    public String aliPay(AlipayBean alipayBean) throws AlipayApiException {
        return alipay.pay(alipayBean);
    }
}

