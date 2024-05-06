package asia.lhweb.diagnosis.controller.common;

import asia.lhweb.diagnosis.constant.BaseConstant;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/common")
public class CommonController {
    @Resource
    private DefaultKaptcha defaultKaptcha;

    @GetMapping(value = "/getCode", produces = "image/jpeg")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        // 定义response输出类型为image/jpeg类型
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");

        //-------------------生成验证码 begin --------------------------
        // 获取验证码文本内容
        String text = defaultKaptcha.createText();
        System.err.println("验证码内容：" + text);
        // 将验证码文本内容放入session
        HttpSession session = request.getSession();
        session.setAttribute(BaseConstant.CODE_KEY, text);
        // System.out.println("生成验证码的sessionId:" + session.getId());
        // 创建验证码图像
        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            // 输出流输出图片，格式为jpg
            ImageIO.write(image, "jpg", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // /**
    //  * 文件上传操作
    //  *
    //  * @param file 文件
    //  * @return {@link Result}<{@link String}>
    //  */
    // @PostMapping("/upload")
    // public Result<String> update(MultipartFile file) {
    //     try {
    //         // 获取原来的文件名
    //         String originalFilename = file.getOriginalFilename();
    //         // 截取扩展名后缀
    //         String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
    //         String objectName = UUID.randomUUID() + extension;
    //
    //         // 文件请求路径
    //         String filePath = aliOssUtil.upload(file.getBytes(), objectName);
    //         return Result.success(filePath);
    //     } catch (IOException e) {
    //
    //     }
    //     return Result.error("上传失败");
    // }

}