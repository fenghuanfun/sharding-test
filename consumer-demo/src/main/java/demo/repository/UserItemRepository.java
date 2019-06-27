package demo.repository;

import demo.bean.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fun
 * @date 2019/6/18
 */
public interface UserItemRepository extends JpaRepository<UserItem, String> {
}
