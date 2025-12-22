package ru.otus.demo.converters;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import ru.otus.demo.dtos.ItemDto;
import ru.otus.demo.mappers.ItemMapper;
import ru.otus.demo.services.ItemService;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ItemConverter {

    private final ItemMapper itemMapper;
    private final ItemService itemService;

    public List<ItemDto> getAll() {
        return itemMapper.toDtoList(itemService.getAll());
    }

    public ItemDto getOne(final UUID id) {
        return itemMapper.toDto(itemService.getOne(id));
    }

    public ItemDto createOne(final String name, final boolean available) {
        return itemMapper.toDto(itemService.createOne(name, available));
    }

    public ItemDto updateOne(final UUID id, final ItemDto itemDto) {
        return itemMapper.toDto(itemService.updateOne(id, itemDto));
    }

    public void deleteOne(final UUID id) {
        itemService.deleteOne(id);
    }

}