package com.potatosoft.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class PacketTest {

	@Test
	public void testSetterAndGetter() {
		
		Packet packet = new Packet();
		
		byte version = 1;
		short messageType = 123;
		int userId = 1000;
		String payload = "Hello World";
		
		packet.setVersion(version);
		packet.setMessageType(messageType);
		packet.setUserId(userId);
		packet.setPayload(payload);
		
		assertEquals(version, packet.getVersion());
		assertEquals(messageType, packet.getMessageType());
		assertEquals(userId, packet.getUserId());
		assertEquals(payload, packet.getPayload());
	}
	
	@Test
	public void testEquals(){
		Packet packet1 = new Packet();
		Packet packet2 = new Packet();
		
		assertTrue(packet1.equals(packet2));
		
		packet1.setVersion((byte)1);
		packet2.setVersion((byte)2);
		
		assertFalse(packet1.equals(packet2));
	}

}
