package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) { // 타입이 List<CommentDto>  인 이유는 여러 Comment를 리스트로 받아오기 위함이다.
        /*
        // 1. 댓글 조회
        List<Comment> comments = commentRepository.findByArticleId(articleId);

        // 2. 엔티티 -> DTO 변환
        List<CommentDto> dtos = new ArrayList<CommentDto>();
        for (int i = 0; i < comments.size(); i++) { // (1) 조회한 댓글 엔티티 수만큼 반복하기
            Comment c = comments.get(i); // (2) 조회한 댓글 엔티티 하나씩 가져오기
            CommentDto dto = CommentDto.createCommentDto(c); // (3) 엔티티를 DTO로 변환
            dtos.add(dto); // (4) 변환된 DTO를 dtos 리스트에 삽입
        }
        // 3. 결과 반환
        return dtos;
         */

        // 위에 for문을 스트림 문법으로 개선하자


        // 3. 결과 반환
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment)) // .map(a -> b) : 스트림의 각 요소(a)를 꺼내 b를 수행한 결과로 매핑
                .collect(Collectors.toList()); // 스트림 데이터를 리스트 자료형으로 변환

        /*
        * 스트림의 특징
        * 스트림은 자바의 컬렉션, 즉 리스트와 해시맵 등의 데이터 묶음을 요소별로 순차적으로 조작하는 데 좋다.
        *
        * 1. 원본 데이터를 읽기만 하고 변경하지 않는다.
        * 2. 정렬된 결과를 컬렉션이나 배열에 담아 반환할 수 있다.
        * 3. 내부 반복문으로 반복문이 코드상에 노출되지 않는다.
        */
    }
}
