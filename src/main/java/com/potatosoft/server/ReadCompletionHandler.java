package com.potatosoft.server;

import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.protobuf.InvalidProtocolBufferException;
import com.potatosoft.protobuf.PacketProtos.RequestPacket;


/**
 * Handles read byte.
 * 
 * @author wonhee.jung
 *
 */
@Component
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
	
	private final Logger logger = LoggerFactory.getLogger(ReadCompletionHandler.class);

	public void completed(Integer result, ByteBuffer buffer) {
        buffer.flip();
        logger.debug("bytes in the buffer are : " + buffer.remaining());
        RequestPacket packet = null;
        byte[] dst = null;
		try {
			dst = new byte[buffer.remaining()];
			buffer.get(dst);
			packet = RequestPacket.parseFrom(dst);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
        logger.debug("From Client[from Packet class [{}].", packet.toString());
	}

	public void failed(Throwable exc, ByteBuffer buffer) {
        throw new UnsupportedOperationException("read failed!");
	}

}
