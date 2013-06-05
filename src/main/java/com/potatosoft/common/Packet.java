package com.potatosoft.common;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Class to define network packet.
 * 
 * 
 * @author wonhee.jung
 *
 */
public class Packet implements BynaryFormatConvertable {
	
	private static final int INTERNAL_BUFFER_SIZE = 1024;
	
	private byte version;
	private short messageType;
	private int userId;
	private String payload;
	
	/**
	 * default constructor
	 */
	public Packet(){
		
	}
	
	public Packet(byte version, short messageType, int userId, String payload){
		this.version = version;
		this.messageType = messageType;
		this.userId = userId;
		this.payload = payload;
	}
	
	/**
	 * Create and initialize object with given ByteBuffer value.
	 * 
	 * @param buffer
	 */
	public Packet(ByteBuffer buffer){
		version = buffer.get();
		messageType = buffer.getShort();
		userId = buffer.getInt();
		final int sizeOfPayload = buffer.remaining();
		byte[] temp = new byte[sizeOfPayload];
		buffer.get(temp);
		payload = new String(temp, Charset.forName("UTF-8"));  
	}
	
	
	public byte getVersion() {
		return version;
	}
	public void setVersion(byte version) {
		this.version = version;
	}
	public short getMessageType() {
		return messageType;
	}
	public void setMessageType(short messageType) {
		this.messageType = messageType;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	/**
	 * Convert values of method filed as byte for network transfer 
	 * @return ByteBuffer which includes byte content of this 
	 */
	public ByteBuffer toByteBuffer(){
		ByteBuffer buffer = ByteBuffer.allocate(INTERNAL_BUFFER_SIZE);
		buffer.put(this.version);
		buffer.putShort(this.messageType);
		buffer.putInt(this.userId);
		buffer.put(payload.getBytes());
		buffer.flip();
		return buffer;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("version:" + version);
		builder.append(", ");
		builder.append("messageType:" + messageType);
		builder.append(", ");
		builder.append("userId:" + userId);
		builder.append(", ");
		builder.append("payload: [" + payload + "]");
		return builder.toString();
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + messageType;
		result = prime * result + ((payload == null) ? 0 : payload.hashCode());
		result = prime * result + userId;
		result = prime * result + version;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Packet))
			return false;
		Packet other = (Packet) obj;
		if (messageType != other.messageType)
			return false;
		if (payload == null) {
			if (other.payload != null)
				return false;
		} else if (!payload.equals(other.payload))
			return false;
		if (userId != other.userId)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
