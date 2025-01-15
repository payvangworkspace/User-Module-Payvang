package com.Payvang.Login.DataAccess.Models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
	@Proxy(lazy=false)@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	public class Roles implements Serializable {

		private static final long serialVersionUID = 3527144404701118357L;

		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		private long id;	
		private String name;

		@OneToMany(targetEntity=Permissions.class,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
		private Set<Permissions> permissions = new HashSet<Permissions>(); 

		public Roles(){
		}

		public Roles(long id, String name){
			this.id = id;
			this.name = name;
		}

			
		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public void addPermission(Permissions permission){
			this.permissions.add(permission);
		}

		
		public Set<Permissions> getPermissions() {
			return permissions;
		}

		public void setPermissions(Set<Permissions> permissions) {
			this.permissions = permissions;
		} 
	}


