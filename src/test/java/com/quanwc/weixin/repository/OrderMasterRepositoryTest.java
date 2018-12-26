package com.quanwc.weixin.repository;

import com.quanwc.weixin.model.entity.OrderMasterDO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author quanwenchao
 * @date 2018/12/25 10:06:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

	@Autowired
	private OrderMasterRepository orderMasterRepository;

	private final String WX_OPENID = "wx_110110";

	/**
	 * save()
	 */
	@Test
	public void saveTest() {
		OrderMasterDO orderMaster = new OrderMasterDO();
		orderMaster.setOrderId("123457");
		orderMaster.setBuyerName("廖师兄");
		orderMaster.setBuyerPhone("12345678910");
		orderMaster.setBuyerAddress("慕课网2");
		orderMaster.setBuyerOpenid(WX_OPENID);
		orderMaster.setOrderAmount(new BigDecimal(10.30));
		orderMaster.setCreateTimestamp(new Date());
		orderMaster.setUpdateTimestamp(new Date());

		OrderMasterDO result = orderMasterRepository.save(orderMaster);
		Assert.assertNotNull(result);
	}


	@Test
	public void findByBuyerOpenid() {

		// 分页参数对象：page：获取第0页，size：每页1条
		PageRequest pageRequest = new PageRequest(1, 1);

		Page<OrderMasterDO> result = orderMasterRepository.findByBuyerOpenid(WX_OPENID, pageRequest);
		Assert.assertNotEquals(0, result.getTotalElements());
	}
}