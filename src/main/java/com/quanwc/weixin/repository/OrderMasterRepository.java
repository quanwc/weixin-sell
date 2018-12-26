package com.quanwc.weixin.repository;

import com.quanwc.weixin.model.entity.OrderMasterDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 订单主表repository
 * @author quanwenchao
 * @date 2018/12/25 9:57:19
 */
public interface OrderMasterRepository extends JpaRepository<OrderMasterDO, String> {

	/**
	 * 分页查询openid对应的订单列表
	 * @param buyerOpenid 买家openid
	 * @param pageable 分页参数
	 * @return
	 */
	Page<OrderMasterDO> findByBuyerOpenid(String buyerOpenid, Pageable pageable);

}
