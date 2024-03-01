package com.seowon.storereservationsystem.repository;

import com.seowon.storereservationsystem.entity.Owner;
import com.seowon.storereservationsystem.entity.Store;
import com.seowon.storereservationsystem.type.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

//@SpringBootTest
class StoreRepositoryTest {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    OwnerRepository ownerRepository;

    @Test
    void relationshipTest1() {
        // given
        Owner owner = Owner.builder()
                .ownerId("zerobase@naver.com")
                .name("서원")
                .phone("01012223333")
                .password("password")
                .role(Role.OWNER)
                .build();

        Store store = Store.builder()
                .storeName("제로베이스 카페")
                .storeLocation("서울")
                .build();

        // when
        ownerRepository.save(owner);
        storeRepository.save(store);

        // then
        System.out.println("store : " + storeRepository.findById(1L).get());
        System.out.println("owner : " + ownerRepository.findById(1L).get());
    }

    @Test
    void relationshipTest2() {
        // given
        Owner owner = Owner.builder()
                .ownerId("zerobase@naver.com")
                .name("서원")
                .phone("01012223333")
                .password("password")
                .role(Role.OWNER)
                .build();

        Store store1 = Store.builder()
                .storeName("제로베이스 카페1")
                .storeLocation("서울")
                .owner(owner)
                .build();

        Store store2 = Store.builder()
                .storeName("제로베이스 카페2")
                .storePhoneNumber("01012345678")
                .storeLocation("서울")
                .owner(owner)
                .build();

        Store store3 = Store.builder()
                .storeName("제로베이스 카페3")
                .storePhoneNumber("01012345678")
                .storeLocation("서울")
                .owner(owner)
                .build();

        // when
        ownerRepository.save(owner);
        storeRepository.save(store1);
        storeRepository.save(store2);
        storeRepository.save(store3);

        // then
        List<Store> storeList = ownerRepository.findById(owner.getId())
                .get().getStoreList();

        for (Store store : storeList) {
            System.out.println(store.getStoreName());
        }
    }

}