package com.Payvang.Login.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Payvang.Login.DataAccess.Models.PermissionType;
import com.Payvang.Login.DataAccess.Models.ResponseObject;
import com.Payvang.Login.Models.CreateSubAdminDTO;
import com.Payvang.Login.Models.CreateSubUserDTO;
import com.Payvang.Login.Services.CreateSubAdmin;
import com.Payvang.Login.Services.CreateSubUser;
import com.Payvang.Login.Services.UpdateSubUserPermission;
import com.Payvang.Login.Util.ErrorType;

@RestController
@RequestMapping("/api/role")
public class UserRoleManagementController {

	@Autowired
	private CreateSubUser createsubuser;

	@Autowired
	private CreateSubAdmin createsubadmin;
	
	@Autowired
	private UpdateSubUserPermission updateSubUserPermission;
	
	@GetMapping("/subuser/permissions")
    public ResponseEntity<List<PermissionType>> getSubUserPermissions() {
        List<PermissionType> subUserPermissions = PermissionType.getSubUserPermissionType();
        return ResponseEntity.ok(subUserPermissions);
    }

	@PostMapping("/createsubuser")
	public ResponseEntity<ResponseObject> createUser(@RequestBody CreateSubUserDTO requestbody) {

		                        ResponseObject responseObject;
		   try {

			         responseObject = createsubuser.CreateMerchantSubuser(requestbody);
			     if (ErrorType.SUCCESS.getResponseCode().equals(responseObject.getResponseCode())) {
				     return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
			 } else {
				     return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
			                                                                                        }
     } catch (Exception e) {
			               ResponseObject errorResponse = new ResponseObject();
			               errorResponse.setResponseCode(ErrorType.INTERNAL_SYSTEM_ERROR.getResponseCode());
			               errorResponse.setResponseMessage(e.getMessage());
			         return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		                                                                                             }}
	                                                                                                 

	@PostMapping("/createsubadmin")
	public ResponseEntity<ResponseObject> createSubadmin(@RequestBody CreateSubAdminDTO requestbody) {

		try {
			ResponseObject responseObject;
			responseObject = createsubadmin.CreateSubAdminstration(requestbody);
			if (ErrorType.SUCCESS.getResponseCode().equals(responseObject.getResponseCode())) {
				return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			ResponseObject errorResponse = new ResponseObject();
			errorResponse.setResponseCode(ErrorType.INTERNAL_SYSTEM_ERROR.getResponseCode());
			errorResponse.setResponseMessage(e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/subuser/{emailId}/permissions")
    public ResponseEntity<String> updateSubUserPermissions(@PathVariable ("emailId")String emailId, @RequestBody CreateSubAdminDTO upSubAdmin) {
		
           try {            
                      upSubAdmin.setEmailId(emailId);          
                    updateSubUserPermission.updateSubuserPermission(upSubAdmin);           
            return ResponseEntity.ok("Sub-user permissions updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error: " + e.getMessage());
        }
    }
	
}
