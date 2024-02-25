package ru.otus.demo.controllers;

import com.fasterxml.jackson.core.type.TypeReference;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;

import ru.otus.demo.AbstractControllerTest;
import ru.otus.demo.callables.ItemCallable;
import ru.otus.demo.converters.ItemConverter;
import ru.otus.demo.dtos.ItemDto;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ItemControllerTest extends AbstractControllerTest {

    @MockBean
    private ItemConverter itemConverter;

    @MockBean
    private ItemCallable itemCallable;


    @Test
    @Override
    public void testMustReturnCorrectObjectByGivenId() throws Exception {

        given(itemConverter.getOne(any(UUID.class))).willReturn(
            OBJECT_MAPPER.readValue(new ClassPathResource("mocks/item/item.json").getFile(), ItemDto.class)
        );

        mockMvc
            .perform(get("/items/{id}", "c082c792-4e3d-4cab-ab01-18b82b3aef7d")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("mYuHnTm0jN"))
            .andExpect(jsonPath("$.available").value(false))
            .andDo(print());

    }

    @Test
    @Override
    public void testMustSuccessfullyReturnListOfObjects() throws Exception {
        given(itemConverter.getAll()).willReturn(
            OBJECT_MAPPER.readValue(new ClassPathResource("mocks/item/items.json").getFile(), new TypeReference<>() {})
        );

        mockMvc
            .perform(get("/items")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(7)))
            .andExpect(jsonPath("$[0].name").value("PNa653RWuM"))
            .andExpect(jsonPath("$[0].available").value(false))
            .andExpect(jsonPath("$[1].name").value("GYbpbYqctO"))
            .andExpect(jsonPath("$[1].available").value(true))
            .andExpect(jsonPath("$[2].name").value("QhtWeuLfqu"))
            .andExpect(jsonPath("$[2].available").value(false))
            .andExpect(jsonPath("$[3].name").value("mYuHnTm0jN"))
            .andExpect(jsonPath("$[3].available").value(false))
            .andDo(print());
    }

    @Test
    @Override
    public void testMustDeleteObjectByGivenId() throws Exception {

        doNothing().when(itemConverter).deleteOne(any(UUID.class));

        mockMvc
            .perform(delete("/items/{id}", "a4b8278d-3b48-403d-9486-f29ca58e6f39")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8))
            .andExpect(status().isNoContent())
            .andDo(print());

        verify(itemConverter, times(1)).deleteOne(any(UUID.class));
        verifyNoMoreInteractions(itemConverter);

    }

    @Test
    @Override
    public void testMustSuccessfullyPersistObjectByGivenData() throws Exception {

        mockMvc
            .perform(post("/items")
            .param("name", "super-item")
            .param("available", "true")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8))
            .andExpect(status().isCreated())
            .andDo(print());

        verify(itemConverter, times(1)).createOne(any(String.class), any(Boolean.class));
        verifyNoMoreInteractions(itemConverter);

    }

    @Test
    @Override
    public void testMustSuccessfullyUpdatePersistedObject() throws Exception {

        ItemDto ingredientDto = OBJECT_MAPPER.readValue(
            new ClassPathResource("mocks/item/item_updated.json").getFile(),
            ItemDto.class
        );

        given(itemConverter.updateOne(any(UUID.class), any(ItemDto.class))).willReturn(ingredientDto);

        mockMvc
            .perform(put("/items/{id}", "ab02d219-5b8f-43c5-970e-3373f904423b")
            .content("""
                {
                    "id": "ab02d219-5b8f-43c5-970e-3373f904423b",
                    "name": "MegaItem",
                    "available" : false
                }""")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8))
            .andExpect(status().isOk())
            .andDo(print());
    }

}