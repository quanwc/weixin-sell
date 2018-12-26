package com.quanwc.weixin.model.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 商品类目do
 * @author quanwenchao
 * @date 2018/12/10 23:17:44
 */
@Data

//使用JPA注解配置映射关系
@Entity //告诉JPA这是一个实体类（和数据表映射的类）
@Table(name = "product_category") //@Table来指定和哪个数据表对应;如果省略默认表名就是product_category
@DynamicUpdate
public class ProductCategoryDO {
	/**
	 * 类目id
	 */
	@Id
	@GeneratedValue//自增主键
	private Integer categoryId;

	/**
	 * 类目名称
	 */
	private String categoryName;

	/**
	 * 类目编号
	 */
	private Integer categoryType;

	/**
	 * 创建时间
	 */
	//private Date createTimestamp;

	public ProductCategoryDO() {
	}

	/**
	 * 修改时间
	 */
	//private Date updateTimestamp;



	public ProductCategoryDO(String categoryName, Integer categoryType) {
		this.categoryName = categoryName;
		this.categoryType = categoryType;
	}
}
