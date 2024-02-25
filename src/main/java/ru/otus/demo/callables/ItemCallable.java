package ru.otus.demo.callables;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import ru.otus.demo.persistence.entitites.Item;
import ru.otus.demo.persistence.repositories.ItemRepository;

import java.util.List;
import java.util.concurrent.Callable;

@Component
@RequiredArgsConstructor
public class ItemCallable implements Callable<List<Item>> {

    private final ItemRepository itemRepository;


    @Override
    public List<Item> call() {
        return itemRepository.findAll();
    }

}