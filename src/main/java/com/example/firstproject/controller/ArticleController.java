package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return "redirect:/articles/" + saved.getId();

    }

    @GetMapping("/articles/{id}") // 컨트롤러에서 URL 변수를 사용할 때는 중괄호 하나만 씀
    public String show(@PathVariable Long id, Model model) {  // 매개변수로 id 받아오기
        // 1. id를 조회해 db에서 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null); // id로 찾은 값이 없이 null 반환

        // 2. 가져온 데이터를 모델에 등록하기
        // 모델이 데이터 등록할 때는 addAttribute() 메서드 사용 -> 형식은 model.addAttribute(String name, Object value)
        model.addAttribute("article", articleEntity);

        // 3. 조회한 데이터 사용자에게 보여줄 뷰 페이지 생성하고 반환하기
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        // 1. db에서 모든 Article 데이터 가져오기
        ArrayList<Article> articleEntityList = articleRepository.findAll();

        // 2. 가져온 Article 묶음을 모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList);

        // 3. 사용자에게 보여줄 뷰 페이지 설정하기
        return  "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){ // 2. id를 매개변수로 받아 오기
        // 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null); // 1. DB에서 수정할 데이터 가져오기 

        // 모델에 데이터 등록하기
        model.addAttribute("articles", articleEntity);

        // 뷰 페이지 설정하기
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form){ // 매개변수로 DTO 받아오기
        // 1. DTO를 엔티티로 변환하기
        Article articleEntity = form.toEntity(); // DTO(form)를 엔티티(articleEntity)로 변환하기

        // 2. 엔티티를 DB에 저장
        // 2-1. DB에서 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        // 2-2. 기존 데이터 값을 갱신하기
        if (target != null){
            articleRepository.save(articleEntity); // 엔티티를 DB에 저장
        }

        // 3. 수정 결과 페이지로 리다이렉트하기
        return  "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){ // 2. id를 매개변수로 가져오기
        // 1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null); // 1. 데이터 찾기

        // 2. 대상 엔티티 삭제하기
        if (target != null){ // 1. 삭제할 대상이 있는지 확인
            articleRepository.delete(target); // delete() 메서드로 대상 삭제
            rttr.addFlashAttribute("msg", "삭제되었습니다!");
        }

        // 3. 결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }

    /*
    * delete() 메서드 설명
    클라이언트가 특정 게시글의 삭제 요청을 하면 컨트롤러의 delete() 메서드에서 @GetMapping 으로 받는다.
    delete() 메서드가 삭제 대상을 찾으려면 대푯값인 id가 필요한데 이때 @PathVariable 어노테이션을 사용한다.
    @PathVariable 어노테이션은 @GetMapping 의 URL에서 중괄호에 둘러싸인 값을 매개변수로 가져온다.
    리파지터리는 이렇게 가져온 ID로 DB에서 삭제 대상을 찾고 리파지터리가 제공하는 delete() 메서드로 데이터를 삭제한다.
    이때 db 내부에서 delete 라는 sql 문이 자동으로 수행된다
    *
    삭제 작업이 끝나면 결과 페이지로 리다이렉트함
    삭제됐다는 메시지도 함께 출력하는데 이를 위해 RedirectAttributes 객체의 addFlashAttribute() 메서드를 이용한다.
    addFlashAttribute() 메서드는 리다이렉트되는 시점에 사용할 휘발성 데이터를 등록한다.
    */
}
