package ru.otus.demo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.demo.dtos.ItemDto;
import ru.otus.demo.exceptions.Code404ItemNotFoundException;
import ru.otus.demo.persistence.entitites.Item;
import ru.otus.demo.persistence.repositories.ItemRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public Item getOne(final UUID id) {
        return itemRepository.findById(id).orElseThrow(() -> new Code404ItemNotFoundException(Item.class, id));
    }

    public Item createOne(final String name, final boolean available) {
        return mergeEntityResult(Item.builder().name(name).available(available).added(LocalDate.now()).build());
    }

    @Transactional
    public Item updateOne(final UUID id, final ItemDto itemDto) {
        Item old = getOne(id);
        old.setName(itemDto.getName());
        old.setAvailable(itemDto.isAvailable());
        return mergeEntityResult(old);
    }

    public void deleteOne(final UUID id) {
        itemRepository.deleteById(id);
    }

    private Item mergeEntityResult(final Item item) {
        return itemRepository.save(item);
    }

}