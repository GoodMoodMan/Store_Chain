package logic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Command implements Serializable {
	private String id; //Command to execute
	private Object data; //Object type of ArrayList<ArrayList<String>>

	public Command(String id, Object data) {
		this.id = id;
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}