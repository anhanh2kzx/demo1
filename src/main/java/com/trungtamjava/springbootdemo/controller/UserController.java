package com.trungtamjava.springbootdemo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.trungtamjava.springbootdemo.dao.UserRepository;
import com.trungtamjava.springbootdemo.entity.User;

@Controller
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/admin/user/add")
	public String addUser(Model model) {
		return "/admin/add-user";
	}

//	@PostMapping("/user/add")
//	public String addUser(@RequestParam(value = "age") int age, @RequestParam(value = "name") String name) {
//		User user = new User();
//		user.setName(name);
//		user.setAge(age);
//
//		// userDao.add(user);
//		userRepository.save(user);
//
//		return "redirect:/user/search";
//	}
	@GetMapping("/download-file")
	public void download(@RequestParam("filename") String fileName,HttpServletResponse response) {
		String uploadFile = "D://Images//";
		File file = new File(uploadFile + fileName);
		if (file.exists()) {
			try {
				Files.copy(file.toPath(), response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	// map thang vao model, thuoc tinh model trung ten voi input name
	@PostMapping("/admin/user/add-model")
	public String addUser(@ModelAttribute User user,@RequestParam(value = "avatarFile") MultipartFile avatarFile) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		//luu file
		String uploadFolder = "D:\\Images\\";
		if (user.getAvatarFile() != null && !user.getAvatarFile().isEmpty()) {
			String originFilename = user.getAvatarFile().getOriginalFilename();
			//tao 1 file trong o cung server
			File file = new File(uploadFolder + originFilename);
			try {//copy noi dung vao file
				user.getAvatarFile().transferTo(file);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//luu lai ten file vao database
			user.setImage(originFilename);
		}
		userRepository.save(user);
		return "redirect:/admin/user/search";
	}

	@GetMapping("/admin/user/search")
	public String searchUser(HttpServletRequest req,
			@RequestParam(value = "keyword", required = false) String keyword) {
		if (keyword == null) {
			keyword = "";
		}
		List<User> userList = userRepository.search("%" + keyword + "%");
		// tuong tu jsp forward
		req.setAttribute("users", userList);

		return "/admin/search-user";
	}

	@GetMapping("/admin/user/update")
	public String update(@RequestParam(value = "id") int id, Model model) {
		model.addAttribute("user", userRepository.getOne(id));
		return "/admin/update-user";
	}

	// map thang vao model, thuoc tinh model trung ten voi input name
	@PostMapping("/admin/user/update")
	public String update(@ModelAttribute User user) {
		userRepository.save(user);
		return "redirect:/user/search";
	}

	@GetMapping("/admin/user/delete")
	public String delete(@RequestParam(value = "id") int id) {
		userRepository.deleteById(id);
		return "redirect:/user/search";
	}

	// dung path variable
	@GetMapping("/admin/user/delete/{id}")
	public String deletePath(@PathVariable(value = "id") int id) {
		userRepository.deleteById(id);
		return "redirect:/user/search";
	}

}
