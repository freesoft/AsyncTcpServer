package com.potatosoft.server;

import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;

import org.springframework.stereotype.Component;

import com.google.protobuf.InvalidProtocolBufferException;
import com.potatosoft.protobuf.PacketProtos.Packet;


/**
 * Handles read byte.
 * 
 * @author wonhee.jung
 *
 */
@Component
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

	public void completed(Integer result, ByteBuffer buffer) {
        buffer.flip();
        System.out.println("bytes in the buffer are : " + buffer.remaining());
        Packet packet = null;
        byte[] dst = null;
		try {
			dst = new byte[buffer.remaining()];
			buffer.get(dst);
			packet = Packet.parseFrom(dst);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
        System.out.println("From Client[from Packet class] [" + packet.toString() + "]");
	}

	public void failed(Throwable exc, ByteBuffer buffer) {
        throw new UnsupportedOperationException("read failed!");
	}

}
