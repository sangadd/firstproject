package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 해당 클래스를 스프링 부트와 연동해 테스트
class ArticleServiceTest {

    @Autowired
    ArticleService articleService; // articleService 객체 주입

    @Test // 해당 메서드가 테스트를 위한 코드
    void index() {
        // 1. 예상 데이터 작성하기
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");
        List<Article> expected = new ArrayList<>(Arrays.asList(a, b, c)); // a b c 합치기

        /*
        * Arrays.asList() 메서드
        * 입력된 배열 또는 2개 이상 동이란 타입 데이터를 정적 리스트로 만들어 반환
        * 정적 리스트는 고정 크기 add()나 remove() 메서드 사용 못함
        */

        // 2. 실제 데이터 획득하기
        List<Article> articles = articleService.index();

        // 3. 예상 데이터와 실제 데이터 비교해 검증하기
        assertEquals(expected.toString(), articles.toString());

    }

    @Test
    void show_성공() {
        // 1. 예상 데이터
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");

        // 2. 실제 데이터
        Article article = articleService.show(id);

        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());

    }


    @Test
    void show_실패() {
        // 1. 예상 데이터
        Long id = -1L;
        Article expected = null;

        // 2. 실제 데이터
        Article article = articleService.show(id);

        // 3. 비교 및 검증
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void create_성공() {
        // 1. 예상 데이터
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);

        // 2. 실제 데이터
        Article article = articleService.create(dto);

        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void create_실패() {
        // 1. 예상 데이터
        Long id = 4L;
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;

        // 2. 실제 데이터
        Article article = articleService.create(dto);

        // 3. 비교 및 검증
        assertEquals(expected, article);

    }
}