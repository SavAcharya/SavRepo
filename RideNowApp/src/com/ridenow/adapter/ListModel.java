package com.ridenow.adapter;

import java.io.Serializable;

public class ListModel implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private String thumurl;
	private String thumburl;
	private boolean isFbContact;
	private boolean isSelected;
    public ListModel(String name){
    	this.name = name;
    }
	public ListModel(long id, String name, String thumburl,
			boolean isfbcontact, boolean isselected) {
		this.id = id;
		this.name = name;
		this.thumburl = thumburl;
		this.isFbContact = isfbcontact;
		this.isSelected = isselected;
	}

	
	public void setisSelected(boolean istrue){
		this.isSelected =istrue;
	}
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getThumurl() {
		return thumurl;
	}

	public String getThumburl() {
		return thumburl;
	}

	public boolean isFbContact() {
		return isFbContact;
	}

	public boolean isSelected() {
		return isSelected;
	}

	
}
