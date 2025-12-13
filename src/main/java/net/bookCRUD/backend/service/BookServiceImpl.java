package net.bookCRUD.backend.service;

import lombok.RequiredArgsConstructor;
import net.bookCRUD.backend.exception.ResourceNotFoundException;
import net.bookCRUD.backend.dto.BookDTO;
import net.bookCRUD.backend.entity.Book;
import net.bookCRUD.backend.mapper.BookMapper;
import net.bookCRUD.backend.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;
    private final BookMapper bookMapper;

    private Book findBookById(Long id) {
        return bookRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
    }

    public BookDTO createBook(BookDTO dto) {
        Book book = bookRepo.save(bookMapper.toEntity(dto));
        return bookMapper.toDTO(book);
    }

    public BookDTO getBook(Long id) {
        return bookMapper.toDTO(findBookById(id));
    }

    public List<BookDTO> getAllBooks() {
        return bookRepo.findAll().stream().map(bookMapper::toDTO).toList();
    }

    public BookDTO updateBook(Long id, BookDTO dto) {
        Book existing = findBookById(id);
        bookMapper.toEntity(dto, existing);
        Book updated = bookRepo.save(existing);
        return bookMapper.toDTO(updated);
    }

    public void deleteBook(Long id) {
        bookRepo.delete(findBookById(id));
    }
}
