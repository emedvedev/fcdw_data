package uk.org.funcube.fcdw.service.rest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HighResolutionWrapper extends AbstractWrappper implements Serializable {
	
	private static final long serialVersionUID = 5277431077208084756L;
	
	private List<HighResolutionJsonOutput> json = new ArrayList<HighResolutionJsonOutput>();

	public HighResolutionWrapper(Long sateliteId, List<HighResolutionJsonOutput> json) {
		super(sateliteId);
		this.json = json;
	}

	public final List<HighResolutionJsonOutput> getJson() {
		return json;
	}

	public final void setJson(List<HighResolutionJsonOutput> json) {
		this.json = json;
	}

}
