package com.quanwc.weixin.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 *
 * @author quanwenchao
 * @date 2018/12/29 17:25:06
 */

// 将 Date -> Long，JsonSerializer<Date>表示入参是Date类型
public class Date2LongSerializer extends JsonSerializer<Date> {
	@Override
	public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException, JsonProcessingException {
		// 将传进来的date/1000后，再调用writeNumber()写回去
		jsonGenerator.writeNumber(date.getTime() / 1000);
	}
}
