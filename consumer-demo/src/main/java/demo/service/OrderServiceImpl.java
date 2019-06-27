package demo.service;

import demo.bean.Order;
import demo.bean.User;
import demo.bean.UserItem;
import demo.repository.OrderItemRepository;
import demo.repository.OrderRepository;
import demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author fun
 * @date 2019/6/12
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserItem testCircleSer() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        UserItem userItem1 = new UserItem();
        UserItem userItem2 = new UserItem();
        UserItem userItem3 = new UserItem();

        userItem1.setId(UUID.randomUUID().toString());
        userItem2.setId(UUID.randomUUID().toString());
        userItem3.setId(UUID.randomUUID().toString());
        userItem1.setUser(user);
        userItem2.setUser(user);
        userItem3.setUser(user);
        user.setUserItems(Arrays.asList(userItem1, userItem2, userItem3));
        return userItem1;
    }

    //    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void test() {
        List<Order> orders = orderRepository.findByCreateTimeBetween(Timestamp.valueOf(LocalDateTime.now().minusMonths(3)), Timestamp.valueOf(LocalDateTime.now()));
        System.out.println("fetch: " + orders);
    }

}
