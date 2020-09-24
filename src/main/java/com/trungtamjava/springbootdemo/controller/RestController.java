//package com.trungtamjava.springbootdemo.controller;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.trungtamjava.springbootdemo.dao.UserRepository;
//import com.trungtamjava.springbootdemo.entity.User;
//@org.springframework.web.bind.annotation.RestController
//@RequestMapping("/api")
//public class RestController {
//	@Autowired
//	private UserRepository userRepository;
//	@GetMapping("/admin/user/search")
//	public User searchUser(HttpServletRequest req,
//			@RequestParam(value = "keyword", required = false) String keyword) {
//		if (keyword == null) {
//			keyword = "";
//		}
//		List<User> userList = userRepository.search("%" + keyword + "%");
//		// tuong tu jsp forward
//		req.setAttribute("users", userList);
//
//		return (User) userList;
//	}
//}
