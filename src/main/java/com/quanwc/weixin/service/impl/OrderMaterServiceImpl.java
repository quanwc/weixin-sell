package com.quanwc.weixin.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.quanwc.weixin.service.PayService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.quanwc.weixin.converter.OrderMaster2OrderMasterDTOConverter;
import com.quanwc.weixin.enumerator.ExceptionResultEnum;
import com.quanwc.weixin.enumerator.OrderStatusEnum;
import com.quanwc.weixin.enumerator.PayStatusEnum;
import com.quanwc.weixin.exception.SellException;
import com.quanwc.weixin.model.dto.OrderMasterDTO;
import com.quanwc.weixin.model.dto.ShopCartDTO;
import com.quanwc.weixin.model.entity.OrderDetailDO;
import com.quanwc.weixin.model.entity.OrderMasterDO;
import com.quanwc.weixin.model.entity.ProductInfo;
import com.quanwc.weixin.repository.OrderDetailRepository;
import com.quanwc.weixin.repository.OrderMasterRepository;
import com.quanwc.weixin.service.OrderMasterService;
import com.quanwc.weixin.service.ProductInfoService;
import com.quanwc.weixin.util.KeyUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 订单主表service实现类
 * @author quanwenchao
 * @date 2018/12/25 11:31:12
 */
@Slf4j
@Service
public class OrderMaterServiceImpl implements OrderMasterService {

	@Autowired
	private ProductInfoService productInfoService;
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private OrderMasterRepository orderMasterRepository;
	@Autowired
	private PayService payService;

	/**
	 * 创建订单
	 * @param orderMasterDTO
	 * @return
	 */
	@Override
	@Transactional
	public OrderMasterDTO create(OrderMasterDTO orderMasterDTO) {

		// 订单总金额
		BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

		// 订单id
		String orderId = KeyUtil.generateUniqueKey();

		// step1：查询商品的(价格、数量)
		for (OrderDetailDO orderDetail : orderMasterDTO.getOrderDetailDOList()) {
			ProductInfo productInfo = productInfoService
					.findOne(orderDetail.getProductId());
			if (productInfo == null) {
				throw new SellException(ExceptionResultEnum.PRODUCT_NOT_EXIST);
			}

			// step2：计算订单总价
			orderAmount = productInfo.getProductPrice()
					.multiply(new BigDecimal(orderDetail.getProductQuantity()))
					.add(orderAmount);

			// 订单详情入库
			orderDetail.setDetailId(KeyUtil.generateUniqueKey());
			orderDetail.setOrderId(orderId);
			BeanUtils.copyProperties(productInfo, orderDetail);
			orderDetailRepository.save(orderDetail);
		}

		// step3：写入数据库(order_master、order_detail)
		OrderMasterDO orderMaster = new OrderMasterDO();
		orderMasterDTO.setOrderId(orderId);
		BeanUtils.copyProperties(orderMasterDTO, orderMaster);
		orderMaster.setOrderAmount(orderAmount);
		orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
		orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
		orderMaster.setCreateTimestamp(new Date());
		orderMaster.setUpdateTimestamp(new Date());
		orderMasterRepository.save(orderMaster);

		// step4：扣库存
		List<ShopCartDTO> cartDTOList = orderMasterDTO.getOrderDetailDOList().stream()
				.map(e -> new ShopCartDTO(e.getProductId(), e.getProductQuantity()))
				.collect(Collectors.toList());
		productInfoService.decreaseStock(cartDTOList);

		return orderMasterDTO;
	}

	/**
	 * 查询单个订单
	 * @param orderId 订单id
	 * @return
	 */
	@Override
	public OrderMasterDTO findOne(String orderId) {
		OrderMasterDO orderMaster = orderMasterRepository.findOne(orderId);
		if (null == orderMaster) {
			throw new SellException(ExceptionResultEnum.ORDER_NOT_EXIST);
		}
		List<OrderDetailDO> orderDetailDOList = orderDetailRepository
				.findByOrderId(orderId);
		OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
		BeanUtils.copyProperties(orderMaster, orderMasterDTO);
		orderMasterDTO.setOrderDetailDOList(orderDetailDOList);
		return orderMasterDTO;
	}

	/**
	 * 查询订单列表，带分页功能
	 * @param buyerOpenid 买家微信openid
	 * @param pageable 分页参数
	 * @return Page<OrderMasterDTO>
	 */
	@Override
	public Page<OrderMasterDTO> findList(String buyerOpenid, Pageable pageable) {

		Page<OrderMasterDO> orderMasterPage = orderMasterRepository
				.findByBuyerOpenid(buyerOpenid, pageable);

		List<OrderMasterDTO> orderMasterDTOList = OrderMaster2OrderMasterDTOConverter
				.orderMasterDOList2OrderMasterDTOList(orderMasterPage.getContent());

		return new PageImpl<OrderMasterDTO>(orderMasterDTOList, pageable,
				orderMasterPage.getTotalElements());
	}

	/**
	 * 取消订单
	 * @param orderMasterDTO
	 * @return
	 */
	@Override
	@Transactional
	public OrderMasterDTO cancel(OrderMasterDTO orderMasterDTO) {

		OrderMasterDO orderMaster = new OrderMasterDO();

		// step1：判断订单状态：取消、完结的订单不能被支付
		if (!orderMasterDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("[取消订单] 订单状态不正确, orderId={}, orderStatus={}",
					orderMasterDTO.getOrderId(), orderMasterDTO.getOrderStatus());
			throw new SellException(ExceptionResultEnum.ORDER_STATUS_ERROR);
		}

		// step2：修改订单状态
		orderMasterDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
		BeanUtils.copyProperties(orderMasterDTO, orderMaster);
		OrderMasterDO updateResult = orderMasterRepository.save(orderMaster);
		if (updateResult == null) {
			log.error("[取消订单] 更新失败, orderMaster={}", orderMaster);
			throw new SellException(ExceptionResultEnum.ORDER_UPDATE_FAIL);
		}
		// step3：返回库存
		if (CollectionUtils.isEmpty(orderMasterDTO.getOrderDetailDOList())) {
			log.error("[取消订单] 订单中无商品详情, orderDTO={}", orderMasterDTO);
			throw new SellException(ExceptionResultEnum.ORDER_DETAIL_EMPTY);
		}
		List<ShopCartDTO> cartDTOList = orderMasterDTO.getOrderDetailDOList().stream()
				.map(e -> new ShopCartDTO(e.getProductId(), e.getProductQuantity()))
				.collect(Collectors.toList());
		productInfoService.increaseStock(cartDTOList);

		// step4：如果已支付，需要退款
		if (orderMasterDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
			payService.refund(orderMasterDTO);
		}
		return orderMasterDTO ;
	}

	/**
	 * 完结订单
	 * @param orderMasterDTO
	 * @return
	 */
	@Override
	public OrderMasterDTO finish(OrderMasterDTO orderMasterDTO) {

		// step1：判断订单状态
		if (!orderMasterDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("[完结订单] 订单状态不正确, orderId={}, orderStatus={}",
					orderMasterDTO.getOrderId(), orderMasterDTO.getOrderStatus());
			throw new SellException(ExceptionResultEnum.ORDER_STATUS_ERROR);
		}

		// step2：修改订单状态
		orderMasterDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
		OrderMasterDO orderMaster = new OrderMasterDO();
		BeanUtils.copyProperties(orderMasterDTO, orderMaster);
		OrderMasterDO updateResult = orderMasterRepository.save(orderMaster);
		if (updateResult == null) {
			log.error("[完结订单] 更新失败, orderMaster={}", orderMaster);
			throw new SellException(ExceptionResultEnum.ORDER_UPDATE_FAIL);
		}
		return orderMasterDTO;
	}

	/**
	 * 支付订单
	 *
	 * @param orderMasterDTO
	 * @return
	 */
	@Override
	public OrderMasterDTO paid(OrderMasterDTO orderMasterDTO) {
		// step1：判断订单状态
		if (!orderMasterDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("[支付订单] 订单状态不正确, orderId={}, orderStatus={}",
					orderMasterDTO.getOrderId(), orderMasterDTO.getOrderStatus());
			throw new SellException(ExceptionResultEnum.ORDER_STATUS_ERROR);
		}

		// step2：判断支付状态
		if (!orderMasterDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
			log.error("[支付订单] 订单支付状态不正确, orderMasterDTO={}", orderMasterDTO);
			throw new SellException(ExceptionResultEnum.ORDER_PAY_STATUS_ERROR);
		}

		// step3：修改支付状态
		orderMasterDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
		OrderMasterDO orderMasterDO = new OrderMasterDO();
		BeanUtils.copyProperties(orderMasterDTO, orderMasterDO);
		OrderMasterDO updateResult = orderMasterRepository.save(orderMasterDO);
		if (updateResult == null) {
			log.error("[支付订单完成] 更新失败, orderMaster={}", orderMasterDO);
			throw new SellException(ExceptionResultEnum.ORDER_UPDATE_FAIL);
		}
		return orderMasterDTO;
	}


	@Override
	public Page<OrderMasterDTO> findList(Pageable pageable) {
		Page<OrderMasterDO> orderMasterPage = orderMasterRepository.findAll(pageable);

		List<OrderMasterDTO> orderMasterDTOList = OrderMaster2OrderMasterDTOConverter
				.orderMasterDOList2OrderMasterDTOList(orderMasterPage.getContent());

		return new PageImpl<OrderMasterDTO>(orderMasterDTOList, pageable,
				orderMasterPage.getTotalElements());
	}
}
