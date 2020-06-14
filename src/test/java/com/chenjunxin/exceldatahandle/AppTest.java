package com.chenjunxin.exceldatahandle;

import com.chenjunxin.exceldatahandle.controller.LogController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest
/*
 * @ActiveProfiles，可以指定一个或者多个 profile，
 * 这样我们的测试类就仅仅加载这些名字的 profile 中定义的 bean 实例。
 * 这里激活application-prod.properties配置文件.
 */
@ActiveProfiles("dev-linux")
public class AppTest {
    private static final Logger log = LoggerFactory.getLogger(LogController.class);

    @Test
    public void testLog() {
        log.trace("trace");
        log.debug("debug");
        log.warn("warn");
        log.info("info");
        log.error("error");
    }
}
