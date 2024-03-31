package com.twd.SpringSecurityJWT.repository;

import com.twd.SpringSecurityJWT.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepo extends JpaRepository<Course, Integer> {
}
