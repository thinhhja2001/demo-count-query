package com.example.demo.controller;

import com.example.demo.dto.BookDTO;
import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

  @Autowired
  private BookService bookService;
  @Autowired
  EntityManager entityManager;

  @PostMapping
  public void create(@RequestBody BookDTO dto) {
    bookService.create(dto);
  }

  @GetMapping
  public List<BookDTO> findAll() {
    return bookService.findAll();
  }

  @GetMapping("/count")
  public Map<String, Long> countBook() {
    CriteriaBuilder hibernateCriteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> countQuery = hibernateCriteriaBuilder.createQuery(Long.class);
    Root<Book> listBookCount = countQuery.from(Book.class);

    List<Predicate> predicates = new ArrayList<>();
    predicates.add(hibernateCriteriaBuilder.equal(listBookCount.get("author"), "Billionaire Industrialist"));
    countQuery.select(hibernateCriteriaBuilder.count(listBookCount))
        .where(hibernateCriteriaBuilder.and(predicates.toArray(new Predicate[0])));

    Map<String, Long> result = new HashMap<>();
    result.put("count", entityManager.createQuery(countQuery).getSingleResult());
    return result;
  }
}