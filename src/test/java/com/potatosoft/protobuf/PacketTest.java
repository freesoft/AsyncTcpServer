package com.potatosoft.protobuf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.potatosoft.protobuf.PacketProtos.Packet;
import com.potatosoft.protobuf.PacketProtos.Packet.MessageType;

public class PacketTest {

	@Test
	public void testSetterAndGetter() {

		byte version = 1;
		MessageType messageType = MessageType.LOGIN;
		int userId = 1000;
		String payload = "Hello World";

		
		Packet packet = Packet.newBuilder()
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

		Packet packet1 = Packet.newBuilder().setVersion(1).setMessageType(MessageType.LOGIN).setUserId(1000).setPayload("123").build();
		Packet packet2 = Packet.newBuilder().setVersion(1).setMessageType(MessageType.LOGIN).setUserId(1000).setPayload("123").build();
		
		assertTrue(packet1.equals(packet2));

		packet1 = Packet.newBuilder().setVersion(1).setMessageType(MessageType.LOGIN).setUserId(1000).setPayload("123").build();
		packet2 = Packet.newBuilder().setVersion(2).setMessageType(MessageType.LOGIN).setUserId(1000).setPayload("123").build();
		
		assertFalse(packet1.equals(packet2));
	}

}
