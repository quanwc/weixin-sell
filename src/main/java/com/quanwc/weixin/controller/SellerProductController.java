package com.quanwc.weixin.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.quanwc.weixin.exception.SellException;
import com.quanwc.weixin.model.entity.ProductCategoryDO;
import com.quanwc.weixin.model.entity.ProductInfo;
import com.quanwc.weixin.model.form.ProductInfoForm;
import com.quanwc.weixin.service.ProductCategoryService;
import com.quanwc.weixin.service.ProductInfoService;
import com.quanwc.weixin.util.KeyUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 卖家商品controller
 *
 * @author quanwenchao
 * @date 2019/1/13 23:24:53
 */
@Slf4j
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

	@Autowired
	private ProductInfoService productInfoService;
	@Autowired
	private ProductCategoryService productCategoryService;

	/**
	 * 卖家端 商品列表
	 *
	 * @param page
	 * @param limit
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	public ModelAndView list(
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "limit", defaultValue = "5") Integer limit,
			Map<String, Object> model) {
		PageRequest request = new PageRequest(page - 1, limit);
		Page<ProductInfo> productInfoPage = productInfoService.findAll(request);

		model.put("productInfoPage", productInfoPage);
		model.put("currentPage", page);
		model.put("limit", limit);
		return new ModelAndView("product/list", model);
	}

	/**
	 * 上架
	 *
	 * @return
	 */
	@GetMapping("/onSale")
	public ModelAndView onSale(@RequestParam("productId") String productId,
			Map<String, Object> model) {

		try {
			productInfoService.onSale(productId);
		}
		catch (SellException e) {
			model.put("msg", e.getMessage());
			model.put("url", "/sell/seller/product/list");
			return new ModelAndView("common/error", model);
		}

		model.put("url", "/sell/seller/product/list");
		return new ModelAndView("/common/success", model);
	}

	/**
	 * 下架
	 *
	 * @return
	 */
	@GetMapping("/offSale")
	public ModelAndView offSale(@RequestParam("productId") String productId,
			Map<String, Object> model) {

		try {
			productInfoService.offSale(productId);
		}
		catch (SellException e) {
			model.put("msg", e.getMessage());
			model.put("url", "/sell/seller/product/list");
			return new ModelAndView("common/error", model);
		}

		model.put("url", "/sell/seller/product/list");
		return new ModelAndView("/common/success", model);

	}

	/**
	 * 进入到新增、修改商品页面
	 * @param productId
	 * @param model
	 * @return
	 */
	@GetMapping("/index")
	public ModelAndView index(
			@RequestParam(value = "productId", required = false) String productId,
			Map<String, Object> model) {

		if (!StringUtils.isEmpty(productId)) {
			ProductInfo productInfo = productInfoService.findOne(productId);
			model.put("productInfo", productInfo);
		}

		// 类目
		List<ProductCategoryDO> productCategoryList = productCategoryService.findAll();
		model.put("productCategoryList", productCategoryList);
		return new ModelAndView("/product/index", model);
	}

	/**
	 * 新增、修改商品入库
	 * @param productInfoForm 请求的表单数据
	 * @param bindingResult 表单校验（表单的name与ProductInfoForm对象进行属性映射）
	 * @param model
	 * @return
	 */
	@PostMapping("/save")
	public ModelAndView save(@Valid ProductInfoForm productInfoForm,
			BindingResult bindingResult, Map<String, Object> model) {

		if (bindingResult.hasErrors()) {
			model.put("msg", bindingResult.getFieldError().getDefaultMessage());
			model.put("url", "/sell/seller/product/index");
			return new ModelAndView("common/error", model);
		}


		ProductInfo productInfo = new ProductInfo();
		try {
			// 修改
			if (!StringUtils.isEmpty(productInfoForm.getProductId())) {
				// 先通过db查出productInfo、再用productInfoForm的新值覆盖productInfo的旧值
				productInfo = productInfoService.findOne(productInfoForm.getProductId());
				BeanUtils.copyProperties(productInfoForm, productInfo);
				productInfo.setUpdateTimestamp(new Date());
			} else {
				// 新增（productId为空说明是新增操作）：设置主键id
				productInfoForm.setProductId(KeyUtil.generateUniqueKey());
				BeanUtils.copyProperties(productInfoForm, productInfo);
				productInfo.setCreateTimestamp(new Date());
				productInfo.setUpdateTimestamp(new Date());
			}

			productInfoService.save(productInfo);
		}
		catch (Exception e) {
			model.put("msg", e.getMessage());
			model.put("url", "/sell/seller/product/index");
			return new ModelAndView("common/error", model);
		}

		model.put("url", "/sell/seller/product/list");
		return new ModelAndView("common/success", model);
	}
}
