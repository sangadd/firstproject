package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 이 클래스가 컨트롤러임을 선언하는 어노테이션
public class FirstController {
    @GetMapping("/hi") // URL 요청 접수
    public String niceToMeetYou(Model model) { // model 객체 받아오기 

        // model 객체가 "홍팍" 값을 "username"에 연결해 웹 브라우저로 보냄 
        model.addAttribute("username", "홍팍");

        return "greetings"; // greetings.mustache 파일 반환, 서버가 알아서 templates 디렉터리에서 greetings.mustache 파일을 찾아 웹 브라우저로 전송한다.
    }
    // => http://localhost:8080/hi로 접속하면 greetings.mustache 파일을 찾아 반환하게됨

    @GetMapping("/bye")
    public String seeYouNext(Model model) {
        model.addAttribute("nickname", "홍길동");
        return "goodbye";
    }
}
