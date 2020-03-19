package demo.service;

import com.alibaba.fastjson.JSONObject;
import demo.bean.User;
import demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fun
 * @date 2020/1/6
 */
@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    public void test() {
        User user = new User();
        user.setName("hello");
        User save = userRepository.save(user);
        System.out.println(JSONObject.toJSONString(save));

        save.setName("olleh");
        User save1 = userRepository.save(save);
        System.out.println(JSONObject.toJSONString(save1));
    }

}
