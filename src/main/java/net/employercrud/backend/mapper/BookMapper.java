package net.employercrud.backend.mapper;

import lombok.RequiredArgsConstructor;
import net.employercrud.backend.dto.BookDTO;
import net.employercrud.backend.entity.Book;
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
