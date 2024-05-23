package asia.lhweb.diagnosis.service;


import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.Counselor;
import asia.lhweb.diagnosis.model.dto.CounselorDTO;
import asia.lhweb.diagnosis.model.vo.CounselorAppointmentStatisticsVO;
import asia.lhweb.diagnosis.model.vo.CounselorVO;

import java.time.LocalDate;
import java.util.List;

/**
* @author Administrator
* @description 针对表【counselor(咨询师表)】的数据库操作Service
* @createDate 2024-04-28 20:11:50
*/
public interface CounselorService {

    /**
     * 页面
     *
     * @param counselorDTO 辅导员dto
     * @return {@link PageResult}<{@link Counselor}>
     */
    PageResult<Counselor> page(CounselorDTO counselorDTO);

    /**
     * 按id删除
     *
     * @param id id
     * @return boolean
     */
    boolean deleteById(int id);

    /**
     * 获取指定时间预约量来源于哪些咨询师
     *
     * @param begin 开始
     * @param end   结束
     * @return {@link CounselorAppointmentStatisticsVO}
     */
    CounselorAppointmentStatisticsVO getCountCAStatistics(LocalDate begin, LocalDate end);

    /**
     * 按区域id获取辅导员名单
     *
     * @param areaId 区域id
     * @return {@link BaseResponse}<{@link List}<{@link Counselor}>>
     */
    BaseResponse<List<CounselorVO>> getCounselorListByAreaId(Integer areaId);

    /**
     * 通过admin id获取辅导员
     *
     * @param adminId 管理员id
     * @return {@link Counselor}
     */
    Counselor getCounselorByAdminId(Integer adminId);

    /**
     * 按id获取信息
     *
     * @param id id
     * @return {@link CounselorVO}
     */
    CounselorVO getInfoById(Integer id);
}
