package com.twd.SpringSecurityJWT.controller;

import com.twd.SpringSecurityJWT.dto.ReqRes;
import com.twd.SpringSecurityJWT.entity.Course;
import com.twd.SpringSecurityJWT.entity.OurUsers;
import com.twd.SpringSecurityJWT.repository.CourseRepo;
import com.twd.SpringSecurityJWT.repository.OurUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminUsers {

    private final CourseRepo courseRepo;

    private final OurUserRepo ourUserRepo;

    public AdminUsers(CourseRepo courseRepo, OurUserRepo ourUserRepo) {
        this.courseRepo = courseRepo;
        this.ourUserRepo = ourUserRepo;
    }


    // admin methods to users...



    @GetMapping("/admin/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> getAllUsers(){
        return ResponseEntity.ok(ourUserRepo.findAll());
    }

    @PostMapping("/admin/saveuser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> saveUser(@RequestBody ReqRes userRequest){
        OurUsers userToSave = new OurUsers();
        userToSave.setEmail(userRequest.getEmail());
        userToSave.setPassword(userRequest.getPassword());
        userToSave.setRole(userRequest.getRole());
        userToSave.setUsername(userRequest.getUsername());
        userToSave.setMobileNumber(userRequest.getMobileNumber());
        return ResponseEntity.ok(ourUserRepo.save(userToSave));
    }

    @DeleteMapping("/admin/deleteuser/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer userId){
        ourUserRepo.deleteById(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping("/admin/edituser/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> editUser(@PathVariable Integer userId, @RequestBody ReqRes userRequest){
        OurUsers userToUpdate = ourUserRepo.findById(userId).orElse(null);
        if(userToUpdate != null){
            userToUpdate.setEmail(userRequest.getEmail());
            userToUpdate.setPassword(userRequest.getPassword());
            userToUpdate.setUsername(userRequest.getUsername());
            userToUpdate.setMobileNumber(userRequest.getMobileNumber());
            userToUpdate.setRole(userRequest.getRole());
            ourUserRepo.save(userToUpdate);
            return ResponseEntity.ok("User updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//////////


/////////  admin methods to courses

    @GetMapping("/public/course")
    public ResponseEntity<Object> getAllCourses(){
        return ResponseEntity.ok(courseRepo.findAll());
    }

    @PostMapping("/admin/savecourse")
    public ResponseEntity<Object> saveCourse(@RequestBody ReqRes courseRequest){
        Course courseToSave = new Course();
        courseToSave.setName(courseRequest.getName());
        courseToSave.setContent(courseRequest.getContent());
        courseToSave.setPrice(Double.parseDouble(courseRequest.getPrice()));
        return ResponseEntity.ok(courseRepo.save(courseToSave));
    }
    @PreAuthorize("hasAuthority('ADMIN')") // Only allow admins to edit courses
    @PutMapping("/admin/editcourse/{courseId}") //
    public ResponseEntity<Object> editCourse(@PathVariable Integer courseId, @RequestBody ReqRes courseRequest){
        Course courseToUpdate = courseRepo.findById(courseId).orElse(null);
        if(courseToUpdate != null){
            courseToUpdate.setName(courseRequest.getName());
            courseRepo.save(courseToUpdate);
            return ResponseEntity.ok("Course updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PreAuthorize("hasAuthority('ADMIN')") // Only allow admins to delete courses
    @DeleteMapping("/admin/deletecourse/{courseId}") // Delete a course by ID
    public ResponseEntity<Object> deleteCourse(@PathVariable Integer courseId){
        courseRepo.deleteById(courseId);
        return ResponseEntity.ok("Course deleted successfully");
    }


    @GetMapping("/user/alone")
    public ResponseEntity<Object> userAlone(){
        return ResponseEntity.ok("Users alone can access this ApI only");
    }

    @GetMapping("/adminuser/both")
    public ResponseEntity<Object> bothAdminaAndUsersApi(){
        return ResponseEntity.ok("Both Admin and Users Can  access the api");
    }

    @GetMapping("/public/email")
    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        System.out.println(authentication.getDetails());
        System.out.println(authentication.getName());
        return authentication.getName();
    }
}
