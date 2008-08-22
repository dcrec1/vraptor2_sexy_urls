package org.vraptor.interceptor;

import java.io.File;

/**
 * @author Paulo Silveira
 */
public class BasicUploadedFileInformation implements UploadedFileInformation {
	private File file;

	private String contentType;

	private String fileName;

	private String completeFileName;

	private static final String NOT_UNIX_LIKE_SEPARATOR = "\\";

	private static final String UNIX_LIKE_SEPARATOR = "/";

	public BasicUploadedFileInformation(File file, String completeFileName,
			String contentType) {
		this.file = file;

		// depends upon the UPLOADER operating system, not on File.separator
		// File.separator is the separator for the server machine, not the
		// client, of course
		// TODO: use File methods to get the fileName from the completeFileName?
		if (completeFileName.indexOf(UNIX_LIKE_SEPARATOR) == -1) {
			this.fileName = completeFileName.substring(completeFileName
					.lastIndexOf(NOT_UNIX_LIKE_SEPARATOR) + 1);
		} else {
			this.fileName = completeFileName.substring(completeFileName
					.lastIndexOf(UNIX_LIKE_SEPARATOR) + 1);
		}

		this.completeFileName = completeFileName;
		this.contentType = contentType;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[uploadedFile location=" + this.file + " uploadedCompleteName="
				+ this.completeFileName + " uploadedName=" + this.fileName
				+ " contentType=" + this.contentType + "";
	}

	/**
	 * @see org.vraptor.interceptor.UploadedFileInformation#getContentType()
	 */
	public String getContentType() {
		return this.contentType;
	}

	/**
	 * @see org.vraptor.interceptor.UploadedFileInformation#getFile()
	 */
	public File getFile() {
		return this.file;
	}

	/**
	 * @see org.vraptor.interceptor.UploadedFileInformation#getFileName()
	 */
	public String getFileName() {
		return this.fileName;
	}

	/**
	 * @see org.vraptor.interceptor.UploadedFileInformation#getCompleteFileName()
	 */
	public String getCompleteFileName() {
		return this.completeFileName;
	}
}
