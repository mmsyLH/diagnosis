package asia.lhweb.diagnosis.model.vo;

import asia.lhweb.diagnosis.model.domain.Area;
import asia.lhweb.diagnosis.model.domain.Comment;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 咨询师表
 * @TableName counselor
 */
@Data
public class CounselorVO implements Serializable {
    /**
     * 咨询师表id
     */
    private Integer id;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 余额
     */
    private Double price;
    /**
     * 咨询师姓名
     */
    private String counselorName;

    /**
     * 管理员id
     */
    private Integer adminId;

    /**
     * 毕业院校
     */
    private String school;

    /**
     * 学历
     */
    private String educationlv;

    /**
     * 个人简介
     */
    private String introduction;

    /**
     * 专业背景
     */
    private String background;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 区域列表
     */
    private List<Area> areaList;
    // private List<Integer> areaIdList;

    /**
     * 评论列表
     */
    private List<Comment> commentList;

    private static final long serialVersionUID = 1L;
}