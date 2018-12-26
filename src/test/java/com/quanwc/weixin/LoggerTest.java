package com.quanwc.weixin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author quanwenchao
 * @date 2018/12/9 22:58:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggerTest {

	Logger logger = LoggerFactory.getLogger(Object.class);

	@Test
	public void test1() {

	logger.debug("debug...");
	logger.info("info...");
	logger.error("error...");

	}

}
