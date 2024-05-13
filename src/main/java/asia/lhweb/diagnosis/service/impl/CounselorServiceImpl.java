package asia.lhweb.diagnosis.service.impl;


import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.mapper.CounselorMapper;
import asia.lhweb.diagnosis.mapper.SysAdminCounselorMapper;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.Counselor;
import asia.lhweb.diagnosis.model.dto.CounselorDTO;
import asia.lhweb.diagnosis.model.vo.CounselorAppointmentStatisticsVO;
import asia.lhweb.diagnosis.service.CounselorService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
* @author Administrator
* @description 针对表【counselor(咨询师表)】的数据库操作Service实现
* @createDate 2024-04-28 20:11:50
*/
@Service
public class CounselorServiceImpl
implements CounselorService{
    @Resource
    private CounselorMapper counselorMapper;
    @Resource
    private SysAdminCounselorMapper sysAdminCounselorMapper;

    /**
     * 页面
     *
     * @param counselorDTO 辅导员dto
     * @return {@link PageResult}<{@link Counselor}>
     */
    @Override
    public PageResult<Counselor> page(CounselorDTO counselorDTO) {
        int pageNo = counselorDTO.getPageNo();
        int pageSize = counselorDTO.getPageSize();
        PageHelper.startPage(pageNo, pageSize);
        // 紧跟着的第一个select方法会被分页
        Page<Counselor> adminPage = counselorMapper.selectAllIf(counselorDTO);
        if (adminPage == null || adminPage.getResult().size() == 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        PageResult<Counselor> pageResult = new PageResult<>();
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalRow((int) adminPage.getTotal());
        pageResult.setItems(adminPage.getResult());
        pageResult.setPageTotalCount(adminPage.getPages());
        return pageResult;
    }

    /**
     * 按id删除
     *
     * @param id id
     * @return boolean
     */
    @Override
    public boolean deleteById(int id) {
        return counselorMapper.deleteByPrimaryKey((long) id) > 0;
    }

    /**
     * 获取指定时间预约量来源于哪些咨询师
     *
     * @param begin 开始
     * @param end   结束
     * @return {@link CounselorAppointmentStatisticsVO}
     */
    @Override
    public CounselorAppointmentStatisticsVO getCountCAStatistics(LocalDate begin, LocalDate end) {
        return null;
    }

    /**
     * 按区域id获取辅导员名单
     *
     * @param areaId 区域id
     * @return {@link BaseResponse}<{@link List}<{@link Counselor}>>
     */
    @Override
    public BaseResponse<List<Counselor>> getCounselorListByAreaId(Integer areaId) {
        List<Counselor> sysAdminCounselorList = counselorMapper.getCounselorListByAreaId(areaId);
        if (sysAdminCounselorList.isEmpty()){
            throw new BusinessException(ErrorCode.NULL_ERROR,"该领域下暂无咨询师");
        }
        return ResultUtils.success(sysAdminCounselorList);
    }
}
