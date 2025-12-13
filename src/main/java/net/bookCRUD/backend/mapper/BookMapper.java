package net.bookCRUD.backend.mapper;

import lombok.RequiredArgsConstructor;
import net.bookCRUD.backend.dto.BookDTO;
import net.bookCRUD.backend.entity.Book;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final ModelMapper modelMapper;


    public BookDTO toDTO(Book book) {
        return modelMapper.map(book, BookDTO.class);
    }


    public Book toEntity(BookDTO dto) {
        return modelMapper.map(dto, Book.class);
    }

    public void toEntity(BookDTO dto, Book existing) {
        modelMapper.map(dto, existing);
    }
}
