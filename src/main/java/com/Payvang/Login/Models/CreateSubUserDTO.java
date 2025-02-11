package com.Payvang.Login.Models;

import java.util.List;

import com.Payvang.Login.DataAccess.Models.PermissionType;

public class CreateSubUserDTO {
	
	private String firstName;
	private String lastName;
	private String mobile;
	private String emailId;
	private List<String> lstPermissionType;
	private List<PermissionType> listPermissionType;
	private String permissionString = "";
	private boolean disableButtonFlag;
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public List<String> getLstPermissionType() {
		return lstPermissionType;
	}
	public void setLstPermissionType(List<String> lstPermissionType) {
		this.lstPermissionType = lstPermissionType;
	}
	public List<PermissionType> getListPermissionType() {
		return listPermissionType;
	}
	public void setListPermissionType(List<PermissionType> listPermissionType) {
		this.listPermissionType = listPermissionType;
	}
	public String getPermissionString() {
		return permissionString;
	}
	public void setPermissionString(String permissionString) {
		this.permissionString = permissionString;
	}
	public boolean isDisableButtonFlag() {
		return disableButtonFlag;
	}
	public void setDisableButtonFlag(boolean disableButtonFlag) {
		this.disableButtonFlag = disableButtonFlag;
	}
	
}
