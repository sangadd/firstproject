package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // 로그 찍을 수 있는 어노테이션
@RestController
public class ArticleApiController {

    @Autowired // 3. 게시글 리파지터리 주입!
    private ArticleRepository articleRepository;

    // GET
    @GetMapping("/api/articles") // 1. url 요청 접수!
    public List<Article> index() { // 2. index() 메서드 정의!
        return articleRepository.findAll();
    }

    // postman 으로 http://localhost:8080/api/articles get 요청 보냈더니
    /*
    결과로 Body에는 아래처럼 나오고
[
    {
        "id": 1,
        "title": "가가가가",
        "content": "1111"
    },
    {
        "id": 2,
        "title": "나나나나",
        "content": "2222"
    },
    {
        "id": 3,
        "title": "다다다다",
        "content": "3333"
    }
]
http에는 [{"id":1,"title":"가가가가","content":"1111"},{"id":2,"title":"나나나나","content":"2222"},{"id":3,"title":"다다다다","content":"3333"}] 여기도 json 데이터 잘 출력됨!
    */

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) { // @PathVariable Long id 를 통해 URL의 id를 매개변수로 받아 오기
        return articleRepository.findById(id).orElse(null);
    }

    /*
    * 이 코드 결과로
    {
    "id": 1,
    "title": "가가가가",
    "content": "1111"
    }
    */

    // POST
    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm dto) {
        Article article = dto.toEntity();
        return articleRepository.save(article);
    }

    /*
    postman으로 post 요청으로 http://localhost:8080/api/articles 보냈더니
    {
        "id": 4,
        "title": null,
        "content": null
    }
    성공 응답이 나왔는데 null이 나온다??
    왜?
    웹 페이지에 게시판 폼을 만들고 데이터를 생성할 때는 컨트롤러의 메서드의 매개변수를 dto로 받아오면 됐었는데
    REST API에서는~!!
    데이터를 생성할 때 JSON 데이터를 받아와야하므로 매개변수로 dto를 쓴다고 해서 받아올 수가 없다

    그럼 어떻게 해야할까?

    dto 매개변수 앞에 @RequestBody라는 어노테이션 추가하면 요청 시 본문(BODY)에 실어 보내는 데이터를 create() 메서드의 매개변수로 받아올 수 있다.

    BODY에
   {
        "title": "aaa",
        "content": "123123123"
    }
    이거 쓰고 요청 보내기

    그럼 결과는 아래와 같이 나옴!
    {
        "id": 4,
        "title": "aaa",
        "content": "123123123"
    }

    */

    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
        // 1. 수정용 엔티팅 생성하기 (DTO -> 엔티티 변환하기)
        Article article = dto.toEntity(); // dto를 엔티티로 변환하기
        log.info("id, article", id, article.toString());

        // 2. DB에 대상 엔티티가 있는지 조회하기
        Article target = articleRepository.findById(id).orElse(null);

        // 대상 엔티티가 없거나 수정하려는 id가 잘못됐을 경우 처리하기
        if (target == null || id != article.getId()){
            // 400, 잘못된 요청 응답
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // 4. 대상 엔티티가 있으면 수정 내용으로 업데이트하고 정상 응답(200) 보내기
        target.patch(article); // 1. 기존 데이터에 새 데이터 붙이기
        Article updated = articleRepository.save(target); // article 엔티티 db에 저장

        return ResponseEntity.status(HttpStatus.OK).body(updated); // 정상 응답
    }
    /*
    postman에서 patch 로 http://localhost:8080/api/articles/1 요청 보내면
    {
        "id": 1,
        "title": "aaa",
        "content": "123123123"
    }
    결과 나옴
    */

    // DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        // 1. DB에서 대상 엔티티가 있는지 조회
        Article target = articleRepository.findById(id).orElse(null);

        // 2. 대상 엔티티가 없어서 요청 자체가 잘못됐을 경우 처리하기
        if (target == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // 3. 대상 엔티티가 있으면 삭제하고 정상 응답(200) 반환
        articleRepository.delete(target);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
    /*
    delete 으로 http://localhost:8080/api/articles/2 요청 보내면
    id가 2인 데이터 삭제됨
    */
}
