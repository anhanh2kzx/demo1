package com.trungtamjava.springbootdemo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.trungtamjava.springbootdemo.dao.CategoryDao;
import com.trungtamjava.springbootdemo.entity.Category;

@Controller
public class CategoryController {

	@Autowired
	private CategoryDao categoryDao;

	@GetMapping("/admin/category/add")
	public String addCategory() {
		return "admin/add-category";
	}

	@PostMapping("/admin/category/add")
	public String addCategory( @RequestParam(value = "name") String name) {
		Category cate = new Category();
		cate.setName(name);

		// userDao.add(user);
		categoryDao.save(cate);

		return "redirect:/admin/category/search";
	}

	// map thang vao model, thuoc tinh model trung ten voi input name
	@PostMapping("/admin/category/add-model")
	public String addCategory(@ModelAttribute Category cate) {
//		userDao.add(user);
		categoryDao.save(cate);
		return "redirect:/admin/category/search";
	}

	@GetMapping("/admin/category/search")
	public String searchCategory(HttpServletRequest req,
			@RequestParam(value = "keyword", required = false) String keyword) {
		if (keyword == null) {
			keyword = "";
		}
		List<Category> categoryList = categoryDao.search("%" + keyword + "%");
		// tuong tu jsp forward
		req.setAttribute("cate", categoryList);

		return "/admin/search-category";
	}

	@GetMapping("/admin/category/update")
	public String update(@RequestParam(value = "id") long id, Model model) {
		model.addAttribute("cate", categoryDao.getOne(id));
		return "/update-category";
	}

	// map thang vao model, thuoc tinh model trung ten voi input name
	@PostMapping("/admin/category/update")
	public String update(@ModelAttribute Category cate) {
		categoryDao.save(cate);
		return "redirect:/admin/category/search";
	}

	@GetMapping("/admin/category/delete")
	public String delete(@RequestParam(value = "id") long id) {
		categoryDao.deleteById(id);
		return "redirect:/category/search";
	}

	// dung path variable
	@GetMapping("/admin/category/delete/{id}")
	public String deletePath(@PathVariable(value = "id") long id) {
		categoryDao.deleteById(id);
		return "redirect:/category/search";
	}
}
