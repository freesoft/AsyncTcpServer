package com.potatosoft.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 
 * @author wonhee.jung
 *
 * TODO Change logger to use logback or log4j instead of System.out.println
 * TODO Move some variables to external properties file and inject it into source code. ( with Spring framework )
 * TODO More load-test
 * TODO Add heart beat check if connection requires keep-alive communication between server and client 

 */
public class Server
{
    final static String SERVER_IP = "localhost";
    final static int SERVER_PORT = 5000;
    
    final static int BUFFER_SIZE = 1024;
    final static long THREAD_SLEEP_MILLISECONDS = 100L;
    
    final static String ENCODING_CHARSET = "UTF-8";
    
	private static final Server server = new Server();
	
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
                asynServerSocketChannel.bind(new InetSocketAddress(SERVER_IP, SERVER_PORT));
 
                System.out.println("Waiting client connection...");
                
                while (true)
                {
                    Future<AsynchronousSocketChannel> asyncSocketChannelFuture =
                        asynServerSocketChannel.accept();
                    
                    try (AsynchronousSocketChannel asyncSocketChannel = asyncSocketChannelFuture.get())
                    {
                        System.out.println("Client connected from : " + asyncSocketChannel.getRemoteAddress());
                        ByteBuffer incomingBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);

                        // Read bytes from client side
                        asyncSocketChannel.read(incomingBuffer, incomingBuffer,  new ReadCompletionHandler());
                        try
                        {
                            Thread.sleep(THREAD_SLEEP_MILLISECONDS);
                        }
                        catch(Exception e){}
                         
                        // Add extra code at here if server side need to to write packets to client side like ACK packet or response message.
                        // ByteBuffer outBuffer = ByteBuffer.wrap("Hello, World".getBytes(Charset.forName(ENCODING_CHARSET)););
                        // asyncSocketChannel.write(outgoingBuffer).get();
                    }
                    catch (IOException | ExecutionException | InterruptedException ex)
                    {
                        System.err.println(ex);
                    }
                }
            }
            else
            {
                System.err.println("Can't open async server socket channel.");
            }
        }
        catch (IOException ex)
        {
            System.err.println(ex);
        }
    }
    
    public static void main(String[] args) throws IOException
    {
    	Server server = Server.getInstance();
    	server.run();
    }
}