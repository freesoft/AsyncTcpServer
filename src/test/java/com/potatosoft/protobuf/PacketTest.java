package com.potatosoft.protobuf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.potatosoft.protobuf.PacketProtos.RequestPacket;
import com.potatosoft.protobuf.PacketProtos.RequestPacket.MessageType;

public class PacketTest {

	@Test
	public void testSetterAndGetter() {

		byte version = 1;
		MessageType messageType = MessageType.LOGIN;
		int userId = 1000;
		String payload = "Hello World";

		
		RequestPacket packet = RequestPacket.newBuilder()
						.setVersion(version)
						.setMessageType(messageType)
						.setUserId(userId)
						.setPayload(payload).build();
		
		assertEquals(version, packet.getVersion());
		assertEquals(messageType, packet.getMessageType());
		assertEquals(userId, packet.getUserId());
		assertEquals(payload, packet.getPayload());
	}
	
	@Test
	public void testEquals(){

		RequestPacket packet1 = RequestPacket.newBuilder().setVersion(1).setMessageType(MessageType.LOGIN).setUserId(1000).setPayload("123").build();
		RequestPacket packet2 = RequestPacket.newBuilder().setVersion(1).setMessageType(MessageType.LOGIN).setUserId(1000).setPayload("123").build();
		
		assertTrue(packet1.equals(packet2));

		packet1 = RequestPacket.newBuilder().setVersion(1).setMessageType(MessageType.LOGIN).setUserId(1000).setPayload("123").build();
		packet2 = RequestPacket.newBuilder().setVersion(2).setMessageType(MessageType.LOGIN).setUserId(1000).setPayload("123").build();
		
		assertFalse(packet1.equals(packet2));
	}

}
