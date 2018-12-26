package com.quanwc.weixin.repository;

import com.quanwc.weixin.model.entity.OrderDetailDO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author quanwenchao
 * @date 2018/12/25 10:42:02
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

	@Autowired
	private OrderDetailRepository orderDetailRepository;


	@Test
	public void save() {
		OrderDetailDO orderDetail = new OrderDetailDO();
		orderDetail.setDetailId("www");
		orderDetail.setOrderId("12345678910");
		orderDetail.setProductIcon("https://www.baidu.com/");
		orderDetail.setProductId("333");
		orderDetail.setProductName("皮蛋粥");
		orderDetail.setProductPrice(new BigDecimal(11.04));
		orderDetail.setProductQuantity(31);

		OrderDetailDO result = orderDetailRepository.save(orderDetail);
		Assert.assertNotNull(result);
	}

	@Test
	public void findByOrderId() {
		List<OrderDetailDO> result = orderDetailRepository.findByOrderId("12345678910");
		assertNotEquals(0, result.size());
	}
}