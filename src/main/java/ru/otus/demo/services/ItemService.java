package ru.otus.demo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.demo.callables.ItemCallable;
import ru.otus.demo.dtos.ItemDto;
import ru.otus.demo.exceptions.Code404ItemNotFoundException;
import ru.otus.demo.persistence.entitites.Item;
import ru.otus.demo.persistence.repositories.ItemRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemCallable itemCallable;
    private final ItemRepository itemRepository;

    public List<Item> getAll() {
        log.info("START: {} ",System.currentTimeMillis() / 1000);
        List<Item> items = itemRepository.findAll();
        log.info("STOP: {} ",System.currentTimeMillis() / 1000);
        return items;
    }

    public List<Item> getAllFast() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        
        List<Item> items = new ArrayList<>();

        List<Future<List<Item>>> futureList = new ArrayList<>();

        log.info("START fast: {} ",System.currentTimeMillis() / 1000);

        Future<List<Item>> submit = executorService.submit(itemCallable);
        futureList.add(submit);

        for (Future<List<Item>> futureItem : futureList) {
            items.addAll(futureItem.get());
        }

        executorService.shutdown();

        log.info("STOP fast: {} ",System.currentTimeMillis() / 1000);

        return items;

    }

    public List<Item> getAllIn(List<UUID> ids) {
        return itemRepository.findAllById(ids);
    }

    public Item getOne(UUID id) {
        return itemRepository.findById(id).orElseThrow(() -> new Code404ItemNotFoundException(Item.class, id));
    }

    public Item createOne(String name, boolean available) {
        return mergeEntityResult(Item.builder().name(name).available(available).added(LocalDate.now()).build());
    }

    @Transactional
    public Item updateOne(UUID id, ItemDto itemDto) {
        Item old = getOne(id);
        old.setName(itemDto.getName());
        old.setAvailable(itemDto.isAvailable());
        return mergeEntityResult(old);
    }

    public void deleteOne(UUID id) {
        itemRepository.deleteById(id);
    }

    private Item mergeEntityResult(Item item) {
        return itemRepository.save(item);
    }

}