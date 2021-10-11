package com.hatt.shoppingcartdemo.controller;

import java.util.List;
import java.util.Map;

import com.hatt.shoppingcartdemo.dao.OrderDAO;
import com.hatt.shoppingcartdemo.entity.Product;
import com.hatt.shoppingcartdemo.form.ProductForm;
import com.hatt.shoppingcartdemo.model.ColourInfo;
import com.hatt.shoppingcartdemo.model.OrderDetailInfo;
import com.hatt.shoppingcartdemo.model.OrderInfo;
import com.hatt.shoppingcartdemo.pagination.PaginationResult;
import com.hatt.shoppingcartdemo.service.OrderService;
import com.hatt.shoppingcartdemo.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class AdminController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

//    @Autowired
//    private ProductFormValidator productFormValidator;

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == ProductForm.class) {
//            dataBinder.setValidator(productFormValidator);
        }
    }

    // GET: Show Login Page
    @RequestMapping(value = { "/admin/login" }, method = RequestMethod.GET)
    public String login(Model model) {

        return "login";
    }

    @RequestMapping(value = { "/admin/accountInfo" }, method = RequestMethod.GET)
    public String accountInfo(Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(userDetails.getPassword());
        log.info(userDetails.getUsername());
        log.info(String.valueOf(userDetails.isEnabled()));

        model.addAttribute("userDetails", userDetails);
        return "accountInfo";
    }

    @RequestMapping(value = { "/admin/orderList" }, method = RequestMethod.GET)
    public String orderList(Model model, //
                            @RequestParam(value = "page", defaultValue = "1") String pageStr) {
        int page = 1;
        try {
            page = Integer.parseInt(pageStr);
        } catch (Exception e) {
            log.error("orderList have the problem " + e.getMessage());
        }
        PaginationResult<OrderInfo> paginationResult = orderService.readOrders(page);
        model.addAttribute("paginationResult", paginationResult);
        return "orderList";
    }

    // GET: Show product.
    @RequestMapping(value = { "/admin/product" }, method = RequestMethod.GET)
    public String product(Model model, @RequestParam(value = "productId", defaultValue = "0") int productId) {
        ProductForm productForm = null;

        if (productId > 0) {
            Product product = productService.findProduct(productId);
            if (product != null) {
                productForm = new ProductForm(product);
            }
        }
        if (productForm == null) {
            productForm = new ProductForm();
            productForm.setNewProduct(true);
        }
        model.addAttribute("productForm", productForm);
        return "product";
    }

    // POST: Save product
    @RequestMapping(value = { "/admin/product" }, method = RequestMethod.POST)
    public String productSave(Model model, //
                              @ModelAttribute("productForm") @Validated ProductForm productForm, //
                              BindingResult result, //
                              final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "product";
        }
        try {
            productService.saveProduct(productForm);
        } catch (Exception e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            String message = rootCause.getMessage();
            model.addAttribute("errorMessage", message);
            // Show product form.
            return "product";
        }

        return "redirect:/productList";
    }

    @RequestMapping(value = { "/admin/order" }, method = RequestMethod.GET)
    public String orderView(Model model, @RequestParam("orderId") String orderId) {
        OrderInfo orderInfo = null;
        if (orderId != null) {
            orderInfo = this.orderService.readOrder(orderId);
        }
        if (orderInfo == null) {
            return "redirect:/admin/orderList";
        }
        List<OrderDetailInfo> details = this.orderService.readOrderDetails(orderId);
        // I'll do it on DB better than here. Have time
        for (OrderDetailInfo item : details)
        {
            item.setColourName(getColourName(item.getColourId()));
        }
        orderInfo.setDetails(details);

        model.addAttribute("orderInfo", orderInfo);

        return "order";
    }

    public String getColourName(Integer colourId) {
        Map<Integer, ColourInfo> colourInfoMap = productService.getColourInfoMap();
        return colourInfoMap.get(colourId) != null ? colourInfoMap.get(colourId).getName() : null;
    }
}
