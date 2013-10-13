package uk.org.funcube.fcdw.service.rest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FitterMessageWrapper extends AbstractWrappper implements Serializable {
	
	private static final long serialVersionUID = 5277431077208084756L;
	
	private List<FitterMessageJsonOutput> json = new ArrayList<FitterMessageJsonOutput>();

	public FitterMessageWrapper(Long sateliteId, List<FitterMessageJsonOutput> json) {
		super(sateliteId);
		this.json = json;
	}

	public final List<FitterMessageJsonOutput> getJson() {
		return json;
	}

	public final void setJson(List<FitterMessageJsonOutput> json) {
		this.json = json;
	}

}
