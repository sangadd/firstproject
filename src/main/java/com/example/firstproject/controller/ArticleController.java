package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class ArticleController {
    @Autowired // 스프링 부트가 미리 생성해 놓은 리파지터리 객체 주입(DI = 의존성 주입)
    private ArticleRepository articleRepository;
    @GetMapping("/articles/new")
    public String newArticlesForm() {
        return "articles/new";
    }

    // System.out.println(form.toString()); 이러한 println() 문으로 데이터를 검증하면 기록에 남지 않을뿐더러 서버의 성능에도 악영향 끼치기 떄문에 로깅 기능 사용하기!
    // 로깅 기능으로 로그를 찍으면 나중에라도 그동안 찍힌 로그를 찾아볼 수 있다. => @Slf4j 어노테이션 추가
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {  // ArticleForm form를 추가해 폼 데이터를 DTO로 받기

        log.info(form.toString()); // @Slf4j 어노테이션 추가하고 로깅 코드 추가
        // 1. DTO를 엔티티로 변환
        Article article = form.toEntity();

        // 2, 리파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);  // articel 엔티티를 저장해 saved 객체에 반환
        log.info(saved.toString()); // @Slf4j 어노테이션 추가하고 로깅 코드 추가
        return "";
    }
}
