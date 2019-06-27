package demo.bean;

import demo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author fun
 * @date 2019/6/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "t_order")
public class Order extends BaseEntity {

    private String name;

    private long testId;

}
