package com.quanwc.weixin.service.impl;

import java.util.Arrays;
import java.util.List;

import com.quanwc.weixin.config.WechatAccountConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quanwc.weixin.model.dto.OrderMasterDTO;
import com.quanwc.weixin.service.PushMessageService;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

/**
 * 推送消息service实现类
 * @author quanwenchao
 * @date 2019/1/21 21:34:42
 */
@Slf4j
@Service
public class PushMessageServiceImpl implements PushMessageService {

	@Autowired
	private WxMpService wxMpService;
	@Autowired
	private WechatAccountConfig wechatAccountConfig;

	/**
	 * 订单状态变更消息
	 * @param orderMasterDTO
	 */
	@Override
	public void orderStatusMessage(OrderMasterDTO orderMasterDTO) {

		WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
		wxMpTemplateMessage
				.setTemplateId(wechatAccountConfig.getTemplateId().get("orderStatus"));
		wxMpTemplateMessage.setToUser(orderMasterDTO.getBuyerOpenid());

		List<WxMpTemplateData> dataList = Arrays.asList(
				new WxMpTemplateData("first", "亲，记得收货"),
				new WxMpTemplateData("keyword1", "微信点餐"),
				new WxMpTemplateData("keyword2", "110"),
				new WxMpTemplateData("keyword3", orderMasterDTO.getOrderId()),
				new WxMpTemplateData("keyword4",
						orderMasterDTO.getOrderStatusEnum().getMsg()),
				new WxMpTemplateData("keyword5", "￥" + orderMasterDTO.getOrderAmount()),
				new WxMpTemplateData("remark", "欢迎下次光临!"));

		wxMpTemplateMessage.setData(dataList);
		try {
			wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
		}
		catch (WxErrorException e) {
			log.error("[微信模板消息] 发送失败", e);
		}
	}
}
