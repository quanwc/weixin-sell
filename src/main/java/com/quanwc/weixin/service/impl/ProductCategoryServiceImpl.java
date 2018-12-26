package com.quanwc.weixin.service.impl;

import com.quanwc.weixin.model.entity.ProductCategoryDO;
import com.quanwc.weixin.repository.ProductCategoryRepository;
import com.quanwc.weixin.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类目service实现类
 * @author quanwenchao
 * @date 2018/12/14 22:50:55
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{

	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Override
	public ProductCategoryDO findOne(Integer categoryId) {
		return productCategoryRepository.findOne(categoryId);
	}

	@Override
	public List<ProductCategoryDO> findAll() {
		return productCategoryRepository.findAll();
	}

	@Override
	public List<ProductCategoryDO> findByCategoryTypeIn(List<Integer> categoryTypeList) {
		return productCategoryRepository.findByCategoryTypeIn(categoryTypeList);
	}

	@Override
	public ProductCategoryDO save(ProductCategoryDO productCategory) {
		return productCategoryRepository.save(productCategory);
	}

}
