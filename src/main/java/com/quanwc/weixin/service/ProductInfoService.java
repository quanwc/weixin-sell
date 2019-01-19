package com.quanwc.weixin.service;

import java.util.List;

import com.quanwc.weixin.model.dto.ShopCartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.quanwc.weixin.model.entity.ProductInfo;

/**
 * 商品详情service接口层
 * @author quanwenchao
 * @date 2018/12/15 10:20:15
 */
public interface ProductInfoService {

	/**
	 * 根据主键productId查询
	 * @param productId
	 * @return
	 */
	ProductInfo findOne(String productId);

	/**
	 * 查询所有在架商品列表
	 * @return
	 */
	List<ProductInfo> findUpAll();

	/**
	 * 查询所有商品，带分页功能
	 * @return
	 */
	Page<ProductInfo> findAll(Pageable pageable);

	/**
	 * 新增
	 * @param productInfo
	 * @return
	 */
	ProductInfo save(ProductInfo productInfo);

	/**
	 * 加库存
	 * @param shopCartDTOList
	 */
	void increaseStock(List<ShopCartDTO> shopCartDTOList);

	/**
	 * 减库存
	 * @param shopCartDTOList
	 */
	void decreaseStock(List<ShopCartDTO> shopCartDTOList);

	/**
	 * 上架
	 * @param productId
	 * @return
	 */
	ProductInfo onSale(String productId);

	/**
	 * 下架
	 * @param productId
	 * @return
	 */
	ProductInfo offSale(String productId);
}
