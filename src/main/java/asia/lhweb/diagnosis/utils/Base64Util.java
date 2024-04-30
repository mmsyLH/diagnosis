package asia.lhweb.diagnosis.utils;

// import org.apache.tika.Tika;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;

/**
 * base64跑龙套
 * base64工具类
 *
 * @author 罗汉
 * @date 2024/03/27
 */
public class Base64Util {
    /**
     * 从Base64编码的字符串生成文件。
     *
     * @param base64 经过Base64编码的字符串，通常代表一个图片。
     * @param path 保存图片的目录路径。
     * @return 生成文件的文件名，如果出错则返回null。
     */
    public static String generateFile(String base64, String path) {
        // 尝试解密并保存base64编码的字符串为文件
        try {
            // 从base64字符串中提取图片类型
            String type = base64.substring(base64.indexOf("/", 1) + 1, base64.indexOf(";", 1));

            // 去掉base64字符串的前缀，如"data:image/jpeg;base64,"
            base64 = base64.substring(base64.indexOf(",", 1) + 1);

            // 使用Base64解码器解密字符串
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] decode = decoder.decode(base64);

            // 确保解密后的byte数组中的负值转换为对应的正值
            for (int i = 0; i < decode.length; ++i) {
                if (decode[i] < 0) {
                    decode[i] += 256;
                }
            }

            // 拼接文件的完整路径，包括随机UUID作为文件名和原始扩展名
            String fileName = DataUtils.getCode() + "." + type;

            // 将解密的数据写入文件
            String totalPath = path + File.separator + fileName;
            OutputStream out = new FileOutputStream(totalPath);
            out.write(decode);
            out.flush();
            out.close();

            // 使用Tika库解析文件的实际后缀名
            // Tika tika = new Tika();
            File file = new File(totalPath);
            // String mimeType = tika.detect(file);
            // String extension = mimeType.split("/")[1];

            // 打印不同类型识别结果进行比对
            System.out.println(type + "base64 的");
            // System.out.println(extension + "tika 的");

            // 检查文件扩展名是否一致，不一致则尝试修改文件扩展名
            // if (!extension.equals(type)) {
            //     File newFile = new File(totalPath.replace(type, extension));
            //     if (file.renameTo(newFile)) {
            //         System.out.println("修改成功");
            //     } else {
            //         System.out.println("修改失败");
            //     }
            // }

            // 返回生成文件的文件名
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
