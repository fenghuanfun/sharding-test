package demo.controller;

import demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @author fun
 * @date 2019/6/20
 */
@RestController
public class DemoController {

    @Autowired
    private OrderService orderService;

    @PostConstruct
    public void test() {
        orderService.test();
    }

}
