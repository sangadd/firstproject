package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor // Article() 생성자를 대체하는 어노테이션 추가
@NoArgsConstructor // 기본 생성자 추가 에노테이션
@ToString           // toString() 메서드를 대체하는 어노테이션 추가
@Entity
@Getter
public class Article {
    @Id                     // 엔티티의 대푯값 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)         // DB가 id 자동 생성
    private Long id;

    @Column                 // title 필드 선언, DB 테이블의 title 열과 연결됨
    private String title;

    @Column                 // content 필드 선언, DB 테이블의 content 열과 연결됨
    private String content;

    // 룸복으로 @Getter 어노테이션 추가하면 이 코드는 삭제해도 됨
//    public Long getId() { // 주의! 데이터 타입을 String -> Long로 변경
//        return id;
//    }

/*    public Article(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }*/

}
