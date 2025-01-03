package com.Payvang.Login.DataAccess.Models;

import java.io.Serializable;

import org.hibernate.annotations.Proxy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Proxy(lazy = false)
public class Permissions implements Serializable {

	private static final long serialVersionUID = 6177396246439248889L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="permission")
	private PermissionType permissionType;

	public Permissions() {
	}

	public Permissions(long id, PermissionType permissionType) {
		this.id = id;
		this.permissionType = permissionType;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public PermissionType getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(PermissionType permissionType) {
		this.permissionType = permissionType;
	}
	
}
