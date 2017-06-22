package edu.mum.coffee.webcontroller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mum.coffee.domain.Address;
import edu.mum.coffee.domain.Order;
import edu.mum.coffee.domain.Orderline;
import edu.mum.coffee.domain.Person;
import edu.mum.coffee.domain.Product;
import edu.mum.coffee.repository.OrderLineRepository;
import edu.mum.coffee.repository.OrderRepository;
import edu.mum.coffee.repository.PersonRepository;
import edu.mum.coffee.repository.ProductRepository;
import edu.mum.coffee.service.OrderService;
import edu.mum.coffee.service.PersonService;
import edu.mum.coffee.service.ProductService;


@Controller
public class WebOrderController {
	
    @Autowired
    private ProductService productRepository;

    @Autowired
    private OrderService orderRepository;

    @Autowired
    private PersonService customerRepository;
    
	@Autowired
	private OrderLineRepository orderLineRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String productsList(Model model){
        model.addAttribute("products", productRepository.getAllProduct());
        model.addAttribute("orders", orderRepository.findAll());
        return "orders";
    }
    
    
    @RequestMapping(value="/createorder", method = RequestMethod.POST)
    @ResponseBody
    public String saveOrder(@RequestParam String firstName, @RequestParam String lastName, @RequestParam(value="productIds[]") int[] productIds,
            @RequestParam String email,@RequestParam String phone, @RequestParam(value="quantity") String quantity){

    	// first i am creating person then order
        Person customer = new Person();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(new Address("Neywork","Iowa","USA","52557"));
        customerRepository.savePerson(customer);
        
        Order customerOrder = new Order();
        customerOrder.setPerson(customerRepository.findById(customer.getId()));
        customerOrder.setOrderDate(new Date());
        orderRepository.save(customerOrder);
        
        List<Orderline> orderSet = new ArrayList<>();
        Orderline o = new Orderline();
        o.setQuantity(Integer.parseInt(quantity));
        o.setOrder(orderRepository.findById(customerOrder.getId()));
        o.setProduct(productRepository.getProduct(productIds[0]));
    	
        orderSet.add(o);
        customerOrder.setOrderLines(orderSet);
        orderRepository.save(customerOrder);
        System.out.println("Proooooooooooo "+productIds[0]);

        return String.valueOf(customerOrder.getId());
    }

    @RequestMapping(value = "/removeorder", method = RequestMethod.POST)
    @ResponseBody
    public String removeOrder(@RequestParam int Id){
        orderRepository.delete(orderRepository.findById(Id));
        return String.valueOf(Id);
    }

}
