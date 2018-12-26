package com.quanwc.weixin.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.quanwc.weixin.enumerator.ProductStatusEnum;
import com.quanwc.weixin.model.entity.ProductInfo;

/**
 * @author quanwenchao
 * @date 2018/12/15 10:41:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

	@Autowired
	private ProductInfoServiceImpl productInfoService;

	@Test
	public void findOne() {
		ProductInfo productInfo = productInfoService.findOne("123456");
		Assert.assertEquals("123456", productInfo.getProductId());
	}

	@Test
	public void findUpAll() {
		List<ProductInfo> upAll = productInfoService.findUpAll();
		Assert.assertNotEquals(0, upAll.size());
	}

	@Test
	public void findAll() {
		PageRequest request = new PageRequest(0, 2);
		Page<ProductInfo> productInfoPage = productInfoService.findAll(request);
		//System.out.println(productInfoPage.getTotalElements());
		Assert.assertNotEquals(0, productInfoPage.getTotalElements());
	}

	@Test
	public void save() {
		ProductInfo productInfo = new ProductInfo();
		productInfo.setProductId("1234567");
		productInfo.setProductName("皮皮虾");
		productInfo.setProductPrice(BigDecimal.valueOf(10.3));
		productInfo.setProductStock(300);
		productInfo.setProductDescription("蒜蓉味的哦");
		productInfo.setProductIcon("http://www.baidu.com/");
		productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
		productInfo.setCategoryType(3);

		productInfoService.save(productInfo);
	}
}