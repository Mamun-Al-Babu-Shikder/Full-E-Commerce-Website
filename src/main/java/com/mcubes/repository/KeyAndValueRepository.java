package com.mcubes.repository;

import com.mcubes.entity.KeyAndValue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by A.A.MAMUN on 4/5/2020.
 */
@Transactional
public interface KeyAndValueRepository extends CrudRepository<KeyAndValue, String> {

    @Query("select kv.value from KeyAndValue kv where kv.keyId=?1")
    String findValueByKey(String keys);
}
