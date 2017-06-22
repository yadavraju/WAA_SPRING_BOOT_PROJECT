package edu.mum.coffee.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mum.coffee.domain.Product;
import edu.mum.coffee.repository.ProductRepository;
import edu.mum.coffee.service.ProductService;

@Controller
public class WebProductController {
	
	 @Autowired
	    ProductService productRepository;


	    @RequestMapping("/product/{id}")
	    public String product(@PathVariable int id, Model model){
	        model.addAttribute("product", productRepository.getProduct(id));
	        return "product";
	    }

	    @RequestMapping(value = "/products",method = RequestMethod.GET)
	    public String productsList(Model model){
	        model.addAttribute("products", productRepository.getAllProduct());
	        return "products";
	    }

	    @RequestMapping(value = "/saveproduct", method = RequestMethod.POST)
	    @ResponseBody
	    public String saveProduct(@RequestBody Product product) {
	        productRepository.save(product);
	        return String.valueOf(product.getId());
	    }

	    @RequestMapping(value = "/removeproduct", method = RequestMethod.POST)
	    @ResponseBody
	    public String removeOrder(@RequestParam int Id){
	    	productRepository.delete(productRepository.getProduct(Id));
	        return String.valueOf(Id);
	    }

}
