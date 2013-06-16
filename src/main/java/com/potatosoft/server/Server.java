package com.potatosoft.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.potatosoft.protobuf.PacketProtos.ResponsePacket;
import com.potatosoft.protobuf.PacketProtos.ResponsePacket.Result;

/**
 * 
 * @author wonhee.jung
 *
 * TODO More load-test
 * TODO Add heart beat check if connection requires keep-alive communication between server and client 

 */
@Component
public class Server
{
	@Value("${server.binding.ip}")
    private String serverBindingIp;
    
    @Value("${server.listen.port}")
    private int port;
    
    final static int BUFFER_SIZE = 1024;
    final static long THREAD_SLEEP_MILLISECONDS = 100L;
    
    @Value("${charset.encoding}")
    private String charsetEncoding = "UTF-8";
    
	private static final Server server = new Server();
	
	final static Logger logger = LoggerFactory.getLogger(Server.class);
	
	@Autowired
	private CompletionHandler<Integer, ByteBuffer> readCompletionHandler;
	
	private Server(){
		
	}
	
	public static Server getInstance(){
		return server;
	}    
	
	public void run() throws IOException
    {
        // Create asynchronous server-socket channel bound to the default group
        try (AsynchronousServerSocketChannel asynServerSocketChannel = AsynchronousServerSocketChannel.open())
        {
        	
            if (asynServerSocketChannel.isOpen())
            {
                asynServerSocketChannel.bind(new InetSocketAddress(serverBindingIp, port));
 
                logger.debug("Waiting client connection...");
                
                while (true)
                {
                    Future<AsynchronousSocketChannel> asyncSocketChannelFuture = asynServerSocketChannel.accept();
                    
                    try (AsynchronousSocketChannel asyncSocketChannel = asyncSocketChannelFuture.get())
                    {
                        logger.debug("Client connected from : {}", asyncSocketChannel.getRemoteAddress());
                        ByteBuffer incomingBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);

                        // Read bytes from client side
                        asyncSocketChannel.read(incomingBuffer, incomingBuffer,  new ReadCompletionHandler());
                        try
                        {
                            Thread.sleep(THREAD_SLEEP_MILLISECONDS);
                        }
                        catch(Exception e){}
                        
                        // add logic at here whatever you want with received message
                        // and make response message
                        
                        // default response
                        ResponsePacket response = ResponsePacket.newBuilder()
                        						.setResult(Result.SUCCESS)
                        						.setPayload("Hello World")
                        						.build();
                         
                        ByteBuffer outgoingBuffer = ByteBuffer.wrap(response.toByteArray());
                        Integer result = asyncSocketChannel.write(outgoingBuffer).get();
                    }
                    catch (IOException | ExecutionException | InterruptedException ex)
                    {
                        System.err.println(ex);
                    } 
                }
            }
            else
            {
                logger.info("Can't open async server socket channel.");
            }
        }
        catch (IOException ex)
        {
            System.err.println(ex);
        }
    }
	
	public CompletionHandler<Integer, ByteBuffer> getReadCompletionHandler() {
		return readCompletionHandler;
	}

	public void setReadCompletionHandler(CompletionHandler<Integer, ByteBuffer> readCompletionHandler) {
		this.readCompletionHandler = readCompletionHandler;
	}

}