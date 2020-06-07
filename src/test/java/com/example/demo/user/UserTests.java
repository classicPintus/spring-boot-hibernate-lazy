package com.example.demo.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootTest
@Slf4j
class UserTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    void contextLoads() {

        transactionTemplate.execute(status -> {

            User user = new User();
            user.setName("name");
            user = userRepository.save(user);

            Address address = new Address();
            address.setFullAddress("full Address");
            address.setUser(user);
            addressRepository.save(address);

            // all this code is in the same transaction in order to avoid the select of the user inside the address

            return null;
        });
//         insert into user (id, name, version) values (default, ?, ?)
//         insert into address (full_address, version, id) values (?, ?, ?)

        Address addressFromDb = addressRepository.findAll().get(0);
//         select address0_.id as id1_0_, address0_.full_address as full_add2_0_, address0_.version as version3_0_ from address address0_

        User userFromDb = userRepository.findAll().get(0);
//        select user0_.id as id1_4_, user0_.name as name2_4_, user0_.version as version3_4_ from user user0_
//        select address0_.id as id1_0_0_, address0_.full_address as full_add2_0_0_, address0_.version as version3_0_0_ from address address0_ where address0_.id=?


    }

}
