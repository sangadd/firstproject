package com.example.firstproject.api;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommentApiController {

    @Autowired
    private CommentService commentService;

    // 1. 댓글 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId){
        // 왜 반환형이 List<Comment>가 아니고 ResponseEntity<List<CommentDto>> 일까? 
        // 그 이유는 DB에서 조회한 댓글 엔티티 목록은 List<Comment> 지만 엔티티를 DTO로 변환하면 List<CommentDto> 가 되기 떄문이다. 
        // 그리고 응답 코드를 같이 보내려면 ResponseEntity 클래스를 활용해야해서 최종 반환형이 되는 것임 
        
        // 서비스에 위임 
        List<CommentDto> dtos = commentService.comments(articleId);
        // articleId를 전달값으로 넘긴 이유는 해당 게시글의 id를 알아야 해당 게시글의 댓글을 가져올 수 있기 떄문.
        
        // 결과 응답 
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
        // return (반환값이 있다면) ? good : bad; -> 삼항연산자
        // 그러나 실제 개발에서는 삼항연산자보다는 예외 처리 방식을 선호함 
        // 예외처리란 예기지 못한 상황이 발생했을 때를 대비해 대처하는 코드를 미리 작성하는 것을 뜻함 
        // REST API의 응답은 ResponseEntity에 실어 보내기 떄문에 ResponseEntity의 상태(status)에는 OK, 본문(body)에는 조회한 댓글 목록인 dtos를 실어 보냄
    }
}
