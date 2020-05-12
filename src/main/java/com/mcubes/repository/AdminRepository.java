package com.mcubes.repository;

import com.mcubes.entity.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by A.A.MAMUN on 4/21/2020.
 */
@Transactional
public interface AdminRepository extends CrudRepository<Admin, String>{

    Admin findAdminByEmail(String email);

}
