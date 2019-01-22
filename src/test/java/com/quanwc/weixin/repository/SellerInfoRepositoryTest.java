package com.quanwc.weixin.repository;

import com.quanwc.weixin.model.entity.SellerInfo;
import com.quanwc.weixin.util.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author quanwenchao
 * @date 2019/1/19 14:18:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

	@Autowired
	private SellerInfoRepository sellerInfoRepository;

	@Test
	public void save() {
		SellerInfo sellerInfo = new SellerInfo();
		sellerInfo.setSellerId(KeyUtil.generateUniqueKey());
		sellerInfo.setUsername("admin");
		sellerInfo.setPassword("admin");
		sellerInfo.setOpenid("wx_abc");

		SellerInfo result = sellerInfoRepository.save(sellerInfo);
		Assert.assertNotNull(result);
	}

	@Test
	public void findByOpenid() {
		SellerInfo sellerInfo = sellerInfoRepository.findByOpenid("wx_abc");
		Assert.assertNotNull(sellerInfo.getSellerId());
	}
}