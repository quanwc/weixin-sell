package com.quanwc.weixin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quanwc.weixin.model.entity.ProductCategoryDO;

/**
 * 类目repository
 * @author quanwenchao
 * @date 2018/12/10 23:31:39
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryDO, Integer>{
	//继承JpaRepository来完成对数据库的操作

	/**
	 * 根据类目编号集合查询
	 * @param categoryTypeList
	 * @return
	 */
	List<ProductCategoryDO> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
