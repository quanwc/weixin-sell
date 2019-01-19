package com.quanwc.weixin.model.form;

import lombok.Data;

/**
 * 类目的新增、修改请求表单，类似于request对象
 *
 * @author quanwenchao
 * @date 2019/1/18 23:05:16
 */
@Data
public class CategoryForm {


	/**
	 * 类目id
	 */
	private Integer categoryId;

	/**
	 * 类目名称
	 */
	private String categoryName;

	/**
	 * 类目编号
	 */
	private Integer categoryType;

}
