package com.mcubes.controller;

import com.mcubes.entity.*;
import com.mcubes.repository.*;
import com.mcubes.service.OrderService;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.security.Principal;
import java.util.*;

/**
 * Created by A.A.MAMUN on 4/14/2020.
 */
@Controller
//@RequestMapping("/account")
public class OrderController {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductColorRepository productColorRepository;
    @Autowired
    private ProductSizeRepository productSizeRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;


    @RequestMapping("/order")
    private String loadOrderPage() {
        return "test";
    }

    @ResponseBody
    @RequestMapping("/product-available-validation")
    private Map<String, Object> productAvailableValidation(Principal principal, @RequestParam(defaultValue = "0") int e)
    {
        try {

            List<CartItem> cartItems = cartRepository.findAllByUserEmail(principal.getName());
            List<OrderItem> orderItems = new ArrayList<>();

            for (CartItem cartItem : cartItems) {

                OrderItem orderItem = new OrderItem();
                orderItem.setProductId(cartItem.getProduct_id());

                long productId = cartItem.getProduct_id();
                long stock = 0;
                double actualPrice = 0;
                double price = 0;
                double old_price = 0;
                int quantity = cartItem.getQuantity();
                long colorId = cartItem.getProductColorId();
                long sizeId = cartItem.getProductSizeId();
                String colorName = null;
                String colorCode = null;
                String sizeName = null;

                Product product = productRepository.findProductForOrder(productId);

                if (colorId == 0 && sizeId == 0) {
                    actualPrice = product.getActualPrice();
                    price = product.getPrice();
                    old_price = product.getOld_price();
                    stock = product.getStock();
                } else {

                    ProductColor productColor = productColorRepository.findProductColorByProductColorId(colorId);
                    ProductSize productSize = productSizeRepository.findProductSizeByProductSizeId(sizeId);

                    if (productColor != null) {
                        colorCode = productColor.getColorCode();
                        colorName = productColor.getColor();
                    }

                    if (productSize != null)
                        sizeName = productSize.getSizeName();

                    ProductVariant productVariant = productVariantRepository
                            .findProductVariantForOrder(colorId, sizeId, productId);

                    if(productVariant==null){
                        actualPrice = product.getActualPrice();
                        price = product.getPrice();
                        old_price = product.getOld_price();
                        stock = product.getStock();
                    }else {
                        actualPrice = productVariant.getActualPrice();
                        price = productVariant.getPrice();
                        old_price = productVariant.getOld_price();
                        stock = productVariant.getStock();
                    }
                }


                if (quantity > stock) {
                    quantity = (int) stock;
                }


                orderItem.setProductId(productId);
                orderItem.setProductImage(product.getImage());
                orderItem.setProductName(product.getName());
                orderItem.setActualPrice(actualPrice);
                orderItem.setPrice(price);
                orderItem.setOld_price(old_price);
                orderItem.setQuantity(quantity);
                orderItem.setProductColorId(colorId);
                orderItem.setColorCode(colorCode);
                orderItem.setColorName(colorName);
                orderItem.setProductSizeId(sizeId);
                orderItem.setSizeName(sizeName);

                orderItems.add(orderItem);

            }

            Map<String, Object> map = new HashMap<>();
            map.put("cartItems", cartItems);
            map.put("orderItems", orderItems);




            CustomerOrder customerOrder = new CustomerOrder();

            customerOrder.setDate(new Date().toLocaleString());
            customerOrder.setNote("note here...");
            customerOrder.setOrderItems(orderItems);
            double totalActualPrice = 0;
            double totalPrice = 0;
            double totalOldPrice = 0;
            for (OrderItem orderItem : orderItems) {
                totalActualPrice += orderItem.getActualPrice() * orderItem.getQuantity();
                totalPrice += orderItem.getPrice() * orderItem.getQuantity();
                totalOldPrice += orderItem.getOld_price() * orderItem.getQuantity();
            }
            customerOrder.setTotalActualPrice(totalActualPrice);
            customerOrder.setTotalPrice(totalPrice);
            customerOrder.setTotalOldPrice(totalOldPrice);
            customerOrder.setServiceCharge(0);
            customerOrder.setStatus(CustomerOrder.PENDING);
            customerOrder.setMethod("Cash on delivary");
            customerOrder.setUserName(appUserRepository.findUserFirstNameByEmail(principal.getName()));
            customerOrder.setUserEmail(principal.getName());
            customerOrder.setShippingAddress(new ShippingAddress("Road 5/23 Home - 23", "Tangail",
                    "Bangladesh", "1902","01626311400"));


            orderService.createOrder(customerOrder, e);



            return map;

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return null;

    }



    @ResponseBody
    @RequestMapping("/order-confirm-request")
    private boolean orderConfirmRequest(@RequestParam(name = "items[]") OrderItem[] orderItems)
    {
        try{
            System.out.println("# "+orderItems);
            //orderItems.stream().forEach(integer -> System.out.println(integer));
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }




}
