package com.example.demo.service;

import com.example.demo.dao.BookDAO;
import com.example.demo.repository.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Resource
    private BookDAO bookDAO;

    public boolean saveBook(Book book){
        Book save = bookDAO.save(book);
        return true;
    }

   public boolean updateBook (Book book){
        //更新和添加操作都可以，更新和添加的区别操作在于有没有ID
        bookDAO.saveAndFlush(book);
        return true;
   }

   public boolean deleteBook(Integer id){
        bookDAO.deleteById(id);
        return true;
   }

   public Book findById(Integer id){
       Optional<Book> book = bookDAO.findById(id);
       return book.get();
   }

   public List<Book> findAll(){
       //查询全部
        List<Book> all = bookDAO.findAll();
       //查询结果根据ID排序
       List<Book> add1 = bookDAO.findAll(new Sort(Sort.Direction.DESC,"id"));
       return all;
   }

   public Page<Book> findAllByPage(){
       Pageable pageable=PageRequest.of(0,2);
       Page<Book> all = bookDAO.findAll(pageable);
       System.out.println(all.getTotalPages());
       System.out.println(all.getContent());
       System.out.println(all.getSize());
       return all;
    }
}
