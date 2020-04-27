package zz.cjj.cn.config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyConfiguration {

    private static ClassLoader loader = ClassLoader.getSystemClassLoader();

    public Connection getConnection(String path) {

        DBConfig dbConfig = readDBConfig(path);

        try {
            Class.forName(dbConfig.getDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        try {
            //建立数据库链接
            connection = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUsername(), dbConfig.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public DBConfig readDBConfig(String path) {
        DBConfig dbConfig = new DBConfig();

        InputStream in = loader.getResourceAsStream(path);
        SAXReader reader = new SAXReader();

        Document document = null;
        try {
            document = reader.read(in);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        Element root = document.getRootElement();
        List<Element> properties = root.elements("property");
        for(Element e : properties) {
            String value = getValue(e);
            String name = e.attributeValue("name").trim();
                if("url".equals(name)) {
                    dbConfig.setUrl(value);
                }
                if("username".equals(name)) {
                    dbConfig.setUsername(value);
                }
                if("password".equals(name)) {
                    dbConfig.setPassword(value);
                }

                if("driverClassName".equals(name)) {
                    dbConfig.setDriver(value);
                }
            }
        return dbConfig;
    }

    private String getValue(Element e) {
        return e.hasContent() ? e.getText().trim() : e.attributeValue("value").trim();
    }

    public MapperBean readMapper(String path) {

        MapperBean mapperBean = new MapperBean();

        InputStream in = loader.getResourceAsStream(path);
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(in);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();
        String interfaceName = root.attributeValue("nameSpace").trim();
        mapperBean.setInterfaceName(interfaceName);

        List<Function> list = new ArrayList<Function>();
        mapperBean.setList(list);

        Iterator<Element> itr = root.elementIterator();
        while (itr.hasNext()) {
            Element e = itr.next();
            String name = e.getName().trim();
            String sql = e.getText().trim();
            String funcName = e.attributeValue("id").trim();
            String resultType = e.attributeValue("resultType").trim();

            Object instance = null;

            try {
                instance = Class.forName(resultType).newInstance();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (InstantiationException e3) {
                e3.printStackTrace();
            }

            Function function = new Function();
            function.setSqlType(name);
            function.setSql(sql);
            function.setFuncName(funcName);
            function.setResultType(instance);
            list.add(function);
        }
        return mapperBean;
    }
}
