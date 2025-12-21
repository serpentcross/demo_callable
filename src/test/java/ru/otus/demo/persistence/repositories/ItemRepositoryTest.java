package ru.otus.demo.persistence.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;


@DataJpaTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;



}