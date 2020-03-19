package demo.bean;

import demo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * @author fun
 * @date 2019/6/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "t_user")
public class User extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<UserItem> userItems;

}
