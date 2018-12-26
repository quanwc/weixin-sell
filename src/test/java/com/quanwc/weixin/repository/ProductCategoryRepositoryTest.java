package com.quanwc.weixin.repository;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.quanwc.weixin.model.entity.ProductCategoryDO;

/**
 * @author quanwenchao
 * @date 2018/12/10 23:33:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	/**
	 * findOne()
	 */
	@Test
	public void findOne() {
		ProductCategoryDO productCategory = productCategoryRepository.findOne(1);
		System.out.println(productCategory);
	}

	/**
	 * save()
	 */
	@Test
	@Transactional
	public void save() {
		ProductCategoryDO productCategory = new ProductCategoryDO("boy love1", 22);
		//productCategory.setCreateTimestamp(new Date());
		//productCategory.setUpdateTimestamp(new Date());
		ProductCategoryDO result = productCategoryRepository.save(productCategory);
		Assert.assertNotNull(result);
		//Assert.assertNotEquals(null, result);
	}

	/**
	 * update():
	 * 其实还是调用save()方法，只不过需要先设置主键id
	 */
	@Test
	public void update() {
		ProductCategoryDO productCategory = productCategoryRepository.findOne(1);
		productCategory.setCategoryType(111);
		productCategoryRepository.save(productCategory);
	}

	@Test
	public void findByCategoryTypeIn(){
		List<Integer> list = Arrays.asList(3, 4, 22);
		List<ProductCategoryDO> result = productCategoryRepository.findByCategoryTypeIn(list);
		Assert.assertNotEquals(0, result.size());
	}



}