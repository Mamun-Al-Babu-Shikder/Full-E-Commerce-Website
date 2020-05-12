package com.mcubes.repository;

import com.mcubes.entity.AppUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by A.A.MAMUN on 2/4/2020.
 */
@Transactional
public interface AppUserRepository extends CrudRepository<AppUser, String>{

    @Query("select new AppUser(a.email, a.password, a.role) from AppUser a where email=?1")
    AppUser findAppUserByEmailAddress(String email);

    int countByEmail(String email);

    @Query("select u.first_name from AppUser u where email=?1")
    String findUserFirstNameByEmail(String email);

    @Query("select new AppUser(u.first_name, u.last_name) from AppUser u where email=?1")
    AppUser findUserFullNameOnlyByEmail(String email);

    @Transactional
    @Modifying
    @Query("update AppUser u set u.first_name=?1, u.last_name=?2, u.phone=?3, u.dob=?4, u.gender=?5, u.address=?6," +
            "u.company=?7, u.city=?8, u.postal_code=?9, u.facebookProfileLink=?10, u.twitterProfileLink=?11," +
            "u.instagramProfileLink=?12 where u.email=?13")
    void updateAppUserInformationByEmail(String first_name, String last_name, String phone, String dob, String gender,
                                         String address, String company, String city, String postal_code, String facebook,
                                         String twitter, String instagram, String email);

    @Query("select u.password from AppUser u where u.email=?1")
    String findAppUserPasswordByEmail(String email);

    @Transactional
    @Modifying
    @Query("update AppUser u set u.password=?1 where u.email=?2")
    void updateAppUserPasswordByEmail(String newPassword, String email);
}
