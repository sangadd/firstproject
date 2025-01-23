package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // 서비스 객체 생성
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository; // 게시글 리파지터리 객체 주입

    public List<Article> index() {
        return articleRepository.findAll(); // 데이터는 리파지터리를 통해 가져와야해서 이렇게 작성 -> db에서 조회한 결과 반환

        /*
        save(S entity) : 신규 데이터 인서트 혹은 기존 데이터 업데이트(Upsert 와 비슷)
        findById(데이터형 id) : id로 조회
        findAll() : Select * 전체 데이터 조회
        deleteById() : 해당 id의 데이터를 Delete 함
        deleteAll() : 테이블의 모든 데이터 삭제
        이거 잘 정리한 블로그 북마크바로 해놨음
        */
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity(); // 1. dto를 엔티티로 변환한 후 article에 저장

        // 2. id가 존재한다면 (null이 아니라면) null을 반환하기
        // 왜냐하면 id는 데이터를 생성할 떄 db가 알아서 자동 생성해주기 떄문에 굳이 넣을 필요 없음
        // 근데 데이터를 생성할 때 id가 존재한다면 객체 참조 타입의 기본값인 null로 반환해줘야함
        if (article.getId() != null){
            return null;
        }

        return articleRepository.save(article); // 3. article를 DB에 저장
    }

    public Article update(Long id, ArticleForm dto) {
        // 1. 수정용 엔티티 생성하기 (DTO -> 엔티티 변환하기)
        Article article = dto.toEntity(); // dto를 엔티티로 변환하기

        // 2. DB에 대상 엔티티가 있는지 조회하기
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 대상 엔티티가 없거나 수정하려는 id가 잘못됐을 경우 처리하기
        if (target == null || id != article.getId()){
            // 400, 잘못된 요청 응답
            return null; // 응답은 컨트롤러가 하므로 여기서는 null 반환 !!
        }

        // 4. 대상 엔티티가 있으면 수정 내용으로 업데이트하고 정상 응답(200) 보내기
        target.patch(article); // 1. 기존 데이터에 새 데이터 붙이기
        Article updated = articleRepository.save(target); // article 엔티티 db에 저장

        return updated; // 응답은 컨트롤러가 하므로 여기서는 수정 데이터만 반환해줘!!!
    }

    public Article delete(Long id) {
        // 1. DB에서 대상 엔티티가 있는지 조회
        Article target = articleRepository.findById(id).orElse(null);

        // 2. 대상 엔티티가 없어서 요청 자체가 잘못됐을 경우 처리하기
        if (target == null){
            return null;  // 응답은 컨트롤러가 하므로 여기서는 null 반환 !!
        }

        // 3. 대상 엔티티가 있으면 삭제하고 정상 응답(200) 반환
        articleRepository.delete(target);

        return target; //  DB에서 삭제한 대상을 컨트롤러에 반환 !!
    }

    /*
    * 트랜잭션은 보통 서비스에서 관리한다.
    * 만약 데이터 생성에 실패했을때 나중에 추가한 데이터들이 남지 않기를 원하고 데이터 생성 실패 이전 상황으로 되돌리기 위해서 트랜잭션 선언
    * 그럼 이 메서드가 중간에 실패하더라도 롤백을 통해 이전 상태로 돌아갈 수 있다.
    * 그럼 결과 실패 후 롤백돼 추가된 데이터가 없음

    * 위에 내용이 뭔 말이냐
    * 예를 들어 5개의 Article 중 3개 데이터한 후 예외가 발생하면 3개의 데이터만 db에 남아있음 이건 데이터 무결성(정확하고 일관성을 유지하며 신뢰할 수 있는 상태) 손상시킴
    * 그래서 트랜잭션을 사용하면 트랜잭션 내에 작업 중 하나라도 실패하면 모든 작업이 롤백이 돼서 3개 데이터에서 예외가 발생하면 db에 저장하지 않고
    * 이전 상태로 복구된다는 말 (롤백)
    * 그래서 코드에서 3번 강제로 예외 발생할 떄 트랜잭션 효과를 볼 수 있는 것임 ! 원자성
    * */

    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // 1. dto 묶음(리스트)을 엔티팅 묶음(리스트)으로 변환
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

        /*
        * 스트림 문법
        * 리스트와 같은 자료구조에 저장된 요소를 하나씩 순회하면서 처리하는 코드 패턴!
        */

        // 2. 엔티팅 묶음(리스트)을 db에 저장
        articleList.stream()
                .forEach(article -> articleRepository.save(article));
        // 이걸 for 문으로 작성하면
        for (int i = 0; i < articleList.size(); i++) {
            Article article = articleList.get(i);
            articleRepository.save(article);
        }


        // 3. 강제 예외 발생시키기
        articleRepository.findById(-1L) // id가 -1인 데이터 찾기
                .orElseThrow(() -> new IllegalArgumentException("결제 실패! ")); // 찾는 데이터가 없으면 예외 발생함

        // 4. 결과 값 반환하기
        return articleList;
    }
}

