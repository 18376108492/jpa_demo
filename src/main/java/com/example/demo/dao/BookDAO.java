package com.example.demo.dao;

import com.example.demo.repository.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@RepositoryRestResource(path = "/bs")不想用books
public interface BookDAO extends JpaRepository<Book,Integer> {

    /**
     * 根据书名查询数据
     * @param name
     * @return
     */
      // @RestResource(path = "/byname",rel = "byname")
        List<Book> getAllByName(@Param(value = "name")String name);


    /**
     * 根据书名字段和作者字段模糊查询
     * @param name
     * @param auth
     * @return
     */
        List<Book> getBookByNameContainsAAndAuthContains(String name,String auth);

    /**
     * 获取ID值最大的数据
     * @return
     */
    @Query(value = "SELECT * from t_book where id=(select max(id) from t_book)",nativeQuery = true)
    Book getMaxIdBook();


    /**
     * 根据数据和作者查询，查询参数录入sql测试
     * @param name
     * @param auth
     * @return
     */
    @Query(value = "SELECT * from t_book where name=:name AND auth=:auth",nativeQuery = true)
    Book getBookByNameAndAuth(@Param("name")String name,@Param("auth")String auth);

    //如果涉及数据库数据修改的需要添加@Modifying
    //例如:
    /**
     * 添加数据操作
     * @param name
     * @param auth
     * @return
     */
    @Query(value = "insert into t_book (name,auth) values(:name,:auth)",nativeQuery = true)
    @Modifying
    int addBook(@Param("name")String name,@Param("auth")String auth);

}