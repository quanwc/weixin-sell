package com.quanwc.weixin.service.impl;

import com.quanwc.weixin.repository.SellerInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quanwc.weixin.model.entity.SellerInfo;
import com.quanwc.weixin.service.SellerInfoService;

/**
 * 卖家信息service实现类
 * @author quanwenchao
 * @date 2019/1/19 14:38:48
 */
@Service
public class SellerInfoServiceImpl implements SellerInfoService {

	@Autowired
	private SellerInfoRepository sellerInfoRepository;

	/**
	 * 根据openid查询卖家信息
	 * @param openid
	 * @return
	 */
	@Override
	public SellerInfo findByOpenid(String openid) {
		return sellerInfoRepository.findByOpenid(openid);
	}
}
