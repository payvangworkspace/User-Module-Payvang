package com.Payvang.Login.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Payvang.Login.DataAccess.Models.Permissions;
import com.Payvang.Login.DataAccess.Models.Roles;
import com.Payvang.Login.DataAccess.Models.User;
import com.Payvang.Login.DataAccess.Models.UserProfileResponse;
import com.Payvang.Login.Models.LoginRequest;
import com.Payvang.Login.Repositories.UserRepository;
import com.Payvang.Login.Services.UserService;
import com.Payvang.Login.Util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {

	@Autowired
	UserService userservice;

	@Autowired
	JwtUtil jwtutil;

	@Autowired
	UserRepository userrepository;

	@GetMapping("profile")
	public UserProfileResponse getUserProfile(@RequestParam("id") String emailId) {
		User user = userservice.findUserProfile(emailId);
		return new UserProfileResponse( user.getBusinessName(),user.getEmailId(),user.getIndustryCategory(),user.getIndustrySubCategory());
	}

//	@GetMapping("/getRole")
//	public ResponseEntity<?> getRole(HttpServletRequest request) {
//
//		String authHeader = request.getHeader("Authorization");
//		String jwt = null;
//		String username = null;
//		String role = null;
//
//		if (authHeader != null && authHeader.startsWith("Bearer ")) {
//			jwt = authHeader.substring(7);
//			try {
//
//				username = jwtutil.extractUsername(jwt);
//
//				Optional<User> user = userrepository.findByEmailId(username);
//				if (user.isPresent()) {
//					role = user.get().getUserType().name();
//				} else {
//					return ResponseEntity.status(HttpStatus.NOT_FOUND)
//							.body("User not found with username: " + username);
//				}
//			} catch (Exception e) {
//				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//						.body("Invalid token or error while extracting role.");
//			}
//		} else {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header is missing or invalid.");
//		}
//
//		return ResponseEntity.ok(role);
//	}
	
	
	@GetMapping("/getRole")
	public ResponseEntity<?> getRole(HttpServletRequest request) {
	    String authHeader = request.getHeader("Authorization");
	    String jwt = null;
	    String username = null;
	    List<String> roles = new ArrayList<>();
	    List<String> permissions = new ArrayList<>();
         String usertype=null;
	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        jwt = authHeader.substring(7);  // Extract JWT from the Authorization header
	        try {
	            // Extract username from the token
	            username = jwtutil.extractUsername(jwt);

	            // Fetch the user by emailId (username)
	            Optional<User> user = userrepository.findByEmailId(username);

	            if (user.isPresent()) {
	            	
	            usertype	= user.get().getUserType().name();
	                // Get roles of the user
	                for (Roles role : user.get().getRoles()) {
	                    roles.add(role.getName());  // Add role name to the roles list

	                    // Get permissions for each role and add them to the permissions list
	                    for (Permissions permission : role.getPermissions()) {
	                        permissions.add(permission.getPermissionType().getPermission());
	                    }
	                }
	            } else {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .body("User not found with username: " + username);
	            }
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                    .body("Invalid token or error while extracting roles/permissions.");
	        }
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header is missing or invalid.");
	    }

	    // Return roles and permissions in the response
	    Map<String, Object> response = new HashMap<>();
	    response.put("userType", usertype);
	    response.put("roles", roles);
	    response.put("permissions", permissions);

	    return ResponseEntity.ok(response);
	}

}
