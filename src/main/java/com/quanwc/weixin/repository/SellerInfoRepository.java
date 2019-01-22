package com.quanwc.weixin.repository;

import com.quanwc.weixin.model.entity.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author quanwenchao
 * @date 2019/1/19 14:15:24
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {
	/**
	 * 根据openid查询卖家信息
	 * @param openid
	 * @return
	 */
	SellerInfo findByOpenid(String openid);
}
