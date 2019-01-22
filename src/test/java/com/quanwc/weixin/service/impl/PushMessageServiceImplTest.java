package com.quanwc.weixin.service.impl;

import com.quanwc.weixin.model.dto.OrderMasterDTO;
import com.quanwc.weixin.service.OrderMasterService;
import com.quanwc.weixin.service.PushMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author quanwenchao
 * @date 2019/1/21 22:10:54
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {

	@Autowired
	private PushMessageService pushMessageService;
	@Autowired
	private OrderMasterService orderMasterService;

	@Test
	public void orderStatusMessage() {
		OrderMasterDTO orderMasterDTO = orderMasterService.findOne("1547276188010679504");
		pushMessageService.orderStatusMessage(orderMasterDTO);
	}
}