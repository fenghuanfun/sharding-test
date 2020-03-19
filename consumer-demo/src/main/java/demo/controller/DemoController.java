package demo.controller;

import demo.service.OrderService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fun
 * @date 2019/6/20
 */
@RestController
public class DemoController {

    @Autowired
    private OrderService orderService;

    public void test() {
        orderService.testPrecise();
        orderService.testRange();
        orderService.testStringPreciseTime();
        orderService.testStringRangeTime();
//        orderService.createTable();
    }

    @RequestMapping("/testRange")
    public void testRange() {
        orderService.testRange();
    }

    @RequestMapping("/testPrecise")
    public void testPrecise() {
        orderService.testPrecise();
    }

    @RequestMapping("/testStringPreciseTime")
    public void testStringTime() {
        orderService.testStringPreciseTime();
    }

    @RequestMapping("/testStringRangeTime")
    public void testStringRangeTime() {
        orderService.testStringRangeTime();
    }

    @RequestMapping("/testHaving")
    public void testHaving() {
        orderService.testHaving();
    }

    @RequestMapping("/testDistinct")
    public void testDistinct() {
        orderService.testDistinct();
    }

    @SneakyThrows
    @RequestMapping("/createTable")
    public void createTable() {
        orderService.createTable();
    }
}
