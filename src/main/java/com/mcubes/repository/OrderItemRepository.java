package com.mcubes.repository;

import com.mcubes.entity.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by A.A.MAMUN on 3/30/2020.
 */
@Transactional
public interface OrderItemRepository extends CrudRepository<OrderItem, Long>{
}
