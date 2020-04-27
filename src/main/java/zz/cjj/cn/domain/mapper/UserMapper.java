package zz.cjj.cn.domain.mapper;

import zz.cjj.cn.domain.pojo.User;
import java.util.List;

public interface UserMapper {

    List<User> select(String id);

}
