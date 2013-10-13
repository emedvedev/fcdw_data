package uk.org.funcube.fcdw.service.rest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RealTimeWrapper extends AbstractWrappper implements Serializable {
	
	private static final long serialVersionUID = 5277431077208084756L;
	
	private List<RealTimeJsonOutput> json = new ArrayList<RealTimeJsonOutput>();

	public RealTimeWrapper(Long sateliteId, List<RealTimeJsonOutput> json) {
		super(sateliteId);
		this.json = json;
	}

	public final List<RealTimeJsonOutput> getJson() {
		return json;
	}

	public final void setJson(List<RealTimeJsonOutput> json) {
		this.json = json;
	}

}
