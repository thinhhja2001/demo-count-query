package com.example.demo.service;

import com.example.demo.dto.BookDTO;
import java.util.List;
public interface BookService {
  void create(BookDTO dto);
  List<BookDTO> findAll();
}
