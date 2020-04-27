package zz.cjj.cn;

import zz.cjj.cn.domain.mapper.UserMapper;
import zz.cjj.cn.domain.pojo.User;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        MySession mySession = new MySession();
        UserMapper userMapper = mySession.getMapper(UserMapper.class);
        List<User> u = userMapper.select("123");
        for(User user : u) {
            System.out.println(user.getId() + "_" + user.getAge() +"_"+ user.getTime());
        }
    }
}
