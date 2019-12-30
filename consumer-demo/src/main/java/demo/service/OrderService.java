package demo.service;


import demo.bean.UserItem;

import java.sql.SQLException;

/**
 * @author fun
 * @date 2019/6/12
 */
public interface OrderService {

    UserItem testCircleSer();

    void testRange();

    void testPrecise();

    void testStringPreciseTime();

    void testStringRangeTime();

    void testHaving();

    void createTable() throws SQLException;
}
