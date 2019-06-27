package demo.repository;

import demo.bean.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author fun
 * @date 2019/6/11
 */
public interface OrderRepository extends JpaRepository<Order, String> {

    /**
     * test
     *
     * @param start
     * @param end
     * @return
     */
    List<Order> findByCreateTimeBetween(Timestamp start, Timestamp end);

}
