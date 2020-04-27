package zz.cjj.cn.config;

import java.util.ArrayList;
import java.util.List;

public class MapperBean {

    private String interfaceName;
    private List<Function> list = new ArrayList<Function>();

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public List<Function> getList() {
        return list;
    }

    public void setList(List<Function> list) {
        this.list = list;
    }
}
