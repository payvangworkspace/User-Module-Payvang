package com.Payvang.Login.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import com.Payvang.Login.DataAccess.Models.NotificationEmailer;
import com.Payvang.Login.Models.NotificationEmailerDTO;
import com.Payvang.Login.Services.NotificationEmailerService;
 
import java.util.List;
import java.util.Optional;
 
@RestController
@RequestMapping("/api/notification-emailers")
public class NotificationEmailerController {
 
    private final NotificationEmailerService service;
 
    @Autowired
    public NotificationEmailerController(NotificationEmailerService service) {
        this.service = service;
    }
 
   
    @GetMapping
    public ResponseEntity<List<NotificationEmailer>> getAllNotificationEmailers() {
        List<NotificationEmailer> emailers = service.getAllNotificationEmailers();
        return ResponseEntity.ok(emailers);
    }
 
   
    @GetMapping("/{id}")
    public ResponseEntity<NotificationEmailer> getNotificationEmailerById(@PathVariable ("id")Long id) {
        Optional<NotificationEmailer> emailer = service.getNotificationEmailerById(id);
        return emailer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
 
   
    @GetMapping("/appId/{appId}")
    public ResponseEntity<NotificationEmailer> getNotificationEmailerByAppId(@PathVariable ("appId")String appId) {
        NotificationEmailer emailer = service.getNotificationEmailerByAppId(appId);
        return emailer != null ? ResponseEntity.ok(emailer) : ResponseEntity.notFound().build();
    }
    
 
    @PostMapping
    public ResponseEntity<?> createNotificationEmailers(@RequestBody NotificationEmailerDTO notificationEmailer) {
        try {
           
            String response = service.createNotificationEmailer(notificationEmailer);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error creating NotificationEmailer: " + e.getMessage());
        }
    }
}
 