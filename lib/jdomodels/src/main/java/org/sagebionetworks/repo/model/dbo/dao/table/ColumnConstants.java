package org.sagebionetworks.repo.model.dbo.dao.table;

import java.io.UnsupportedEncodingException;

public class ColumnConstants {

	public static final int MAX_BYTES_PER_CHAR_UTF_8;
	static{
		char[] chars = new char[]{Character.MAX_VALUE};
		try {
			MAX_BYTES_PER_CHAR_UTF_8 = new String(chars).getBytes("UTF-8").length;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * The maximum number of bytes of a boolean when represented as a string.
	 */
	public static final int MAX_BOOLEAN_BYTES_AS_STRING;
	static{
		try {
			MAX_BOOLEAN_BYTES_AS_STRING = "FALSE".getBytes("UTF-8").length;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * The maximum number of bytes of a long when represented as a string.
	 */
	public static final int MAX_LONG_BYTES_AS_STRING;
	static{
		try {
			MAX_LONG_BYTES_AS_STRING = Long.toString(-Long.MAX_VALUE).getBytes("UTF-8").length;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * The maximum number of bytes of a double when represented as a string.
	 */
	public static final int MAX_DOUBLE_BYTES_AS_STRING;
	static{
		try {
			MAX_DOUBLE_BYTES_AS_STRING = Double.toString(-Double.MAX_VALUE).getBytes("UTF-8").length;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * The maximum number of bytes of a FileHandle ID when represented as a string (same as long).
	 */
	public static final int MAX_FILE_HANDLE_ID_BYTES_AS_STRING = MAX_LONG_BYTES_AS_STRING;
}
