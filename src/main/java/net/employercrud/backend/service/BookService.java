package net.employercrud.backend.service;

import net.employercrud.backend.dto.BookDTO;

import java.util.List;



public interface BookService {
    BookDTO createBook(BookDTO dto);
    BookDTO getBook(Long id);
    List<BookDTO> getAllBooks();
    BookDTO updateBook(Long id, BookDTO dto);
    void deleteBook(Long id);
}
