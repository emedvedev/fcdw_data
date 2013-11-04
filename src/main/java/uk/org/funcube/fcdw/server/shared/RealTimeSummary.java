// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;

public class RealTimeSummary {

	private String photoVoltsXPlus = "0.0";
	private String photoVoltsXMinus = "0.0";
	private String photoVoltsYPlus = "0.0";
	private String photoVoltsYMinus = "0.0";
	
	public RealTimeSummary() {
	}

	public String getPhotoVoltsXPlus() {
		return photoVoltsXPlus;
	}

	public void setPhotoVoltsXPlus(String photoVoltsXPlus) {
		this.photoVoltsXPlus = photoVoltsXPlus;
	}

	public String getPhotoVoltsXMinus() {
		return photoVoltsXMinus;
	}

	public void setPhotoVoltsXMinus(String photoVoltsXMinus) {
		this.photoVoltsXMinus = photoVoltsXMinus;
	}

	public String getPhotoVoltsYPlus() {
		return photoVoltsYPlus;
	}

	public void setPhotoVoltsYPlus(String photoVoltsYPlus) {
		this.photoVoltsYPlus = photoVoltsYPlus;
	}

	public String getPhotoVoltsYMinus() {
		return photoVoltsYMinus;
	}

	public void setPhotoVoltsYMinus(String photoVoltsYMinus) {
		this.photoVoltsYMinus = photoVoltsYMinus;
	}
	
	

}
