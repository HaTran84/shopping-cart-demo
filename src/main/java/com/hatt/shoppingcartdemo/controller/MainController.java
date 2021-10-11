package com.hatt.shoppingcartdemo.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hatt.shoppingcartdemo.entity.Product;
import com.hatt.shoppingcartdemo.form.CustomerForm;
import com.hatt.shoppingcartdemo.form.SearchForm;
import com.hatt.shoppingcartdemo.model.*;
import com.hatt.shoppingcartdemo.pagination.PaginationResult;
import com.hatt.shoppingcartdemo.service.OrderService;
import com.hatt.shoppingcartdemo.service.ProductService;
import com.hatt.shoppingcartdemo.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MainController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;


//    @Autowired
//    private CustomerFormValidator customerFormValidator;


    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        log.info("Target=" + target);

        // Case update quantity in cart
        // (@ModelAttribute("cartForm") @Validated CartInfo cartForm)
        if (target.getClass() == CartInfo.class) {

        }

        // Case save customer information.
        // (@ModelAttribute @Validated CustomerInfo customerForm)
        else if (target.getClass() == CustomerForm.class) {
//            dataBinder.setValidator(customerFormValidator);
        }

    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "/403";
    }

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    // Product List
    @RequestMapping({ "/productList" })
    public String listProductHandler(Model model,
                                     @RequestParam(value = "page", defaultValue = "1") int page,
                                     @RequestParam(value = "catagory", defaultValue = "") String catagory,
                                     @RequestParam(value = "brand", defaultValue = "0") Integer brand) {
        final int maxResult = 5;
        final int maxNavigationPage = 10;
        model.addAttribute("catagoryURL", catagory);
        initDataCommon(model);
        SearchProductInfo searchProductInfo = convertParameterModelSearch(page, catagory, brand, maxResult, maxNavigationPage);

        PaginationResult<ProductInfo> result = productService.readProducts(searchProductInfo);

        model.addAttribute("paginationProducts", result);
        return "productList";
    }


    @RequestMapping(value = {"/searchProductList"}, method = RequestMethod.GET)
    public String searchProductList(HttpServletRequest request, //
                                    Model model) {
        initDataCommon(model);
        model.addAttribute("searchForm", new SearchForm());
        model.addAttribute("listNoData", new ArrayList<ProductInfo>());

        return "searchProductList";
    }


    // POST: Save customer information.
    @RequestMapping(value = {"/searchProductList"}, method = RequestMethod.POST)
    public String searchProductList(HttpServletRequest request, //
                                    Model model, //
                                    @ModelAttribute("searchForm") @Validated SearchForm searchForm,
                                    BindingResult result) {
        if (result.hasErrors()) {
            searchForm.setValid(false);
            return "productList";
        }
        initDataCommon(model);
        searchForm.setValid(true);
        SearchProductInfo  searchProductInfo = convert(searchForm);
        PaginationResult<ProductInfo> products = productService.readProducts(searchProductInfo);

        model.addAttribute("paginationProducts", products);
        model.addAttribute("searchForm", searchForm);
        return "searchProductList";
    }


    private void initDataCommon(Model model) {
        // should use cache
        model.addAttribute("catagories", productService.getCatagories());
        model.addAttribute("brands", productService.getBrands());
        model.addAttribute("colours", productService.getColours());
    }

    @RequestMapping({ "/buyProduct" })
    public String listProductHandler(HttpServletRequest request, Model model, //
                                     @RequestParam(value = "productId", defaultValue = "0") int productId,
                                     @RequestParam(value = "colourId", required = false) Integer colourId) {
        CartInfo cartInfo = Utils.getCartInSession(request);
        productService.addProduct(cartInfo, productId, colourId);
        return "redirect:/shoppingCart";
    }

    // remove
    @RequestMapping({ "/shoppingCartRemoveProduct" })
    public String removeProductHandler(HttpServletRequest request, Model model, //
                                       @RequestParam(value = "productId", defaultValue = "0") int productId,
                                       @RequestParam(value = "colourId", required = false) Integer colourId) {
        CartInfo cartInfo = Utils.getCartInSession(request);
        productService.removeProduct(cartInfo, productId, colourId);
        return "redirect:/shoppingCart";
    }

    // POST: Update quantity for product in cart
    @RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.POST)
    public String shoppingCartUpdateQty(HttpServletRequest request, //
                                        Model model, //
                                        @ModelAttribute("cartForm") CartInfo cartForm) {

        CartInfo cartInfo = Utils.getCartInSession(request);
        cartInfo.updateQuantity(cartForm);

        return "redirect:/shoppingCart";
    }

    // GET: Show cart.
    @RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.GET)
    public String shoppingCartHandler(HttpServletRequest request, Model model) {
        CartInfo myCart = Utils.getCartInSession(request);
        model.addAttribute("cartForm", myCart);
        return "shoppingCart";
    }

    // GET: Enter customer information.
    @RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.GET)
    public String shoppingCartCustomerForm(HttpServletRequest request, Model model) {
        CartInfo cartInfo = Utils.getCartInSession(request);
        if (cartInfo.isEmpty()) {
            return "redirect:/shoppingCart";
        }
        CustomerInfo customerInfo = cartInfo.getCustomerInfo();
        CustomerForm customerForm = new CustomerForm(customerInfo);
        model.addAttribute("customerForm", customerForm);
        return "shoppingCartCustomer";
    }

    // POST: Save customer information.
    @RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.POST)
    public String shoppingCartCustomerSave(HttpServletRequest request, //
                                           Model model, //
                                           @ModelAttribute("customerForm") @Validated CustomerForm customerForm, //
                                           BindingResult result, //
                                           final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            customerForm.setValid(false);
            // Forward to reenter customer info.
            return "shoppingCartCustomer";
        }

        customerForm.setValid(true);
        CartInfo cartInfo = Utils.getCartInSession(request);
        CustomerInfo customerInfo = new CustomerInfo(customerForm);
        cartInfo.setCustomerInfo(customerInfo);

        return "redirect:/shoppingCartConfirmation";
    }

    // GET: Show information to confirm.
    @RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.GET)
    public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
        CartInfo cartInfo = Utils.getCartInSession(request);
        if (cartInfo == null || cartInfo.isEmpty()) {
            return "redirect:/shoppingCart";
        } else if (!cartInfo.isValidCustomer()) {
            return "redirect:/shoppingCartCustomer";
        }
        model.addAttribute("myCart", cartInfo);
        return "shoppingCartConfirmation";
    }

    // POST: Submit Cart (Save)
    @RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.POST)
    public String shoppingCartConfirmationSave(HttpServletRequest request, Model model) {
        CartInfo cartInfo = Utils.getCartInSession(request);
        if (cartInfo.isEmpty()) {
            return "redirect:/shoppingCart";
        } else if (!cartInfo.isValidCustomer()) {
            return "redirect:/shoppingCartCustomer";
        }
        try {
            orderService.saveOrder(cartInfo);
        } catch (Exception e) {
            return "shoppingCartConfirmation";
        }

        // Remove Cart from Session.
        Utils.removeCartInSession(request);

        // Store last cart.
        Utils.storeLastOrderedCartInSession(request, cartInfo);

        return "redirect:/shoppingCartFinalize";
    }

    @RequestMapping(value = { "/shoppingCartFinalize" }, method = RequestMethod.GET)
    public String shoppingCartFinalize(HttpServletRequest request, Model model) {
        CartInfo lastOrderedCart = Utils.getLastOrderedCartInSession(request);
        if (lastOrderedCart == null) {
            return "redirect:/shoppingCart";
        }
        model.addAttribute("lastOrderedCart", lastOrderedCart);
        return "shoppingCartFinalize";
    }

    @RequestMapping(value = { "/productImage" }, method = RequestMethod.GET)
    public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
                             @RequestParam("productId") int productId) throws IOException {
        Product product = productService.findProduct(productId);
        if (product != null && product.getImage() != null) {
            response.setContentType("image/jpeg" +","+" image/jpg, image/png, image/gif");
            response.getOutputStream().write(product.getImage());
        }
        response.getOutputStream().close();
    }

    private SearchProductInfo convertParameterModelSearch(int page, String catagory, Integer brand, int maxResult, int maxNavigationPage) {
        SearchProductInfo searchProductInfo = new SearchProductInfo(page, maxResult, maxNavigationPage,
                null, catagory == null || catagory.equals("") ? null : Arrays.asList(catagory),
                brand == null || brand <= 0 ? null : Arrays.asList(brand),
                null,null, null);
        return searchProductInfo;
    }

    private SearchProductInfo convert(SearchForm searchForm) {
        final int maxResult = 5;
        final int maxNavigationPage = 10;
        return new SearchProductInfo((searchForm.getPageNumber() != null && searchForm.getPageNumber() > 0) ? searchForm.getPageNumber() : 1, //
                maxResult, maxNavigationPage, searchForm.getName(),
                (searchForm.getCatIdList() != null && searchForm.getCatIdList().length > 0) ? Arrays.asList(searchForm.getCatIdList()):null,
                (searchForm.getBrandIdList() != null && searchForm.getBrandIdList().length > 0) ?Arrays.asList(searchForm.getBrandIdList()) : null,
                (searchForm.getColourIdList() != null && searchForm.getColourIdList().length > 0) ?Arrays.asList(searchForm.getColourIdList()):null,
                searchForm.getMinPrice(), searchForm.getMaxPrice());
    }
}
