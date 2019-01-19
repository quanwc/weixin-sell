package com.quanwc.weixin.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.quanwc.weixin.enumerator.ExceptionResultEnum;
import com.quanwc.weixin.enumerator.ProductStatusEnum;
import com.quanwc.weixin.exception.SellException;
import com.quanwc.weixin.model.dto.ShopCartDTO;
import com.quanwc.weixin.model.entity.ProductInfo;
import com.quanwc.weixin.repository.ProductInfoRepository;
import com.quanwc.weixin.service.ProductInfoService;

/**
 * 商品详情service实现类
 * @author quanwenchao
 * @date 2018/12/15 10:32:22
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

	@Autowired
	private ProductInfoRepository productInfoRepository;

	@Override
	public ProductInfo findOne(String productId) {
		return productInfoRepository.findOne(productId);
	}

	@Override
	public List<ProductInfo> findUpAll() {
		return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
	}

	@Override
	public Page<ProductInfo> findAll(Pageable pageable) {
		return productInfoRepository.findAll(pageable);
	}

	@Override
	public ProductInfo save(ProductInfo productInfo) {
		return productInfoRepository.save(productInfo);
	}

	/**
	 * 加库存
	 * @param shopCartDTOList
	 */
	@Override
	public void increaseStock(List<ShopCartDTO> shopCartDTOList) {
		for (ShopCartDTO shopCartDTO : shopCartDTOList) {
			ProductInfo productInfo = productInfoRepository
					.findOne(shopCartDTO.getProductId());
			if (productInfo == null) {
				throw new SellException(ExceptionResultEnum.PRODUCT_NOT_EXIST);
			}
			Integer stockResult = productInfo.getProductStock()
					+ shopCartDTO.getProductQuantity();
			productInfo.setProductStock(stockResult);
			productInfoRepository.save(productInfo);
		}
	}

	/**
	 * 扣库存
	 * @param shopCartDTOList
	 */
	@Override
	@Transactional
	public void decreaseStock(List<ShopCartDTO> shopCartDTOList) {
		if (CollectionUtils.isEmpty(shopCartDTOList)) {
			return;
		}
		for (ShopCartDTO shopCartDTO : shopCartDTOList) {
			ProductInfo productInfo = productInfoRepository
					.findOne(shopCartDTO.getProductId());
			if (productInfo == null) {
				throw new SellException(ExceptionResultEnum.PRODUCT_NOT_EXIST);
			}
			Integer stockResult = productInfo.getProductStock()
					- shopCartDTO.getProductQuantity();
			if (stockResult < 0) {
				throw new SellException(ExceptionResultEnum.PRODUCT_STOCK_ERROR);
			}
			productInfo.setProductStock(stockResult);
			productInfoRepository.save(productInfo);
		}
	}

	/**
	 * 上架
	 * @param productId
	 * @return
	 */
	@Override
	public ProductInfo onSale(String productId) {
		ProductInfo productInfo = productInfoRepository.findOne(productId);
		if (productInfo == null) {
			throw new SellException(ExceptionResultEnum.PRODUCT_NOT_EXIST);
		}
		if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
			throw new SellException(ExceptionResultEnum.PRODUCT_STATUS_ERROR);
		}

		// 更新
		productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
		return productInfoRepository.save(productInfo);
	}

	/**
	 * 下架
	 * @param productId
	 * @return
	 */
	@Override
	public ProductInfo offSale(String productId) {

		ProductInfo productInfo = productInfoRepository.findOne(productId);
		if (productInfo == null) {
			throw new SellException(ExceptionResultEnum.PRODUCT_NOT_EXIST);
		}
		if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
			throw new SellException(ExceptionResultEnum.PRODUCT_STATUS_ERROR);
		}

		// 更新
		productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
		return productInfoRepository.save(productInfo);
	}
}
