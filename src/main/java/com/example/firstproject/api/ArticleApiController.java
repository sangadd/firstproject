package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // 로그 찍을 수 있는 어노테이션
@RestController
public class ArticleApiController {

    @Autowired
    private ArticleService articleService;

    private ArticleRepository articleRepository;


    // 12장에서 다 주석처리 하라고 해서 주석처리하고 시작

    // GET
    @GetMapping("/api/articles") // 1. url 요청 접수!
    public List<Article> index() { // 2. index() 메서드 정의!
        return articleService.index();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) { // @PathVariable Long id 를 통해 URL의 id를 매개변수로 받아 오기
        return articleService.show(id);
    }

    // POST
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {
        Article created = articleService.create(dto);
        return (created != null) ? // 생성하면 정상, 실패하면 오류 응답보내기
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
       Article updated = articleService.update(id, dto);
       return (updated != null) ?
               ResponseEntity.status(HttpStatus.OK).body(updated) : // 정상 응답
               ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        Article updated = articleService.delete(id); // 1. 서비스를 통해 게시글 삭제
        return (updated != null) ? // 삭제 결과에 따라 응답 처리
                ResponseEntity.status(HttpStatus.OK).body(updated) : // 정상 응답
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/api/transaction-test") // 1. 여러 게시글 생성 요청 접수
    /*
    * @RequestBody 어노테이션 : POST 요청 시 본문에 실어보내는 데이터를 transactionTest() 메서드의 매개변수로 받아오는 역할함
    */
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos){
         List<Article> createdList = articleService.createArticles(dtos); // 서비스 호출

        // 컨트롤러는 그냥 웨이터처럼 요청을 받고 결과를 반환해 실제 작업은 서비스가 함

        return (createdList != null) ? // 삭제 결과에 따라 응답 처리
                ResponseEntity.status(HttpStatus.OK).body(createdList) : // 정상 응답
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
