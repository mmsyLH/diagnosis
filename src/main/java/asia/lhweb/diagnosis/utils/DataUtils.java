package asia.lhweb.diagnosis.utils;


import asia.lhweb.diagnosis.constant.BaseConstant;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

/**
 * @author :罗汉
 * @date : 2024/1/29
 */
public class DataUtils {
    /**
     * 随机生成id
     *
     * @return
     */
    public static String getId() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * 生成随机码
     *
     * @return
     */
    public static String getCode() {
        return getId();
    }
    /**
     * MD5加密密码
     *
     * @param password 密码
     * @return {@link String}
     */
    public static String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((BaseConstant.SALT + password).getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 验证密码
     *
     * @param inputPassword     输入密码
     * @param encryptedPassword 加密密码
     * @return boolean
     */
    public static boolean verifyPassword(String inputPassword, String encryptedPassword) {
        // 对用户输入的密码进行MD5加密
        String inputEncryptedPassword = encryptPassword(inputPassword);
        // 比对加密后的密码是否匹配
        return inputEncryptedPassword.equals(encryptedPassword);
    }

    /**
     * 解读url编码转中文
     *
     * @param encodedString 编码字符串
     * @return {@link String}
     */
    public static String decodeChinese(String encodedString) {
        try {
            return URLDecoder.decode(encodedString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 是空还是空
     *
     * @param strings 字符串
     * @return boolean
     */
    public static boolean isAnyNullOrEmpty(String... strings) {
        for (String str : strings) {
            if (str == null || str.isEmpty()) {
                return true;
            }
        }
        return false;
    }


    // /**
    //  * 处理null或空字符串的情况
    //  *
    //  * @param resp    响应对象
    //  * @param gson    Gson对象
    //  * @param strings 要检查的字符串
    //  * @return 如果所有字符串都不为空或null，则返回true；否则返回false
    //  */
    // public static boolean handleNullOrEmpty(HttpServletResponse resp, Gson gson, String... strings) {
    //     if (isAnyNullOrEmpty(strings)) {
    //         // 设置响应内容类型为JSON
    //         resp.setContentType("application/json");
    //         try {
    //             resp.getWriter().write(gson.toJson(Result.error("操作失败，上传的属性不能为空")));
    //         } catch (IOException e) {
    //             throw new RuntimeException(e);
    //         }
    //         return false;
    //     } else {
    //         return true;
    //     }
    // }
    // 生成随机验证码
    public static String generateRandomCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10)); // 生成0到9的随机数字
        }
        return sb.toString();
    }
    /**
     * 复制源对象中非空属性值到目标对象中。
     *
     * @param source 源对象，其属性值将被复制。
     * @param target 目标对象，将接收源对象的非空属性值。
     * @param <T> 源对象和目标对象的通用类型。
     * @return 返回经过属性值复制后的目标对象。
     */
    public static <T> T copyNonNullProperties(T source, T target) {
        // 获取源对象和目标对象的类类型
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();

        // 获取源对象的所有字段
        Field[] fields = sourceClass.getDeclaredFields();
        for (Field field : fields) {
            try {
                // 允许访问私有字段
                field.setAccessible(true);
                // 获取字段当前值
                Object value = field.get(source);
                // 如果字段值非空，则将其值复制到目标对象对应的字段上
                if (value != null) {
                    Field targetField = targetClass.getDeclaredField(field.getName());
                    targetField.setAccessible(true); // 允许访问私有字段
                    targetField.set(target, value); // 设置字段值
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return target;
    }


    /**
     * 是否是数字
     *
     * @param strings 字符串参数列表
     * @return boolean
     */
    public static boolean isNumeric(String... strings) {
        for (String str : strings) {
            if (str == null || str.isEmpty()) {
                return false;
            }
            for (char c : str.toCharArray()) {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
        }
        return true;
    }
}
