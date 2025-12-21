package ru.otus.demo.mappers;

import org.mapstruct.Mapper;

import ru.otus.demo.dtos.ItemDto;
import ru.otus.demo.persistence.entitites.Item;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    Item toEntity(final ItemDto itemDto);
    ItemDto toDto(final Item item);
    List<ItemDto> toDtoList(final List<Item> items);
    List<Item> toEntityList(final List<ItemDto> itemDtos);
}