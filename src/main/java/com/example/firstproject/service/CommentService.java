package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {
        // 이 메서드는 DB 내용을 바꾸기 떄문에 실패할 경우를 대비해야해서 @Transactional을 추가해 문제가 발생했을 때 롤백하게 해줌
        // 1. DB에서 부모 게시글을 조회해 가져오고 없을 경우 예외 발생시키기
        Article article = articleRepository.findById(articleId) // (1) 부모 게시글 가져오기
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패!" + "대상 게시글이 없습니다.")); // (2) 없으면 에러 메시지 출력

        // 2. 부모 게시글의 새 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article);

        // 3. 생성된 엔티티를 DB에 저장
        Comment created = commentRepository.save(comment);

        // 4. DB에 저장한 엔티티를 DTO로 변환해 반환하기
        return CommentDto.createCommentDto(created);
    }

    /*
    * orElseThrow 메서드
    * Optional 객체(존재할 수도 있지만 안 할 수도 있는 객체, 즉 null이 될 수도 있는 개테에 값이 존재하면 그 값을 반환하고 존재하지 않으면 전달값으로 보낸 예외를 발생시키는 메서드
    전달값으로 IllegalArgumentException 쿨래스를 사용하면 매소드가 잘못됐거나 부적합한 전달값을 보냈음을 나타냄
    */
    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        // 1. DB에서 해당 게시글을 조회해 가져오고 없을 경우 예외 발생시키기
        Comment target = commentRepository.findById(id) // (1) 수정할 댓글 가져오기 
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패!" + "대상 댓글이 없습니다.")); // (2) 없으면 에러 메시지 출력

        // 2. 가져온 댓글 내용 수정하기
        target.patch(dto);

        // 3. 수정한 댓글을 DB에 갱신하기 (수정 데이터로 덮어쓰기)
        Comment updated = commentRepository.save(target);

        // 4. DB에 반영된 엔티티를 DTO로 변환해 컨트롤러오 반환하기
        return CommentDto.createCommentDto(updated);
    }

    public CommentDto delete(Long id) {
        // 1. DB에서 해당 게시글을 조회해 가져오고 없을 경우 예외 발생시키기
        Comment target = commentRepository.findById(id) // (1) 삭제할 댓글 가져오기
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패!" + "대상이 없습니다.")); // (2) 없으면 에러 메시지 출력

        // 2. 가져온 댓글을 DB에서 삭제하기
        commentRepository.delete(target);

        // 3. 삭제한 댓글을 DTO로 변환해 반환하기 
        return CommentDto.createCommentDto(target);
    }
}
