package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// JpaRepository<대상 엔티티, 대표값의 타입> 형식
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 쿼리를 메서드로 작성하는 이러한 메서드를 네이티브 쿼리 메서드(native query method) 라고 함
    // 네이티브 쿼리 메서드는 직접 작성한 sql 쿼리를 리파지터리 메서드로 실행할 수 있게 해줌

    // 특정 게시물의 모든 댓글 조회
//    @Query(value = "SELECT * FROM comment WHERE article_id = :articleId",
//        nativeQuery = true) // value 속성에 실행하려는 쿼리 작성
    // 이 둘 동일함 아래는 + 연산자 사용한것
    // 특정 게시글의 모든 댓글 조회
    @Query(value = "SELECT * FROM comment WHERE article_id = :articleId",
            nativeQuery = true) // value 속성에 실행하려는 쿼리 작성
    List<Comment> findByArticleId(Long articleId);

    // findByArticleId 메서드로 원하는 메서드로 쿼리를 수행하기 위해 @Query 어노테이션 붙이기
    // 형식 : @Query(value = "쿼리", nativeQuery = true)
    // @Query  어노테이션은 SQL과 유사한 JPQL(Java Persistence Query Language)이라는 객체 지향 쿼리 언어를 통해 복잡한 쿼리 처리 지원
    // nativeQuery = true 은 기존 sql문을 그대로 사용할 수 있다.
    // where절에 조건을 작성할 때 매개변수 앞에는 꼭 콜론 붙이기 -> 그래야만 메서드에서 넘긴 매개변수와 매칭됨

    // 특정 닉네임의 모든 댓글 조회
    List<Comment> findByNickname(String nickname);


}
