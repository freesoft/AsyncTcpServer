package com.potatosoft.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.springframework.beans.factory.annotation.Value;

import com.potatosoft.protobuf.PacketProtos.Packet;
import com.potatosoft.protobuf.PacketProtos.Packet.MessageType;
 
/**
 * Ugly TCP client test code for testing.
 * 
 * @author wonhee.jung
 *
 */
public class ClientForTest implements Runnable {
	
	private final static int PORT = 5000;
	private final static String SERVER_HOSTNAME = "localhost";
	
	private Packet packet;
	
	public ClientForTest(){
				
	}
	
	public ClientForTest(Packet packet){
		this.packet = packet;
	}
	

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	
	public void send() throws IOException{

    	SocketChannel channel = SocketChannel.open();
 
        channel.configureBlocking(false); // non-blocking mode on
        System.out.println("Connecting to " + SERVER_HOSTNAME + "(" + PORT + ")");
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
        System.out.println("bytes written:" + bytesWritten + ", actual in the Packet : " + this.packet.getSerializedSize());
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
    	
    	Packet packet1 = 
			    	Packet.newBuilder()
					.setVersion(1)
					.setMessageType(MessageType.LOGIN)
					.setUserId(123)
					.setPayload("Payload in packet1")
					.build();
			    	
    	Packet packet2 = 
		    	Packet.newBuilder()
				.setVersion(1)
				.setMessageType(MessageType.LOGIN)
				.setUserId(456)
				.setPayload("Payload in packet2")
				.build();
    	
    	Packet packet3 = 
		    	Packet.newBuilder()
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
