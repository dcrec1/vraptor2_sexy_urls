package org.vraptor.remote.json;

import org.vraptor.remote.OutjectionSerializer;

public class JSONSerializer implements OutjectionSerializer {

	public  static final int MAXIMUM_DEPTH_DEFAULT = 3;
	
	private int maximumDepth;

	public JSONSerializer() {
		this(MAXIMUM_DEPTH_DEFAULT);
	}
	
	public JSONSerializer(int maximumDepth) {
		this.maximumDepth = maximumDepth;
	}

	public CharSequence serialize(Object object) {
		return new JSONWriter(maximumDepth).write(object);
	}

}
