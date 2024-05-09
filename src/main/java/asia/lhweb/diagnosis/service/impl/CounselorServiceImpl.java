package asia.lhweb.diagnosis.service.impl;


import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.mapper.CounselorMapper;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.Counselor;
import asia.lhweb.diagnosis.model.dto.CounselorDTO;
import asia.lhweb.diagnosis.service.CounselorService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
