package com.trungtamjava.springbootdemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private int age;

	@Column(name = "username", unique = true)
	private String username;

	private String password;
	private String role;// vaitro
	private String gender;// gioitinh
	private String image;// url anh
	@Transient
	private MultipartFile avatarFile;
}
