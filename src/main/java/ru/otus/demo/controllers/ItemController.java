package ru.otus.demo.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ru.otus.demo.converters.ItemConverter;
import ru.otus.demo.dtos.ItemDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemConverter itemConverter;

    @GetMapping("/{id}")
    public ItemDto one(@PathVariable UUID id) {
        return itemConverter.getOne(id);
    }

    @GetMapping
    public List<ItemDto> all() {
        return itemConverter.getAll();
    }

    @GetMapping("/fast")
    public List<ItemDto> allFast() throws ExecutionException, InterruptedException {
        return itemConverter.getAllFast();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto create(@RequestParam String name, @RequestParam boolean available) {
        return itemConverter.createOne(name, available);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        itemConverter.deleteOne(id);
    }

    @PutMapping("/{id}")
    public ItemDto update(@PathVariable UUID id, @RequestBody ItemDto itemDto) {
        return itemConverter.updateOne(id, itemDto);
    }

}