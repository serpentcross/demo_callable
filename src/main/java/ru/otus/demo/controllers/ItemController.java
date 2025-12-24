package ru.otus.demo.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ru.otus.demo.converters.ItemConverter;
import ru.otus.demo.dtos.ItemDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ItemController implements ItemsApi {

    private final ItemConverter itemConverter;

    @Override
    public ResponseEntity<List<ItemDto>> allItems() {
        return new ResponseEntity<>(itemConverter.getAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ItemDto> createItem(final String name, final Boolean available) {
        return new ResponseEntity<>(itemConverter.createOne(name, available), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteItem(final UUID id) {
        itemConverter.deleteOne(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<ItemDto> oneItem(final UUID id) {
        return new ResponseEntity<>(itemConverter.getOne(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ItemDto> updateItem(final UUID id, final ItemDto itemDto) {
        return new ResponseEntity<>(itemConverter.updateOne(id, itemDto), HttpStatus.OK);
    }

}