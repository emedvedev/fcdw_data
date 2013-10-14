// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

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
