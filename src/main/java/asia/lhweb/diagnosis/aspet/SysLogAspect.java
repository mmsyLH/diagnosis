package asia.lhweb.diagnosis.aspet;

import asia.lhweb.diagnosis.annotation.Log;
import asia.lhweb.diagnosis.model.domain.SysLog;
import asia.lhweb.diagnosis.model.vo.LoginAdminVO;
import asia.lhweb.diagnosis.service.SysAdminService;
import asia.lhweb.diagnosis.service.SysLogService;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * 系统日志方面
 * 环绕通知
 * 内容：某个人（管理员id）在什么时间（操作时间）操作了什么（操作内容），入参字符串、出参json格式字符串。
 *
 * @author 罗汉
 * @date 2024/05/14
 */
@Aspect
@Order(value = -1)// 值越小 优先级越高
public class SysLogAspect {

    @Resource
    private SysLogService sysLogService;
    @Resource
    private SysAdminService sysAdminService;

    @Around(value = "@annotation(log)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint, Log log) throws Throwable {

        // 构建前置参数
        SysLog sysLog = new SysLog();
        beforeHandleLog(log, joinPoint, sysLog);
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();                      // 执行业务方法
            afterHandlLog(log, proceed, sysLog, 0, null);  // 构建响应结果参数
        } catch (Throwable e) {                                 // 代码执行进入到catch中，业务方法执行产生异常
           //接着往外抛出异常
            afterHandlLog(log, proceed, sysLog, 1, e.toString());
            throw e;
        }
        // 保存日志数据
        sysLogService.save(sysLog);

        // 返回执行结果
        return proceed;
    }

    private void afterHandlLog(Log log, Object proceed, SysLog sysLog, int status, String errorMsg) {
        String statusStr= status == 0 ? "成功" : "失败";
        if (log.isSaveResponseData()) {
            sysLog.setLogResult(String.format("执行结果：%s", statusStr));
            if (status==0){
                sysLog.setOutputParams(JSON.toJSONString(proceed));
            }
            else if (status==1){
                sysLog.setOutputParams(JSON.toJSONString(errorMsg));
            }
        }

    }

    private void beforeHandleLog(Log log, ProceedingJoinPoint joinPoint, SysLog sysLog) {
        // 获取目标方法信息
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        // 获取请求相关参数
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        LoginAdminVO loginAdminVO = sysAdminService.getLoginAdminVO(request);
        sysLog.setAdminId(loginAdminVO.getId());
        String requestMethod = request.getMethod();
        String fullMethodName = method.getDeclaringClass().getName() + "." + method.getName();
        //是get
        if (HttpMethod.GET.name().equals(requestMethod)) {
            sysLog.setLogContent(String.format("%s:%s:%s:%s?%s",log.businessType(),requestMethod, request.getRequestURI(), fullMethodName, request.getQueryString()));
        }
        if (HttpMethod.POST.name().equals(requestMethod)){
            if (request.getContentType().contains("multipart/form-data")) {
                sysLog.setLogContent(String.format("%s:%s:%s:%s",log.businessType(),requestMethod, request.getRequestURI(), fullMethodName));
                return;
            }
            sysLog.setLogContent(String.format("%s:%s:%s:%s",log.businessType(),requestMethod, request.getRequestURI(), fullMethodName));
        }

        // 设置请求参数
        if (log.isSaveRequestData()) {
            if (HttpMethod.GET.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
                String params = argsArrayToString(joinPoint.getArgs());
                sysLog.setInputParams(params);
            }
        }
    }

    // 参数拼装
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (!StringUtils.isEmpty(o) && !isFilterObject(o)) {
                    try {
                        Object jsonObj = JSON.toJSON(o);
                        params += jsonObj.toString() + " ";
                    } catch (Exception e) {
                    }
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {       // 判断是否为数组类型
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);  // 如果是数组，判断其元素类型是否为MultipartFile或其子类
        } else if (Collection.class.isAssignableFrom(clazz)) { // 判断是否为Collection集合类型
            Collection collection = (Collection) o;
            for (Object value : collection) {  // 只要有一个元素的类型为MultipartFile或其子类，则认为该集合对象为过滤对象
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {  // 判断是否为Map集合类型
            Map map = (Map) o;
            for (Object value : map.entrySet()) {  // 只要有一个Value的类型是MultipartFile或其子类，则认为该映射对象为过滤对象
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }

        // 如果以上条件都不满足，最后判断对象本身是否为MultipartFile、HttpServletRequest、HttpServletResponse类的实例
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }
}


