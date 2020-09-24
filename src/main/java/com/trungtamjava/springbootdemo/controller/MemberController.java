package com.trungtamjava.springbootdemo.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trungtamjava.springbootdemo.dao.BillDao;
import com.trungtamjava.springbootdemo.dao.BillProductDao;
import com.trungtamjava.springbootdemo.dao.ProductDao;
import com.trungtamjava.springbootdemo.dao.UserDao;
import com.trungtamjava.springbootdemo.entity.Bill;
import com.trungtamjava.springbootdemo.entity.BillProduct;
import com.trungtamjava.springbootdemo.entity.Product;
import com.trungtamjava.springbootdemo.entity.User;
import com.trungtamjava.springbootdemo.model.UserPrincipal;


@Controller
public class MemberController {
	@Autowired
	private BillProductDao billProductDao;
	@Autowired
	private BillDao billDao;
	@Autowired 
	private ProductDao productDao;
	
	@Autowired
	private UserDao userDao;
	
	@GetMapping("/member/home")
	public String home() {
		return "member/home";
	}
	
	@GetMapping(value = "/member/bill/add")
	public String addOrder(HttpSession session,
			Model model) throws IOException {
		// lay member dang nhap hien tai
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		User user = new User();
		user.setId(principal.getId());

		// lay sp trong gio hang
		Object object = session.getAttribute("cart");

		if (object != null) {
			Map<String, BillProduct> map = (Map<String, BillProduct>) object;

			Bill bill = new Bill();
			bill.setBuyer(userDao.get(principal.getId()));
			bill.setStatus("Chua xac nhan");
			bill.setBuyDate(new Date());
			bill.setPay("COD");
			billDao.save(bill);

			long totalPrice = 0L;
			long finalTotalPrice = 0L;
			for (Entry<String, BillProduct> entry : map.entrySet()){
				BillProduct billProduct = entry.getValue();
				billProduct.setBill(bill);

				billProductDao.save(billProduct);

				 //update so luong sp sau khi mua hang thanh cong.
				Product product = productDao.get(entry.getValue().getProduct().getId());
				product.setSoLuong(product.getSoLuong() - billProduct.getQuantity());
				productDao.save(product);
				
				totalPrice = totalPrice + (billProduct.getQuantity() * billProduct.getUnitPrice());
			}
			List<Bill> list = billDao.findAll(); // search trong bang
			
			// if (list.isEmpty() == true ) {// lan dau mua
			if (list.size() == 1) { // lan dau mua
				finalTotalPrice = (totalPrice - (totalPrice * 5 / 100));
				bill.setPriceTotal(finalTotalPrice);
				bill.setDiscountPercent(5);
			} else {
				bill.setPriceTotal(totalPrice);
				bill.setDiscountPercent(0);
			}
			billDao.save(bill);

			session.removeAttribute("cart");

			return "redirect:/member/bills";
		}
		return "redirect:/products";

	}
	long billId;
	@GetMapping(value = "/member/bills")
	public String bills(HttpServletRequest request) {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
		
		List<Bill> listBill = billDao.searchById(principal.getId(), firstPageWithTwoElements); // search trong bang bill
		request.setAttribute("bills", listBill);// danh sach bill cua 1 client
		return "member/bills";
	}
	@GetMapping(value = "/member/delete/bills")
	public String deleteBillsProduct(@RequestParam(name = "billId", required = true) Long billId) {
		billDao.deleteById(billId);
		return "redirect:/member/bills";
	}
	@GetMapping(value = "/member/bill")
	public String billDetail(Model request, @RequestParam(name = "billId", required = true) Long billId) {
//		Optional<BillProduct> listBillProduct =  billProductDao.findById(billId);
//		if (listBillProduct.isPresent()) {
//			request.addAttribute("billProducts", listBillProduct.get());
//		}
		this.billId = billId;
		List<BillProduct> listBllProduct = billProductDao.searchbyBill(billId);
		request.addAttribute("billProducts", listBllProduct);
		request.addAttribute("billId", billId);

		return "member/bill";
	}
	@GetMapping(value = "/member/delete/bill")
	public String deleteBillProduct(@RequestParam(name = "billProductId", required = true) Long billProductId, Model model) {
		billProductDao.deleteById(billProductId);
		return "redirect:/member/bill?billId="+ this.billId;
	}
}
