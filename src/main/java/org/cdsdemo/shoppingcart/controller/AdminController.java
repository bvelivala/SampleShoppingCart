package org.cdsdemo.shoppingcart.controller;


import java.util.List;

import org.cdsdemo.shoppingcart.dao.OrderDAO;
import org.cdsdemo.shoppingcart.dao.ProductDAO;
import org.cdsdemo.shoppingcart.model.OrderDetailInfo;
import org.cdsdemo.shoppingcart.model.OrderInfo;
import org.cdsdemo.shoppingcart.model.PaginationResult;
import org.cdsdemo.shoppingcart.model.ProductInfo;
import org.cdsdemo.shoppingcart.validator.ProductInfoValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need to use RedirectAttributes
@EnableWebMvc
public class AdminController {
	
   private static Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

   @Autowired
   private OrderDAO orderDAO;

   @Autowired
   private ProductDAO productDAO;

   @Autowired
   private ProductInfoValidator productInfoValidator;

   // Configurated In ApplicationContextConfig.
   @Autowired
   private ResourceBundleMessageSource messageSource;

   @InitBinder
   public void myInitBinder(WebDataBinder dataBinder) {
       Object target = dataBinder.getTarget();
       if (target == null) {
           return;
       }
       LOGGER.info("Target=" + target);

       if (target.getClass() == ProductInfo.class) {
           dataBinder.setValidator(productInfoValidator);
           // For upload Image.
           dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
       }
   }

   // GET: Show Login Page
   @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
   public String login(Model model) {

       return "login";
   }

   @RequestMapping(value = { "/accountInfo" }, method = RequestMethod.GET)
   public String accountInfo(Model model) {

       UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       LOGGER.info("User password is : "+userDetails.getPassword());
       LOGGER.info("User name is     : "+userDetails.getUsername());
       LOGGER.info("User active      : "+userDetails.isEnabled());

       model.addAttribute("userDetails", userDetails);
       return "accountInfo";
   }

   @RequestMapping(value = { "/orderList" }, method = RequestMethod.GET)
   public String orderList(Model model, //
           @RequestParam(value = "page", defaultValue = "1") String pageStr) {
       int page = 1;
       try {
           page = Integer.parseInt(pageStr);
       } catch (Exception e) {
    	   LOGGER.error("Error while retrieving the order list due to : ", e);
    	   String message = e.getMessage();
           model.addAttribute("message", message);
       }
       final int MAX_RESULT = 5;
       final int MAX_NAVIGATION_PAGE = 10;

       PaginationResult<OrderInfo> paginationResult = orderDAO.listOrderInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);

       model.addAttribute("paginationResult", paginationResult);
       return "orderList";
   }

   // GET: Show product.
   @RequestMapping(value = { "/product" }, method = RequestMethod.GET)
   public String product(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
       ProductInfo productInfo = null;

       if (code != null && code.length() > 0) {
           productInfo = productDAO.findProductInfo(code);
       }
       if (productInfo == null) {
           productInfo = new ProductInfo();
           productInfo.setNewProduct(true);
       }
       model.addAttribute("productForm", productInfo);
       return "product";
   }

   // POST: Save product
   @RequestMapping(value = { "/product" }, method = RequestMethod.POST)
   // Avoid UnexpectedRollbackException (See more explanations)
   @Transactional(propagation = Propagation.NEVER)
   public String productSave(Model model, //
           @ModelAttribute("productForm") @Validated ProductInfo productInfo, //
           BindingResult result, //
           final RedirectAttributes redirectAttributes) {

       if (result.hasErrors()) {
           return "product";
       }
       try {
           productDAO.save(productInfo);
       } catch (Exception e) {
           // Need: Propagation.NEVER?
           String message = e.getMessage();
           model.addAttribute("message", message);
           LOGGER.error("Error while saving the product due to :: ", e);
           // Show product form.
           return "product";

       }
       return "redirect:/productList";
   }

   @RequestMapping(value = { "/order" }, method = RequestMethod.GET)
   public String orderView(Model model, @RequestParam("orderId") String orderId) {
       OrderInfo orderInfo = null;
       if (orderId != null) {
           orderInfo = this.orderDAO.getOrderInfo(orderId);
       }
       if (orderInfo == null) {
           return "redirect:/orderList";
       }
       List<OrderDetailInfo> details = this.orderDAO.listOrderDetailInfos(orderId);
       orderInfo.setDetails(details);

       model.addAttribute("orderInfo", orderInfo);

       return "order";
   }
   
}
