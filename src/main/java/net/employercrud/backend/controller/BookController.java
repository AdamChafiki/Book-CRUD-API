package net.employercrud.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.employercrud.backend.dto.BookDTO;
import net.employercrud.backend.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    @PostMapping
    public ResponseEntity<BookDTO> create(@Valid @RequestBody BookDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(dto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAll() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> update(@PathVariable Long id, @Valid @RequestBody BookDTO dto) {
        return ResponseEntity.ok(bookService.updateBook(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}