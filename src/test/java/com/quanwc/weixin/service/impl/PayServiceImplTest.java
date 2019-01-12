package com.quanwc.weixin.service.impl;

import com.quanwc.weixin.model.dto.OrderMasterDTO;
import com.quanwc.weixin.service.OrderMasterService;
import com.quanwc.weixin.service.PayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

/**
 * @author quanwenchao
 * @date 2019/1/9 9:01:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

	@Autowired
	private PayService payService;
	@Autowired
	private OrderMasterService orderMasterService;

	@Test
	public void create() {
		OrderMasterDTO orderMasterDTO = orderMasterService.findOne("1545730283483747433");
		payService.create(orderMasterDTO);
	}
}