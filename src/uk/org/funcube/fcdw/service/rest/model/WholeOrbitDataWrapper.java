package uk.org.funcube.fcdw.service.rest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WholeOrbitDataWrapper extends AbstractWrappper implements Serializable {
	
	private static final long serialVersionUID = 5277431077208084756L;
	
	private List<WholeOrbitDataJsonOutput> json = new ArrayList<WholeOrbitDataJsonOutput>();

	public WholeOrbitDataWrapper(Long sateliteId, List<WholeOrbitDataJsonOutput> json) {
		super(sateliteId);
		this.json = json;
	}

	public final List<WholeOrbitDataJsonOutput> getJson() {
		return json;
	}

	public final void setJson(List<WholeOrbitDataJsonOutput> json) {
		this.json = json;
	}

}
