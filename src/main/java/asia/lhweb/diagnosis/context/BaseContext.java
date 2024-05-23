package asia.lhweb.diagnosis.context;

import asia.lhweb.diagnosis.model.domain.SysAdmin;
import lombok.Data;

@Data
public class BaseContext {

    public static ThreadLocal<SysAdmin> threadLocal = new ThreadLocal<>();

    public static void setCurrentAdmin(SysAdmin sysAdmin) {
        threadLocal.set(sysAdmin);
    }
    public static SysAdmin getCurrentAdmin() {
        return threadLocal.get();
    }
    public static void removeCurrentAdmin() {
        threadLocal.remove();
    }
}
