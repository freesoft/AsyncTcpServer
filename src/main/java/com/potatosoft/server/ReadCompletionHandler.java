package com.potatosoft.server;

import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;

import com.potatosoft.common.Packet;

/**
 * Handles read byte.
 * 
 * @author wonhee.jung
 *
 */
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

	public void completed(Integer result, ByteBuffer buffer) {
        buffer.flip();
        System.out.println("bytes in the buffer are : " + buffer.remaining());
        Packet packet = new Packet(buffer);
        System.out.println("From Client[from Packet class] [" + packet.toString() + "]");
	}

	public void failed(Throwable exc, ByteBuffer buffer) {
        throw new UnsupportedOperationException("read failed!");
	}

}
