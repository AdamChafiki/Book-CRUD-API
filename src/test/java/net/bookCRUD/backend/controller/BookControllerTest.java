package net.bookCRUD.backend.controller;

import net.bookCRUD.backend.dto.BookDTO;
import net.bookCRUD.backend.exception.ResourceNotFoundException;
import net.bookCRUD.backend.service.BookService;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @MockitoBean
    private BookService bookService;

    // GET /books
    @Test
    void testGetAllBooks() throws Exception {
        BookDTO book1 = BookDTO.builder()
                .id(1L)
                .title("Book 1")
                .author("Author 1")
                .publicationYear(2020)
                .build();
        BookDTO book2 = BookDTO.builder()
                .id(2L)
                .title("Book 2")
                .author("Author 2")
                .publicationYear(2021)
                .build();


        Mockito.when(bookService.getAllBooks()).thenReturn(Arrays.asList(book1, book2));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].author").value("Author 1"));
    }

    // GET /books/{id}
    @Test
    void testGetBookById() throws Exception {
        BookDTO book1 = BookDTO.builder()
                .id(1L)
                .title("Book 1")
                .author("Author 1")
                .publicationYear(2024)
                .build();
        Mockito.when(bookService.getBook(1L)).thenReturn(book1);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("Author 1"));
    }

    // GET /books/{id} not found
    @Test
    void testGetBookNotFound() throws Exception {
        Mockito.when(bookService.getBook(99L))
                .thenThrow(new ResourceNotFoundException("Book not found with ID: 99"));

        mockMvc.perform(get("/books/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book not found with ID: 99"));
    }

    @Test
    void testCreateBook() throws Exception {
        BookDTO dto = BookDTO.builder()
                .id(null)
                .title("Book 1")
                .author("Author 1")
                .publicationYear(2020)
                .build();

        BookDTO saved = BookDTO.builder()
                .id(1L)
                .title("Book 1")
                .author("Author 1")
                .publicationYear(2020)
                .build();

        Mockito.when(bookService.createBook(Mockito.any()))
                .thenReturn(saved);

        mockMvc.perform(post("/books")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.author").value("Author 1"));
    }


    // POST /books validation error
    @Test
    void testCreateBookValidationError() throws Exception {
        BookDTO dto = BookDTO.builder()
                .id(null)
                .title("")
                .author("")
                .publicationYear(null)
                .build();

        mockMvc.perform(post("/books")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fields.author").value("Author is required"));
    }

    // PUT /books/{id}
    @Test
    void testUpdateBook() throws Exception {
        BookDTO dto = BookDTO.builder()
                .id(null)
                .title("Updated Book")
                .author("Updated Author")
                .publicationYear(1999)
                .build();
        BookDTO updated = BookDTO.builder()
                .id(1L)
                .title("Updated Book")
                .author("Updated Author")
                .publicationYear(1999)
                .build();

        Mockito.when(bookService.updateBook(Mockito.eq(1L), Mockito.any())).thenReturn(updated);

        mockMvc.perform(put("/books/1")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("Updated Author"));
    }

    // DELETE /books/{id}
    @Test
    void testDeleteBook() throws Exception {
        Mockito.doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());
    }
}
