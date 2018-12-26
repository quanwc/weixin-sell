package com.quanwc.weixin.service;

import com.quanwc.weixin.model.entity.ProductCategoryDO;

import java.util.List;

/**
 * 类目service接口层
 * @author quanwenchao
 * @date 2018/12/13 23:42:18
 */
public interface ProductCategoryService {
	/**
	 * 根据categoryId查询
	 * @param categoryId
	 * @return
	 */
	ProductCategoryDO findOne(Integer categoryId);

	/**
	 * 查询所有
	 * @return
	 */
	List<ProductCategoryDO> findAll();

	/**
	 * 根据类目编号集合查询
	 * @param categoryTypeList
	 * @return
	 */
	List<ProductCategoryDO> findByCategoryTypeIn(List<Integer> categoryTypeList);

	/**
	 * 新增、修改
	 * @param productCategory
	 * @return
	 */
	ProductCategoryDO save(ProductCategoryDO productCategory);
}
