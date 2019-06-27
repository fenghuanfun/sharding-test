package demo.repository;


import demo.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fun
 * @date 2019/6/18
 */
public interface UserRepository extends JpaRepository<User, String> {
}
