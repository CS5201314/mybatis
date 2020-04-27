package zz.cjj.cn.proxy;

import zz.cjj.cn.config.Function;
import zz.cjj.cn.config.MapperBean;
import zz.cjj.cn.config.MyConfiguration;
import zz.cjj.cn.executor.Executor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * desc:
 * Created by zhaoxg at 2020-04-27 9:49
 */
public class MyMapperProxy implements InvocationHandler {

    private Class clazz;
    private Executor executor;

    public MyMapperProxy(Class clazz, Executor executor) {
        this.clazz = clazz;
        this.executor = executor;
    }

    public Object invoke(Object proxy, final Method method, Object[] args) throws Throwable {
        MyConfiguration myConfiguration = new MyConfiguration();
        MapperBean mapperBean = myConfiguration.readMapper("./mybatis/usermapper.xml");

        for(Function function : mapperBean.getList()) {
            if(method.getName().equals(function.getFuncName())) {
                String sql = function.getSql();
                String type = function.getSqlType();
                Object resultType = function.getResultType();

                return executor.query(resultType.getClass(),sql);
            }
        }

        return null;
    }
}
