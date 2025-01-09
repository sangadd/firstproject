package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor  // 이 어노테이션 추가했으니 생성자 코드 삭제
@ToString
public class ArticleForm {
    // 입력 폼에서 제목과 내용을 전송할 예정이니 DTO에도 필드 2개가 필요함
    private String title; // 제목을 받을 필드
    private String content;  // 내용을 받을 필드

    // 1. 전송받은 제목과 내용을 필드에 저장하는 생성자 추가
/*
    @AllArgsConstructor 어노테이션 추가해서 이 생성자 코드는 삭제

    public ArticleForm(String title, String content) {
        this.title = title;
        this.content = content;
    }*/

    // 2. 폼 데이터를 잘 받았는지 확인하기 위해 toString() 메서드 추가
/*
    @ToString 어노테이션 추가해서 이 메서드 코드 삭제

    @Override
    public String toString() {
        return "ArticleForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }*/

    // 이 메서드는 DTO인 form 객체를 엔티티 객체로 변환하는 역할
    public Article toEntity() {
        return new Article(null, title, content); // Article 엔티티의 생성자 입력 양식에 맞게 작성
    }
}
