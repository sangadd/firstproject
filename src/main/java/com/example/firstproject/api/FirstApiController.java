package com.example.firstproject.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // REST API용 컨트롤러
public class FirstApiController {
    @GetMapping("/api/hello")
    public String hello(){
        return "hello world!";
    }

    // postman 으로 http://localhost:8080/api/hello GET 요청 보냈더니 hello world! 출력 확인
    /*
    * 그럼 REST  컨트롤러와 일반 컨트롤러 차이 ???
    *
    *
    * REST 컨트롤ㄹ러는 JSON 이나 텍스트 같은 데이터를 반환 VS 일반 컨트롤러는 뷰 페이지를 반환한다. (return 통해~) */
}
