package asia.lhweb.diagnosis.service.impl;

import asia.lhweb.diagnosis.common.BaseContext;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.constant.BaseConstant;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.mapper.SysUserMapper;
import asia.lhweb.diagnosis.mapper.TransactionUserMapper;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.SysUser;
import asia.lhweb.diagnosis.model.domain.TransactionUser;
import asia.lhweb.diagnosis.model.dto.SysUserDTO;
import asia.lhweb.diagnosis.model.vo.LoginUserVO;
import asia.lhweb.diagnosis.model.vo.SysUserStatisticsVO;
import asia.lhweb.diagnosis.service.SysUserService;
import asia.lhweb.diagnosis.utils.JwtUtil;
import asia.lhweb.diagnosis.utils.cache.RedisCacheUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【sys_user(系统用户表)】的数据库操作Service实现
 * @createDate 2024-04-28 20:11:50
 */
@Service
public class SysUserServiceImpl
        implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private RedisCacheUtil redisCacheUtil;
    @Resource
    private TransactionUserMapper transactionUserMapper;

    /**
     * 按id删除
     *
     * @param i 我
     * @return int
     */
    @Override

    public boolean deleteById(int i) {
        Long id = Long.valueOf(i);
        int res = sysUserMapper.deleteByPrimaryKey(id);
        if (res <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "删除失败");
        }

        return true;
    }

    /**
     * 用户注销
     *
     * @param request 请求
     * @return int
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        // 移除登录
        // request.getSession().removeAttribute(BaseConstant.USER_LOGIN_STATE);
        // jwt去除登录令牌
        BaseContext.removeCurrentId();
        return 1;
    }

    /**
     * 用户登录
     *
     * @param userAccount  用户帐户
     * @param userPassword 用户密码
     * @param request      请求
     * @return {@link LoginUserVO}
     */
    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1 校验
        if (!StringUtils.hasLength(userAccount) || !StringUtils.hasLength(userPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "用户或者密码为空");
        }
        // 4-20字符
        if (userAccount.length() > 20 || userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度需要在4-20字符");
        }
        if (userPassword.length() > 15 || userPassword.length() < 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度需要在6-15字符");
        }
        // 账户不能包含特殊字符
        String validRule = "^[a-zA-Z\\d]+$";
        Matcher matcher = Pattern.compile(validRule).matcher(userAccount);
        // 如果包含非法字符，则返回
        if (!matcher.matches()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不能包含非法字符");
        }

        // 2 加密 import org.springframework.util.DigestUtils;
        String encryptPassword = DigestUtils.md5DigestAsHex((BaseConstant.SALT + userPassword).getBytes());

        // 查询用户是否存在
        SysUser user = sysUserMapper.selectOne(userAccount, encryptPassword);
        // 用户不存在
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号或者密码错误 ");
        }

        // 3 脱敏
        LoginUserVO cleanUser = getSafetyUser(user);

        // 4 token
        String token = createToken(cleanUser);
        cleanUser.setToken(token);

        // 5 redis存token 毫秒
        redisCacheUtil.setCacheObject(String.format(BaseConstant.REDIS_USER_KEY_FORMAT, cleanUser.getUserAccount()), cleanUser, BaseConstant.JWT_EXPIRE_TIME, TimeUnit.MILLISECONDS);

        return cleanUser;
    }

    /**
     * 创建令牌
     *
     * @param cleanUser 干净用户
     * @return 令牌
     */
    private String createToken(LoginUserVO cleanUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userAccount", cleanUser.getUserAccount());
        claims.put("userName", cleanUser.getUserName());
        return createToken(claims);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims) {
        String token = JwtUtil.createJWT(BaseConstant.JWT_EXPIRE_TIME, claims);
        if (!StringUtils.hasText(token)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "令牌生成失败");
        }
        return token;
    }

    /**
     * 获取分页列表
     *
     * @param sysUserDTO 系统用户dto
     * @return {@link PageResult}<{@link SysUser}>
     */
    @Override
    public PageResult<SysUser> page(SysUserDTO sysUserDTO) {
        int pageNo = sysUserDTO.getPageNo();
        int pageSize = sysUserDTO.getPageSize();
        PageHelper.startPage(pageNo, pageSize);
        // 紧跟着的第一个select方法会被分页
        Page<SysUser> adminPage = sysUserMapper.selectAllIf(sysUserDTO);
        if (adminPage == null || adminPage.getResult().size() == 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        PageResult<SysUser> pageResult = new PageResult<>();
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalRow((int) adminPage.getTotal());
        pageResult.setItems(adminPage.getResult());
        pageResult.setPageTotalCount(adminPage.getPages());
        return pageResult;
    }


    /**
     * 获得指定时间范围用户统计
     *
     * @param begin 开始日期
     * @param end   结束日期
     * @return {@link SysUserStatisticsVO} 包含指定时间范围内每天的新用户数量统计
     */
    @Override
    public SysUserStatisticsVO getUserStatistics(LocalDate begin, LocalDate end) {

        // 1. 计算日期范围内的每一天
        // 存放每天日期的集合
        List<LocalDate> dateList = new ArrayList<>();

        dateList.add(begin);

        if (begin.isAfter(end)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "开始日期不能大于结束日期");
        }

        while (!begin.equals(end)) {
            // 日期计算，计算指定日期的后一天对应的日期
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        // 使用Java 8 Stream API将日期列表转换为逗号分隔的字符串
        String dateStr = dateList.stream()
                .map(LocalDate::toString)
                .collect(Collectors.joining(","));

        // 2. 统计每天的新用户数量
        List<Integer> newUserCountList = new ArrayList<>(); // 存放每天的新用户数量

        for (LocalDate date : dateList) {
            // 查询当天注册的用户数量
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN); // 当天开始时间
            LocalDateTime endTime = LocalDateTime.of(date.plusDays(1), LocalTime.MIN).minusNanos(1); // 下一天开始时间减一纳秒，确保区间是完整的

            // 统计指定日期范围内注册的用户数量
            Integer newUserCount = sysUserMapper.countNewUsersByDateRange(beginTime, endTime);


            // 如果查询结果为null，则将其视为0
            if (newUserCount == null) {
                newUserCount = 0;
            }
            newUserCountList.add(newUserCount);
        }
        // 使用Java 8 Stream API将用户数量列表转换为逗号分隔的字符串
        String userListStr = newUserCountList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        // 3. 封装统计结果为SysUserStatisticsVO对象返回
        return SysUserStatisticsVO.builder()
                .dateList(dateStr)
                .userList(userListStr)
                .build();
    }

    /**
     * 用户注册
     *
     * @param sysUser 系统用户
     * @return int
     */
    @Override
    public int userRegister(SysUser sysUser) {
        if (sysUser == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = sysUser.getUserAccount();
        // 各种空判断 账户、密码、手机号、通讯地址、性别
        if (!StringUtils.hasLength(sysUser.getPhone())
                || !StringUtils.hasLength(sysUser.getAddress())
                || !StringUtils.hasLength(userAccount)
                || !StringUtils.hasLength(sysUser.getUserPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        // 性别不能为空
        if (sysUser.getSex() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "性别不能为空");
        }
        // 账户长度 4-20个字符
        if (userAccount.length() < 4 || userAccount.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号长度需要4-20个字符");
        }
        // 账户不能包含特殊字符
        String validRule = "^[a-zA-Z0-9]+$";
        Matcher matcher = Pattern.compile(validRule).matcher(userAccount);
        // 如果包含非法字符，则返回
        if (!matcher.matches()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户包含非法字符");
        }
        // 密码 6-15字符
        if (sysUser.getUserPassword().length() < 6 || sysUser.getUserPassword().length() > 15) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码长度需要 6-15字符");
        }
        // 判断账户是否存在
        if (sysUserMapper.selectByUserAccount(userAccount) != null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户已存在");
        }
        // 加密 import org.springframework.util.DigestUtils;
        String encryptPassword = DigestUtils.md5DigestAsHex((BaseConstant.SALT + sysUser.getUserPassword()).getBytes());
        sysUser.setUserPassword(encryptPassword);
        int i = sysUserMapper.insertSelective(sysUser);
        if (i <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败");
        }
        return i;
    }

    /**
     * 获取登录用户vo
     *
     * @param request 请求
     * @return {@link LoginUserVO}
     */
    @Override
    public LoginUserVO getLoginUserVO(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        String token = request.getHeader(BaseConstant.USER_TOKEN_NAME);
        if (!StringUtils.hasLength(token)) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        LoginUserVO loginUserVO;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            System.err.println(claims);
            String userAccount = claims.get("userAccount", String.class);
            String redisKey = String.format(BaseConstant.REDIS_USER_KEY_FORMAT, userAccount);
            if (!redisCacheUtil.hasKey(redisKey)) {
                throw new BusinessException(ErrorCode.NOT_LOGIN);
            }
            // 从数据库拿
            loginUserVO = redisCacheUtil.getCacheObject(redisKey);
            if (loginUserVO == null) {
                throw new BusinessException(ErrorCode.NOT_LOGIN);
            }
            SysUser sysUser = sysUserMapper.selectByPrimaryKey(Long.valueOf(loginUserVO.getId()));
            loginUserVO= getSafetyUser(sysUser);
            loginUserVO.setToken(token);
            // 更新缓存中的管理员信息
            redisCacheUtil.setCacheObject(redisKey, loginUserVO, BaseConstant.REDIS_EXPIRATION_TIME, TimeUnit.SECONDS);

        } catch (Exception e) {// 说明解密失败
            e.printStackTrace();
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        return loginUserVO;
    }

    /**
     * 更新用户
     *
     * @param sysUser 系统用户
     * @return int
     */
    @Override
    public int updateUser(SysUser sysUser) {
        if (sysUser == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (sysUser.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        SysUser sysUser1 = sysUserMapper.selectByPrimaryKey(Long.valueOf(sysUser.getId()));
        if (sysUser1 == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.hasLength(sysUser.getUserPassword())) {
            String encryptPassword = DigestUtils.md5DigestAsHex((BaseConstant.SALT + sysUser.getUserPassword()).getBytes());
            sysUser.setUserPassword(encryptPassword);
        }

        return sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

    /**
     * 更新头像url
     *
     * @param userId    用户id
     * @param avatarUrl 《阿凡达》url
     * @return {@link Boolean}
     */
    @Override
    public Boolean updateAvatarUrl(Integer userId, String avatarUrl) {
        if (userId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.hasLength(avatarUrl)){
            SysUser sysUser = new SysUser();
            sysUser.setId(userId);
            sysUser.setAvatar(avatarUrl);
            return sysUserMapper.updateByPrimaryKeySelective(sysUser) > 0;
        }
        return false;
    }

    /**
     * 添加钱
     *
     * @param userId     用户id
     * @param money      钱
     * @param outTradeNo 我们贸易号
     * @return {@link Boolean}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addMoney(Integer userId, Double money, String outTradeNo) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(Long.valueOf(userId));
        if (sysUser == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //添加金钱
        SysUser sysUser1 = new SysUser();
        sysUser1.setId(userId);
        sysUser1.setMoney(sysUser.getMoney() + money);
        if (sysUserMapper.updateByPrimaryKeySelective(sysUser1) <= 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        //添加流水
        TransactionUser transactionUser = new TransactionUser();
        transactionUser.setTransactionId(outTradeNo);
        transactionUser.setUserId(userId);
        transactionUser.setAdminId(1);
        transactionUser.setTransactionType(1);
        transactionUser.setTransactionInfo("余额充值");
        transactionUser.setPayChannel(1);
        transactionUser.setMoney(money);
        transactionUser.setTransactionStatus(1);
        if (transactionUserMapper.insertSelective(transactionUser) <= 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return true;

    }


    /**
     * 获得安全用户
     *
     * @param user 用户
     * @return {@link LoginUserVO}
     */
    private LoginUserVO getSafetyUser(SysUser user) {
        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setId(user.getId());
        loginUserVO.setUserAccount(user.getUserAccount());
        loginUserVO.setUserName(user.getUserName());
        loginUserVO.setAvatar(user.getAvatar());
        loginUserVO.setPhone(user.getPhone());
        loginUserVO.setAddress(user.getAddress());
        loginUserVO.setSex(user.getSex());
        loginUserVO.setAge(user.getAge());
        loginUserVO.setMoney(user.getMoney());
        return loginUserVO;
    }
}
