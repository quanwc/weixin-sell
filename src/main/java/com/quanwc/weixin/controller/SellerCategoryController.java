package com.quanwc.weixin.controller;

import com.quanwc.weixin.model.entity.ProductCategoryDO;
import com.quanwc.weixin.model.entity.ProductInfo;
import com.quanwc.weixin.model.form.CategoryForm;
import com.quanwc.weixin.model.form.ProductInfoForm;
import com.quanwc.weixin.service.ProductCategoryService;
import com.quanwc.weixin.util.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 卖家类目controller
 *
 * @author quanwenchao
 * @date 2019/1/18 22:45:50
 */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

	@Autowired
	private ProductCategoryService productCategoryService;

	/**
	 * 类目列表
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	public ModelAndView list(Map<String, Object> model) {
		List<ProductCategoryDO> productCategoryList = productCategoryService.findAll();
		model.put("productCategoryList", productCategoryList);
		return new ModelAndView("category/list", model);
	}

	/**
	 * 进入到新增、修改类目页面
	 * @param categoryId 不传为新增，传了为修改
	 * @param model
	 * @return
	 */
	@GetMapping("/index")
	public ModelAndView index(
			@RequestParam(value = "categoryId", required = false) Integer categoryId,
			Map<String, Object> model) {

		if (categoryId != null) {
			ProductCategoryDO productCategory = productCategoryService
					.findOne(categoryId);
			model.put("productCategory", productCategory);
		}

		return new ModelAndView("/category/index", model);
	}

	/**
	 * 新增、修改类目入库
	 * @param categoryForm 请求的表单数据
	 * @param bindingResult 表单校验（表单的name与ProductInfoForm对象进行属性映射）
	 * @param model
	 * @return
	 */
	@PostMapping("/save")
	public ModelAndView save(@Valid CategoryForm categoryForm,
			BindingResult bindingResult, Map<String, Object> model) {

		if (bindingResult.hasErrors()) {
			model.put("msg", bindingResult.getFieldError().getDefaultMessage());
			model.put("url", "/sell/seller/category/index");
			return new ModelAndView("common/error", model);
		}

		ProductCategoryDO productCategory = new ProductCategoryDO();
		try {
			// 修改
			if (null != categoryForm.getCategoryId()) {
				productCategory = productCategoryService.findOne(categoryForm.getCategoryId());
				BeanUtils.copyProperties(categoryForm, productCategory);
				productCategory.setUpdateTimestamp(new Date());
			}
			else {
				// 新增
				BeanUtils.copyProperties(categoryForm, productCategory);
				productCategory.setCreateTimestamp(new Date());
				productCategory.setUpdateTimestamp(new Date());
			}

			productCategoryService.save(productCategory);
		}
		catch (Exception e) {
			model.put("msg", e.getMessage());
			model.put("url", "/sell/seller/category/index");
			return new ModelAndView("common/error", model);
		}

		model.put("url", "/sell/seller/category/list");
		return new ModelAndView("common/success", model);
	}

}
