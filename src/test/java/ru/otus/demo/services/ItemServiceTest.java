package ru.otus.demo.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.otus.demo.dtos.ItemDto;
import ru.otus.demo.exceptions.Code409ItemConflictException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertFalse;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Test
    public void mustExtractExactDtoById() {
        assertEquals("QH8NbyGuH7", itemService.getOne(UUID.fromString("7b2bd50f-95b2-4674-97ff-7cc9349b95d4")).getName());
    }

    @Test
    public void mustDeleteOneEntity() {
        itemService.deleteOne(UUID.fromString("0ef0f4cf-1d80-427f-9669-715f69f00456"));
        assertEquals(4, itemService.getAll().size());
        itemService.createOne("Yogurt", false);
    }

    @Test
    public void mustUpdateOneEntity() {

        UUID id = UUID.fromString("fa441a3d-db5b-4ac4-bffc-4d7596e3438b");

        itemService.updateOne(id, ItemDto.builder().name("Pork").available(false).build());
        assertEquals("Pork", itemService.getOne(id).getName());
        assertFalse("Pork", itemService.getOne(id).isAvailable());

    }

//    @Test
//    public void mustThrowAConflictException() {
//        assertThrows(
//            Code409ItemConflictException.class,
//            () -> itemService.deleteOne(UUID.fromString("ab02d219-5b8f-43c5-970e-3373f904423b"))
//        );
//    }

}