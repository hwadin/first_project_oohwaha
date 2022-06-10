package network_dto;

import java.io.Serializable;

public class NetworkData<V> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3978049570963219913L;

	String action;
	V v;

	public NetworkData(String action, V v) {
		this.action = action;
		this.v = v;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public V getV() {
		return v;
	}

	public void setV(V v) {
		this.v = v;
	}

	@Override
	public String toString() {
		return "NetworkData [action=" + action + ", v=" + v + "]";
	}

}
