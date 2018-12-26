package com.quanwc.weixin.repository;

import com.quanwc.weixin.model.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 商品详情repository
 * @author quanwenchao
 * @date 2018/12/15 9:47:54
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

	/**
	 * 根据商品状态查询
	 * @param productStatus
	 * @return
	 */
	List<ProductInfo> findByProductStatus(Integer productStatus);
}
