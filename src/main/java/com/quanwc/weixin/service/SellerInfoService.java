package com.quanwc.weixin.service;

import com.quanwc.weixin.model.entity.SellerInfo;

/**
 * 卖家信息service接口层
 * @author quanwenchao
 * @date 2019/1/19 14:37:50
 */
public interface SellerInfoService {

	/**
	 * 根据openid查询卖家信息
	 * @param openid
	 * @return
	 */
	SellerInfo findByOpenid(String openid);
}
