package zz.cjj.cn.executor;

import zz.cjj.cn.config.MyConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyExecutor implements Executor{

    MyConfiguration myConfiguration = new MyConfiguration();

    public <T> List<T> query(Class<T> clazz,String statement, Object... objects) {
        Connection con = myConfiguration.getConnection("./mybatis/mybatisconfig.xml");
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(statement);
            for(int i = 0; i < objects.length; i++) {
                Object object = objects[i];
                if(object instanceof String) {
                    ps.setString(i,(String)object);
                }
            }
            rs = ps.executeQuery();
            return (List<T>)doQuery(clazz,rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
                con.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private List<Object> doQuery(Class clazz, ResultSet rs) {
        List<Object> list = new ArrayList<Object>();
        try {
            while(rs.next()) {
                list.add(doRow(clazz,rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Object doRow(Class clazz, ResultSet rs) {
        Object object = null;
        try {
            object = clazz.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        }

        try {
            Method[] methods = clazz.getMethods();
            for (Method m : methods) {
                String name = m.getName();
                Class<?>[] types = m.getParameterTypes();
                String param = name.replace("set", "");
                if (name.startsWith("set") && types.length == 1) {
                    Class type = types[0];
                    if (type == Integer.class || "int".equals(type.getName())) {
                        m.invoke(object, rs.getInt(param));
                    }
                    if (type == String.class) {
                        m.invoke(object, rs.getString(param));
                    }
                    if(type == Timestamp.class) {
                        m.invoke(object, rs.getTimestamp(param));
                    }
                }
            }
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
        } catch (IllegalAccessException e4) {
            e4.printStackTrace();
        } catch (SQLException e5) {
            e5.printStackTrace();
        }

        return object;
    }
}
