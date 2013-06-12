package com.potatosoft.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author wonhee.jung
 */
public class Main
{
	final static Logger logger = LoggerFactory.getLogger(Main.class);
	
    public static void main(String[] args) throws IOException
    {
    	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applications.xml"});
    	Server server = context.getBean(Server.class);
    	server.run();
    }

}