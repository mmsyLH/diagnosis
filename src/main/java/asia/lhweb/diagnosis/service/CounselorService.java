package asia.lhweb.diagnosis.service;


import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.Counselor;
import asia.lhweb.diagnosis.model.dto.CounselorDTO;

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
}
