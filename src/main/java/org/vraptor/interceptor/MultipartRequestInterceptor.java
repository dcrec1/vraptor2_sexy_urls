package org.vraptor.interceptor;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.http.VRaptorServletRequest;
import org.vraptor.view.ViewException;

/**
 * Interceptor capable of parsing the input stream.
 *
 * @author Guilherme Silveira
 * @author Paulo Silveira
 */
public class MultipartRequestInterceptor implements Interceptor {
	private static final Logger LOG = Logger.getLogger(MultipartRequestInterceptor.class);

	private final File temporaryDirectory;

	private final long sizeLimit;

	public MultipartRequestInterceptor() throws IOException {
		this.sizeLimit = 2 * 1024 * 1024;
		// this directory must be configurable through the properties
		this.temporaryDirectory = File.createTempFile("raptor.", ".upload").getParentFile();
	}

	@SuppressWarnings("unchecked")
	public void intercept(LogicFlow flow) throws LogicException, ViewException {

		if (!ServletFileUpload.isMultipartContent(flow.getLogicRequest().getRequest())) {
			flow.execute();
			return;
		}

		VRaptorServletRequest servletRequest = (VRaptorServletRequest) flow.getLogicRequest().getRequest();

		LOG.debug("Trying to parse multipart request.");

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory(4096 * 16, this.temporaryDirectory);
		LOG.debug("Using repository [" + factory.getRepository() + "]");

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// TODO: variables in raptor.properties
		upload.setSizeMax(sizeLimit);

		List<FileItem> fileItems;

		// assume we know there are two files. The first file is a small
		// text file, the second is unknown and is written to a file on
		// the server
		try {
			fileItems = upload.parseRequest(servletRequest);
		} catch (FileUploadException e) {
			LOG
					.warn(
							"There was some problem parsing this multipart request, or someone is not sending a RFC1867 compatible multipart request.",
							e);
			flow.execute();
			return;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("Found [" + fileItems.size() + "] attributes in the multipart form submission. Parsing them.");
		}

		for (FileItem item : fileItems) {
			if (item.isFormField()) {
				servletRequest.overwriteParameters(item.getFieldName(), item.getString());
			} else {
				if (!item.getName().trim().equals("")) {
					try {
						File file = File.createTempFile("raptor.", ".upload");
						file.deleteOnExit();
						item.write(file);
						UploadedFileInformation fileInformation = new BasicUploadedFileInformation(file,
								item.getName(), item.getContentType());
						servletRequest.setAttribute(item.getFieldName(), fileInformation);
						LOG.info("Uploaded file: " + item.getFieldName() + " with " + fileInformation);
					} catch (Exception e) {
						LOG.error("Nasty uploaded file " + item.getName(), e);
					}
				} else {
					LOG.info("A file field was empy: " + item.getFieldName());
				}
			}
		}

		flow.execute();

		// should we delete the temporary files afterwards or onExit as done by
		// now?
		// maybe also a config in .properties

	}
}
