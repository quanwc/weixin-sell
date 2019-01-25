package com.quanwc.weixin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.quanwc.weixin.model.entity.ProductCategoryDO;
import com.quanwc.weixin.model.entity.ProductInfo;
import com.quanwc.weixin.service.ProductCategoryService;
import com.quanwc.weixin.util.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quanwc.weixin.model.vo.ProductInfoVO;
import com.quanwc.weixin.model.vo.ProductVO;
import com.quanwc.weixin.model.vo.ResultVO;
import com.quanwc.weixin.service.ProductInfoService;

/**
 * 买家端商品controller
 * @author quanwenchao
 * @date 2018/12/15 23:13:04
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

	@Autowired
	private ProductInfoService productInfoService;
	@Autowired
	private ProductCategoryService productCategoryService;

	/**
	 * 买家端商品列表
	 * @return
	 */
	@RequestMapping("/list")
	//@Cacheable
	public ResultVO list() {

		// step1：查询所有上架商品
		List<ProductInfo> productInfoList = productInfoService.findUpAll();

		// step2：查询商品类目
		//// 传统做法，for循环
		// List<Integer> categoryTypeList = new ArrayList<>();
		// for(ProductInfo productInfo : productInfoList) {
		// categoryTypeList.add(productInfo.getCategoryType());
		// }
		// 精简方法(java8、lambda)
		List<Integer> categoryTypeList = productInfoList.stream()
				.map(e -> e.getCategoryType()).collect(Collectors.toList());
		List<ProductCategoryDO> productCategoryDOList = productCategoryService
				.findByCategoryTypeIn(categoryTypeList);

		// step3：拼装数据
		List<ProductVO> productVOList = new ArrayList<>();
		for (ProductCategoryDO productCategory : productCategoryDOList) {
			Integer categoryType = productCategory.getCategoryType();

			ProductVO productVO = new ProductVO();
			productVO.setCategoryName(productCategory.getCategoryName());
			productVO.setCategoryType(categoryType);

			List<ProductInfoVO> productInfoVOList = new ArrayList<>();
			for (ProductInfo productInfo : productInfoList) {
				if (productInfo.getCategoryType().equals(categoryType)) {
					ProductInfoVO productInfoVO = new ProductInfoVO();
					// productInfoVO.setProductId(productInfo.getProductId());
					// productInfoVO.setProductName(productInfo.getProductName());
					// productInfoVO.setProductPrice(productInfo.getProductPrice());
					// productInfoVO.setProductIcon(productInfo.getProductIcon());
					// productInfoVO.setProductDescription(productInfo.getProductDescription());
					BeanUtils.copyProperties(productInfo, productInfoVO);
					productInfoVOList.add(productInfoVO);
				}
			}

			productVO.setProductInfoVOList(productInfoVOList);
			productVOList.add(productVO);
		}

		return ResultVOUtil.success(productVOList);
	}

}
