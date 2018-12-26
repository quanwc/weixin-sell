package com.quanwc.weixin.repository;

import com.quanwc.weixin.model.entity.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author quanwenchao
 * @date 2018/12/15 9:52:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

	@Autowired
	private ProductInfoRepository productInfoRepository;

	/**
	 * save
	 */
	@Test
	public void save() {
		ProductInfo productInfo = new ProductInfo();
		productInfo.setProductId("123456");
		productInfo.setProductName("小米粥");
		productInfo.setProductPrice(BigDecimal.valueOf(10.3));
		productInfo.setProductStock(300);
		productInfo.setProductDescription("很好吃哦");
		productInfo.setProductIcon("http://www.baidu.com/");
		productInfo.setProductStatus(1);
		productInfo.setCategoryType(3);

		productInfoRepository.save(productInfo);
	}

	/**
	 * findByProductStatus
	 */
	@Test
	public void findByProductStatus() {
		List<ProductInfo> productInfoList = productInfoRepository.findByProductStatus(1);
		Assert.assertNotEquals(0, productInfoList.size());
	}
}