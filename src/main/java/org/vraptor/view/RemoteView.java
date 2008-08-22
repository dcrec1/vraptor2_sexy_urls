package org.vraptor.view;

import org.vraptor.remote.OutjectionSerializer;
import org.vraptor.remote.json.JSONSerializer;
import org.vraptor.remote.xml.XMLSerializer;

public enum RemoteView {
	AJAX("application/json") {
		public OutjectionSerializer newSerializer(int depth) {
			return new JSONSerializer(depth);
		}

	},
	XML("text/xml") {
		public OutjectionSerializer newSerializer(int depth) {
			return new XMLSerializer();
		}
	};

	private final String contentType;

	RemoteView(String contentType) {
		this.contentType = contentType;
	}

	public abstract OutjectionSerializer newSerializer(int depth);

	public String getContentType() {
		return contentType;
	}
}