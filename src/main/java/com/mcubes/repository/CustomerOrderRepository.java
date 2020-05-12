package com.mcubes.repository;

import com.mcubes.entity.CustomerOrder;
import com.mcubes.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by A.A.MAMUN on 3/30/2020.
 */
@Transactional
public interface CustomerOrderRepository extends CrudRepository<CustomerOrder, Long> {

//select * from customer_order , order_item WHERE customer_order.order_id = order_item.order_id and customer_order.status = 'Pending' and order_item.product_id=8
    @Query("select count(c) from CustomerOrder c, OrderItem o where  c.status = '"+CustomerOrder.PENDING+"' and o.productId=?1")
    int findCustomerOrderByProductId(long product_id);

    @Query("select o.status from CustomerOrder o where o.orderId=?1")
    String findCustomerOrderStatusByOrderId(long orderId);

    @Query("select o from CustomerOrder o where (o.orderNumber like %?1% or o.date like %?1% or o.userName like %?1% or o.userEmail like %?1%) and o.status like ?2% order by o.orderId desc")
    Page<CustomerOrder> findPageableCustomerOrder(String search, String status, Pageable pageable);

    @Query("select new CustomerOrder(o.orderId, o.orderNumber, o.date, o.status, o.totalPrice, o.totalOldPrice, o.serviceCharge) from CustomerOrder o where o.userEmail=?1 and o.status like ?2% order by o.orderId desc ")
    Page<CustomerOrder> findCustomerOrderForCustomerView(String userEmail, String status , Pageable pageable);

    CustomerOrder findCustomerOrderByOrderId(long orderId);

    CustomerOrder findCustomerOrderByUserEmailAndOrderId(String userEmail, long orderId);

    int countCustomerOrderByUserEmail(String email);

}

