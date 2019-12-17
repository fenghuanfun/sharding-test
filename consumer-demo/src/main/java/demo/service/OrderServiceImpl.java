package demo.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.JPQLQueryFactory;
import demo.bean.Order;
import demo.bean.QOrder;
import demo.bean.User;
import demo.bean.UserItem;
import demo.repository.OrderItemRepository;
import demo.repository.OrderRepository;
import demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
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
    @Autowired
    private JPQLQueryFactory queryFactory;
    private QOrder qOrder = QOrder.order;
    private StringTemplate dateTemplate = Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m-%d')", qOrder.createTime);

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

    private LocalDateTime dateTime = LocalDate.now().atStartOfDay();

    private List<Timestamp> timestamps = Arrays.asList(
            Timestamp.valueOf(dateTime),
            Timestamp.valueOf(dateTime.minusMonths(1)),
            Timestamp.valueOf(dateTime.plusMonths(1)),
            Timestamp.valueOf(dateTime.plusMonths(2)));
    private List<String> times = Arrays.asList("2019-11-17", "2019-12-17", "2020-01-17", "2020-02-17");

    //    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void testRange() {
        List<Order> orders = orderRepository.findByCreateTimeBetween(Timestamp.valueOf(dateTime.minusMonths(1)), Timestamp.valueOf(dateTime.plusMonths(2)));
        System.out.println("fetch: " + JSONObject.toJSONString(orders, SerializerFeature.PrettyFormat));
    }

    @Override
    public void testPrecise() {
        List<Order> orders = orderRepository.findByCreateTime(Timestamp.valueOf(dateTime));
        System.out.println("fetch-orders: " + JSONObject.toJSONString(orders, SerializerFeature.PrettyFormat));
        List<Order> inOrders = orderRepository.findByCreateTimeIn(timestamps);
        System.out.println("fetch-inOrders: " + JSONObject.toJSONString(inOrders, SerializerFeature.PrettyFormat));
    }

    @Override
    public void testStringPreciseTime() {
        List<Order> eqRes = queryFactory.selectFrom(qOrder)
                .where(
                        dateTemplate.eq("2019-11-17")
                )
                .fetch();
        System.out.println("Eq: " + JSONObject.toJSONString(eqRes, SerializerFeature.PrettyFormat));

        List<Order> inRes = queryFactory.selectFrom(qOrder)
                .where(
                        dateTemplate.in(Arrays.asList("2019-11-17", "2020-02-17"))
                )
                .fetch();
        System.out.println("In: " + JSONObject.toJSONString(inRes, SerializerFeature.PrettyFormat));
    }

    @Override
    public void testStringRangeTime() {
        List<Order> betweenRes = queryFactory.selectFrom(qOrder)
                .where(
                        dateTemplate.between("2019-11-01", "2020-02-30")
                )
                .fetch();
        System.out.println(JSONObject.toJSONString(betweenRes, SerializerFeature.PrettyFormat));
    }

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private DataSource dataSource;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void createTable() throws SQLException {
        Connection connection = dataSource.getConnection();
        String sql0 = "create table if not exists t_order_temp like t_order";
        PreparedStatement preparedStatement = connection.prepareStatement(sql0);
        boolean execute = preparedStatement.execute();
//        int i0 = entityManager.createNativeQuery(sql0).executeUpdate();
        System.out.println(execute);

//        String sql = "create table if not exists t_order like t_order_temp";
//        int i = entityManager.createNativeQuery(sql).executeUpdate();
//        System.out.println(i);
//        List<String> dropSql = new ArrayList<>();
//        dropSql.add("drop table t_order_temp");
//        dropSql.add("drop table t_order");
//        System.out.println(dropSql);
    }

}
