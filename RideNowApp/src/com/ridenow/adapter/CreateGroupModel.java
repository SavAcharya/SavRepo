package com.ridenow.adapter;

import java.util.ArrayList;

public class CreateGroupModel {
	String groupname = "";
	boolean groupstatus;
	ArrayList<String> groupUsers;

	public CreateGroupModel(String catogariesID, boolean groupstatus,
			ArrayList<String> groupUsers) {
		this.groupname = catogariesID;
		this.groupstatus = groupstatus;
		this.groupUsers = groupUsers;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public boolean isGroupstatus() {
		return groupstatus;
	}

	public void setGroupstatus(boolean groupstatus) {
		this.groupstatus = groupstatus;
	}

	public ArrayList<String> getGroupUsers() {
		return groupUsers;
	}

	public void setGroupUsers(ArrayList<String> groupUsers) {
		this.groupUsers = groupUsers;
	}

	
}
