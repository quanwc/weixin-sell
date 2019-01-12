package com.quanwc.weixin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.quanwc.weixin.enumerator.OrderStatusEnum;
import com.quanwc.weixin.enumerator.PayStatusEnum;
import com.quanwc.weixin.model.dto.OrderMasterDTO;
import com.quanwc.weixin.model.entity.OrderDetailDO;

import lombok.extern.slf4j.Slf4j;

/**
 * @author quanwenchao
 * @date 2018/12/25 16:22:01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMaterServiceImplTest {

	@Autowired
	private OrderMaterServiceImpl orderMaterService;

	private final String WX_OPENID = "wx_201812251624";

	private final String ORDER_ID = "1545730283483747433";

	@Test
	public void create() {

		OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
		orderMasterDTO.setBuyerName("活动活动的666");
		orderMasterDTO.setBuyerAddress("南山区");
		orderMasterDTO.setBuyerPhone("110");
		orderMasterDTO.setBuyerOpenid(WX_OPENID);

		// 购物车
		List<OrderDetailDO> orderDetailDOList = new ArrayList<>();
		OrderDetailDO orderDetail1 = new OrderDetailDO();
		orderDetail1.setProductId("12345678");
		orderDetail1.setProductQuantity(1);
		orderDetailDOList.add(orderDetail1);

		OrderDetailDO orderDetail2 = new OrderDetailDO();
		orderDetail2.setProductId("1234567");
		orderDetail2.setProductQuantity(2);
		orderDetailDOList.add(orderDetail2);

		orderMasterDTO.setOrderDetailDOList(orderDetailDOList);

		OrderMasterDTO result = orderMaterService.create(orderMasterDTO);

		log.info("[创建订单] result={}", result);
	}

	@Test
	public void findOne() {
		OrderMasterDTO result = orderMaterService.findOne(ORDER_ID);
		log.info("[查询单个订单] result={} ", result);
		Assert.assertEquals(ORDER_ID, result.getOrderId());
	}

	@Test
	public void findList() {
		PageRequest pageRequest = new PageRequest(0, 2);
		Page<OrderMasterDTO> masterDTOPage = orderMaterService.findList(WX_OPENID,
				pageRequest);
		Assert.assertNotEquals(0, masterDTOPage.getTotalElements());
	}

	@Test
	public void cancel() {
		OrderMasterDTO orderMasterDTO = orderMaterService.findOne(ORDER_ID);
		OrderMasterDTO result = orderMaterService.cancel(orderMasterDTO);
		Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
	}

	@Test
	public void finish() {
		OrderMasterDTO orderMasterDTO = orderMaterService.findOne(ORDER_ID);
		OrderMasterDTO result = orderMaterService.finish(orderMasterDTO);
		Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());
	}

	@Test
	public void paid() {
		OrderMasterDTO orderMasterDTO = orderMaterService.findOne(ORDER_ID);
		OrderMasterDTO result = orderMaterService.paid(orderMasterDTO);
		Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());
	}

	@Test
	public void list() {
		PageRequest pageRequest = new PageRequest(0, 2);
		Page<OrderMasterDTO> masterDTOPage = orderMaterService.findList(pageRequest);
		Assert.assertTrue("查询订单列表", masterDTOPage.getTotalElements() > 0);
	}
}