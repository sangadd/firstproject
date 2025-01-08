package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

/*
* 리파지터리는 사용자가 직접 구현할 수도 있지만 JPA에서 제공하는 리파지터리 인스페이스를 활용해 만들 수도 있다.
* JPA에서 제공하는 인터페이스인 CrudRepository를 상속해 엔티티를 관리(생성, 조회, 수정, 삭제)할 수 있다.
* 2개의 제네릭 요소를 받는데,
* 첫번째는 관리 대상 엔티티의 클래스 타입인데 여기서는 Article을 사용한다.
* 두번째는 관리 대상 엔티티의 대푯값 타입인데 여기서는 Long을 작성한다.
*/

public interface ArticleRepository extends CrudRepository<Article, Long> {
}
