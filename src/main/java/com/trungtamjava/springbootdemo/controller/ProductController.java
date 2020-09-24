package com.trungtamjava.springbootdemo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trungtamjava.springbootdemo.dao.CategoryDao;
import com.trungtamjava.springbootdemo.dao.ProductDao;
import com.trungtamjava.springbootdemo.entity.Category;
import com.trungtamjava.springbootdemo.entity.Product;

@Controller
public class ProductController {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private CategoryDao categoryDao;

	@GetMapping("/admin/product/add")
	public String addProduct(Model model) {
		List<Category> listCategory = categoryDao.findAll();
		model.addAttribute("categoryList",listCategory );
		return "/admin/add-product";
	}

	@PostMapping("/admin/product/add-model")
	public String addProduct(@ModelAttribute(name = "pro") Product pro) {
		productDao.save(pro);
		return "redirect:/product/search";
	}

	@GetMapping("/admin/product/search")
	public String searchProduct(HttpServletRequest req,
			@RequestParam(value = "keyword", required = false) String keyword) {
		if (keyword == null) {
			keyword = "";
		}
		List<Product> productList = productDao.search("%" + keyword + "%");
		
		req.setAttribute("pro",productList );
		
		return "/admin/search-product";
	}
	@GetMapping("/admin/product/update")
	public String update(@RequestParam(value = "id") long id, Model model) {
		model.addAttribute("pro", productDao.getOne(id));
		List<Category> listCategory = categoryDao.findAll();
		model.addAttribute("categoryList",listCategory );
		return "/admin/update-product";
	}

	// map thang vao model, thuoc tinh model trung ten voi input name
	@PostMapping("/admin/product/update")
	public String update(@ModelAttribute Product pro) {
		productDao.save(pro);
		return "redirect:/product/search";
	}

	@GetMapping("/admin/product/delete")
	public String delete(@RequestParam(value = "id") long id) {
		productDao.deleteById(id);
		return "redirect:/product/search";
	}
}
