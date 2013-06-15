package com.potatosoft.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.springframework.beans.factory.annotation.Value;

import com.potatosoft.common.Packet;
 
/**
 * Ugly TCP client test code for testing.
 * 
 * @author wonhee.jung
 *
 */
public class ClientForTest implements Runnable {
	
	@Value("${server.listen.port}")
	private int port;
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
        channel.connect(new InetSocketAddress(SERVER_HOSTNAME, port));
 
        while (!channel.finishConnect()) {
            // System.out.println("still connecting");
        }
        
        // Send given packet bytes to server side.
        ByteBuffer buffer = this.packet.toByteBuffer();
        
        int bytesWritten = 0;
        while (buffer.hasRemaining()) {
        	bytesWritten += channel.write(buffer);
        }
        // for debugging
        System.out.println("bytes written:" + bytesWritten + ", actual in the Packet : " + this.packet.toByteBuffer().remaining());
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
    	
    	Packet packet1 = new Packet((byte)1, (short)1, 123, "Payload in packet1");
    	Packet packet2 = new Packet((byte)1, (short)1, 456, "Payload in packet2");
    	Packet packet3 = new Packet((byte)1, (short)1, 789, "Payload in packet3");
    	
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
