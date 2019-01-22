package com.quanwc.weixin.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 卖家信息
 *
 * @author quanwenchao
 * @date 2019/1/19 14:13:18
 */
@Data
@Entity
public class SellerInfo {

	/**
	 * 主键id
	 */
	@Id
	private String sellerId;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 微信openid
	 */
	private String openid;

}
