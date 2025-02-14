package com.Payvang.Login.Services;

import java.util.Iterator;
import java.util.Set;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Payvang.Login.DataAccess.Models.PermissionType;
import com.Payvang.Login.DataAccess.Models.Permissions;
import com.Payvang.Login.DataAccess.Models.Roles;
import com.Payvang.Login.DataAccess.Models.User;
import com.Payvang.Login.Models.CreateSubAdminDTO;
import com.Payvang.Login.Repositories.UserRepository;

@Service
public class UpdateSubUserPermission {

	@Autowired
	UserRepository userrepository;

	public void updateSubuserPermission(CreateSubAdminDTO upSubAdmin) {
	
		User user = userrepository.findByEmailId(upSubAdmin.getEmailId())
				.orElseThrow(() -> new RuntimeException("Merchant not found"));

		
		Set<Roles> roles = user.getRoles();
		if (roles.isEmpty()) {
			throw new RuntimeException("User has no roles assigned");
		}

		
		Roles role = roles.iterator().next();

		
		role.getPermissions().clear();

	
		List<String> lstPermissionType = upSubAdmin.getLstPermissionType();
		if (lstPermissionType != null && !lstPermissionType.isEmpty()) {
			for (String permissionType : lstPermissionType) {
				Permissions permission = new Permissions();
				permission.setPermissionType(PermissionType.getInstanceFromName(permissionType));
				role.addPermission(permission);
			}
		}

		
		getPermissions(user, upSubAdmin);

		
		userrepository.save(user);
	}

	private void getPermissions(User user, CreateSubAdminDTO upSubAdmin) {
		Set<Roles> roles = user.getRoles();
		if (roles.isEmpty()) {
			return; 
		}

		
		Set<Permissions> permissions = roles.iterator().next().getPermissions();
		if (permissions.isEmpty()) {
			return;
		}

	
		StringBuilder perms = new StringBuilder();
		for (Permissions permission : permissions) {
			perms.append(permission.getPermissionType().getPermission());
			perms.append("-");
		}

	
		if (perms.length() > 0) {
			perms.deleteCharAt(perms.length() - 1);
		}

		
		upSubAdmin.setPermissionString(perms.toString());
	}
}

//public void updateSubuserPermission(CreateSubAdminDTO upSubAdmin) {
//
//List<String> lstPermissionType = upSubAdmin.getLstPermissionType();
//User user = userrepository.findByEmailId(upSubAdmin.getEmailId())
//		.orElseThrow(() -> new RuntimeException("Merchant not found"));
//
//Set<Roles> roles = user.getRoles();
//Iterator<Roles> itr = roles.iterator();
//Roles role = new Roles();
//if (!roles.isEmpty()) {
//	role = itr.next();
//	Iterator<Permissions> permissionIterator = role.getPermissions().iterator();
//	while (permissionIterator.hasNext()) {				
//		@SuppressWarnings("unused")
//		Permissions permission = permissionIterator.next();
//		permissionIterator.remove();
//	}
//}
//if (upSubAdmin.getLstPermissionType() == null) {
//      
//} else {
//	for (String permissionType : lstPermissionType) {
//		Permissions permission = new Permissions();
//		permission.setPermissionType(PermissionType.getInstanceFromName(permissionType));
//		role.addPermission(permission);
//	}
//}
//     getPermissions(user,upSubAdmin);
//    User userresponse = userrepository.save(user);
//
//}
//
//private void getPermissions(User agent, CreateSubAdminDTO upsubadmin) {
//Set<Roles> roles = agent.getRoles();
//Set<Permissions> permissions = roles.iterator().next().getPermissions();
//if (!permissions.isEmpty()) {
//	StringBuilder perms = new StringBuilder();
//	Iterator<Permissions> itr = permissions.iterator();
//	while (itr.hasNext()) {
//		PermissionType permissionType = itr.next().getPermissionType();
//		perms.append(permissionType.getPermission());
//		perms.append("-");
//	}
//	perms.deleteCharAt(perms.length() - 1);
//	upsubadmin.setPermissionString(perms.toString());
//}
//}
//}
