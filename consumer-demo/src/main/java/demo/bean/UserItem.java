package demo.bean;

import demo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author fun
 * @date 2019/6/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "t_user_item")
public class UserItem extends BaseEntity {

    @ManyToOne
    private User user;

}
