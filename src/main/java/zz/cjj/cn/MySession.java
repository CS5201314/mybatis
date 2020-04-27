package zz.cjj.cn;

import zz.cjj.cn.executor.Executor;
import zz.cjj.cn.executor.MyExecutor;
import zz.cjj.cn.proxy.MyMapperProxy;

import java.lang.reflect.Proxy;
import java.util.List;

public class MySession {

    private Executor executor = new MyExecutor();

    public <T> List<T> select(Class<T> clazz, String statement, Object... parameters) {
        return executor.query(clazz,statement,parameters);
    }

    public <T> T getMapper(Class<T> clazz) {
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},new MyMapperProxy(clazz,executor));
    }
}
