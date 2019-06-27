package demo.repository;

import demo.bean.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fun
 * @date 2019/6/11
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
}
