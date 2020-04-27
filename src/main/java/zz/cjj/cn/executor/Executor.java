package zz.cjj.cn.executor;

import java.util.List;

public interface Executor {

    public <T> List<T> query(Class<T> clazz, String statement, Object... objects);
}
