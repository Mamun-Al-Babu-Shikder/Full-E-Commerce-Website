package com.mcubes.service;

import com.mcubes.entity.CustomerOrder;
import com.mcubes.entity.OrderItem;
import com.mcubes.repository.CartRepository;
import com.mcubes.repository.CustomerOrderRepository;
import com.mcubes.repository.ProductRepository;
import com.mcubes.repository.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

/**
 * Created by A.A.MAMUN on 4/14/2020.
 */
@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;
    @Autowired
    private CustomerOrderRepository customerOrderRepository;
    @Autowired
    private CartRepository cartRepository;

    @Transactional( rollbackFor = {RuntimeException.class})
    public void createOrder(CustomerOrder customerOrder, int e)  {

        /* save customer order */
        customerOrderRepository.save(customerOrder);

        /* create a customer order number */
        customerOrder.setOrderNumber("#"+(char) (65+(int)(Math.random()*10))+(char) (74+(int)(Math.random()*10))
                +(char) (83+(int)(Math.random()*9))+customerOrder.getOrderId());

        /* remove all cart items */
        cartRepository.removeCartItemsByUserEmail(customerOrder.getUserEmail());


        if(e!=0) {
            int a = 1 / 0;
        }

         /*
        write customer payment code here...
         */

        /* update product stock */
        List<OrderItem> orderItems = customerOrder.getOrderItems();
        for(OrderItem item : orderItems)
        {
            if(item.getProductColorId()==0 && item.getProductSizeId()==0){
                /* update at product table */
                updateStockAtProductTable(item.getProductId(), item.getQuantity());
            }else{
                /* update at product_variant table */
                updateStockAtProductVariantTable(item.getProductId(), item.getProductColorId(), item.getProductSizeId(),
                        item.getQuantity());
            }
        }



    }


    private void updateStockAtProductTable(long productId, int quantity) {
        long stock = productRepository.findProductStockByProductId(productId);
        stock = stock - quantity;
        stock = stock<=0 ? 0 : stock;
        productRepository.updateProductStockByProductId(productId, stock);
    }

    private void updateStockAtProductVariantTable(long productId, long colorId, long sizeId, int quantity) {
        int stock = productVariantRepository.findStockByProductIdColorIdAndSizeId(productId, colorId, sizeId);
        stock = stock - quantity;
        stock = stock<=0 ? 0 : stock;
        productVariantRepository.updateStockByProductIdColorIdAndSizeId(productId, colorId, sizeId, stock);
    }




}

