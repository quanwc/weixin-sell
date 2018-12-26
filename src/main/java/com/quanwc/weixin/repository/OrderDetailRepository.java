package com.quanwc.weixin.repository;

import com.quanwc.weixin.model.entity.OrderDetailDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 订单详情repository
 * @author quanwenchao
 * @date 2018/12/25 10:04:30
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetailDO, String> {

	/**
	 * 根据订单id，查询订单详情列表
	 * @param orderId 订单id
	 * @return List<OrderDetailDO>
	 */
	List<OrderDetailDO> findByOrderId(String orderId);
}
