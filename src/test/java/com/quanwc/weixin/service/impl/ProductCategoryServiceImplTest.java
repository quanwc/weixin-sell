package com.quanwc.weixin.service.impl;

import com.quanwc.weixin.model.entity.ProductCategoryDO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author quanwenchao
 * @date 2018/12/14 22:58:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

	@Autowired
	private ProductCategoryServiceImpl productCategoryService;

	@Test
	public void findOne() {
		ProductCategoryDO productCategory = productCategoryService.findOne(1);
		Assert.assertEquals(new Integer(1), productCategory.getCategoryId());
	}

	// 快速换行：ctrl + shift + 回车

	@Test
	public void findAll() {
		List<ProductCategoryDO> productCategoryDOList = productCategoryService.findAll();
		Assert.assertNotEquals(0, productCategoryDOList.size());
	}

	@Test
	public void findByCategoryTypeIn() {
		List<ProductCategoryDO> productCategoryDOList = productCategoryService.findByCategoryTypeIn(Arrays.asList(1, 2, 2, 3));
		Assert.assertNotEquals(0, productCategoryDOList.size());
	}

	@Test
	public void save() {
		ProductCategoryDO productCategory = new ProductCategoryDO("boy private only", 4);
		ProductCategoryDO category = productCategoryService.save(productCategory);
		Assert.assertNotNull(category);
	}
}