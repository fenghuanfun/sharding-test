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
@Table(name = "t_order_item")
public class OrderItem extends BaseEntity {

    private String orderId;

    private String name;

    private Double price;

    private long testId;

}
