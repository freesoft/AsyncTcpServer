package com.potatosoft.common;

import java.nio.ByteBuffer;

/**
 * Every class need to be sent/received through network should implmenet this interface.
 * 
 * @author wonhee.jung
 *
 */
public interface BynaryFormatConvertable {

	public ByteBuffer toByteBuffer();
	
}
