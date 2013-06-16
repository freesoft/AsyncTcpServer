package com.potatosoft.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.potatosoft.protobuf.PacketProtos.RequestPacket;
import com.potatosoft.protobuf.PacketProtos.ResponsePacket;
import com.potatosoft.protobuf.PacketProtos.RequestPacket.MessageType;
 
/**
 * Ugly TCP client test code for testing.
 * 
 * @author wonhee.jung
 *
 */
public class ClientForTest implements Runnable {
	
	// please check those two configuration from server side and change it properly
	// if you want to use it for testing.
	private final static int PORT = 5000;
	private final static String SERVER_HOSTNAME = "localhost";
	
	private RequestPacket packet;
	
	private Logger logger = LoggerFactory.getLogger(ClientForTest.class);
	
	public ClientForTest(){
				
	}
	
	public ClientForTest(RequestPacket packet){
		this.packet = packet;
	}
	

	public RequestPacket getPacket() {
		return packet;
	}

	public void setPacket(RequestPacket packet) {
		this.packet = packet;
	}

	
	public void send() throws IOException{

    	SocketChannel channel = SocketChannel.open();
 
        channel.configureBlocking(false); // non-blocking mode on
        logger.debug("Connecting to {}({})", SERVER_HOSTNAME, PORT);
        channel.connect(new InetSocketAddress(SERVER_HOSTNAME, PORT));
 
        while (!channel.finishConnect()) {
            // System.out.println("still connecting");
        }
        
        // Send given packet bytes to server side.
        //ByteBuffer buffer = ByteBuffer.this.packet.toByteArray());
        ByteBuffer buffer = ByteBuffer.wrap(this.packet.toByteArray());
        
        int bytesWritten = 0;
        while (buffer.hasRemaining()) {
        	bytesWritten += channel.write(buffer);
        }
        // for debugging
        logger.debug("bytes written:{}, actual in the Packet : {}", bytesWritten, this.packet.getSerializedSize());
        
        ByteBuffer incomingBuffer = ByteBuffer.allocate(1024);
        while (channel.read(incomingBuffer) >= 0){
        	
        }
        channel.close();
        incomingBuffer.flip();

        byte[] dst = new byte[incomingBuffer.remaining()];
		incomingBuffer.get(dst);
        
        ResponsePacket response = ResponsePacket.parseFrom(dst);
        logger.debug("response({})", response.toString());

	}
	
	public void run() {
		while(true){
			try {
				send();
				Thread.sleep(100);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
     
    public static void main(String[] args){
    	
    	RequestPacket packet1 = 
			    	RequestPacket.newBuilder()
					.setVersion(1)
					.setMessageType(MessageType.LOGIN)
					.setUserId(123)
					.setPayload("Payload in packet1")
					.build();
			    	
    	RequestPacket packet2 = 
		    	RequestPacket.newBuilder()
				.setVersion(1)
				.setMessageType(MessageType.LOGIN)
				.setUserId(456)
				.setPayload("Payload in packet2")
				.build();
    	
    	RequestPacket packet3 = 
		    	RequestPacket.newBuilder()
				.setVersion(1)
				.setMessageType(MessageType.LOGIN)
				.setUserId(789)
				.setPayload("Payload in packet3")
				.build();
    	
    	
    	ClientForTest client1 = new ClientForTest(packet1);
    	ClientForTest client2 = new ClientForTest(packet2);
    	ClientForTest client3 = new ClientForTest(packet3);
    	
    	Thread t1 = new Thread(client1);
    	Thread t2 = new Thread(client2);
    	Thread t3 = new Thread(client3);
    	
    	t1.start();
    	t2.start();
    	t3.start();
    }


}
