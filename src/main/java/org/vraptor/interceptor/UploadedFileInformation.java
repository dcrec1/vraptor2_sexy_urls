package org.vraptor.interceptor;

import java.io.File;

public interface UploadedFileInformation {

	/**
	 * @return Returns the contentType.
	 */
	String getContentType();

	/**
	 * @return Returns the file.
	 */
	File getFile();

	/**
	 * 
	 * @return Returns the fileName.
	 */
	String getFileName();

	/**
	 * The complete file name from this file, as it was uploaded from the
	 * client.
	 * 
	 * @return Returns the fileName.
	 */
	String getCompleteFileName();

}