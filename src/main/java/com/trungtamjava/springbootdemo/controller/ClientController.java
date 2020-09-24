package com.trungtamjava.springbootdemo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.trungtamjava.springbootdemo.dao.ProductDao;
import com.trungtamjava.springbootdemo.entity.BillProduct;
import com.trungtamjava.springbootdemo.entity.Product;

@Controller
public class ClientController {
	@Autowired
	private ProductDao productDao;
	@GetMapping("/products")
	public String getAll(Model model) {
		model.addAttribute("pro", productDao.findAll());
		return "/client/products";
	}
	
	@GetMapping("member/cart/addtocart/{id}")
	public String addToCart(@PathVariable("id") long id,ModelMap modelMap,HttpSession session) {
		Product product = productDao.getOne(id);

		Object object = session.getAttribute("cart");
		if (object == null) {
			BillProduct billProduct = new BillProduct();
			billProduct.setProduct(product);
			billProduct.setQuantity(1);
			billProduct.setUnitPrice(product.getPrice());
			Map<Long, BillProduct> map = new HashMap<>();
			map.put(id, billProduct);
			session.setAttribute("cart", map);
		} else {
			Map<Long, BillProduct> map = (Map<Long, BillProduct>) object;
			BillProduct billProduct = map.get(id);
			if (billProduct == null) {
				billProduct = new BillProduct();
				billProduct.setProduct(product);
				billProduct.setQuantity(1);
				billProduct.setUnitPrice(product.getPrice());
				map.put(id, billProduct);
			} else {
				billProduct.setQuantity(billProduct.getQuantity() + 1);

			}
			session.setAttribute("cart", map);

		}
		return "client/cart";
	}
	

	@GetMapping("/delete-from-cart")
	public String Deletefromtocart(HttpServletRequest req, @RequestParam(name = "key", required = true) Long key) {
		HttpSession session = req.getSession();
		Object object = session.getAttribute("cart");
		if (object != null) {
			Map<Long, BillProduct> map = (Map<Long, BillProduct>) object;
			map.remove(key);
			session.setAttribute("cart", map);
		}
		return "redirect:/cart";
	}
	@GetMapping(value = "/cart")
	public String cart() {
		return "client/cart";
	}

}
