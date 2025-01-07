## 2.1.2 MVC 패턴 
화면을 담당하는 뷰 템플릿은 간단히 '뷰'라고 한다. 
뷰는 컨트롤러와 모델이라는 두 동료가 있는데, 컨트롤러(Controller)는 클라이언트의 요청에 따라 서버에서 이를 처리하는 역할
모델(Model)은 데이터를 관리하는 역할 
=> 이처럼 웹 페이지를 화면에 보여 주고(View), 클라이언트의 요청을 받아 처리하고(Controller), 데이터를 관리하는(Model) 역할을 나누는 기법을 MVC이라고 한다. 

## 2.2.1 뷰 템플릿 페이지 만들기
mustache는 뷰 템플릿을 만드는 도구, 즉 뷰 템플릿 엔진을 의미한다. 

## 2.2.2 컨트롤러 만들고 실행하기 
어노테이션(Annotation) : 소스 코드에 추가해 사용하는 메타 데이터의 일종. 
메타데이터는 프로그램에서 처리해야 할 데이터가 아니라 컴파일 및 실행 과정에서 코드를 어떻게 처리해야 할 지 알려주는 추가 정보 

## 2.2.3 모델 추가하기
model.addAttribute("변수명", 변숫값) : 변숫값을 "변수명"이라는 이름으로 추가 

## 2.3.1 /hi 페이지의 실행 흐름 
```
@Controller                                         // 1. 컨트롤러 선언
public class FirstController {
@GetMapping("/hi")                                  // 2. URL 요청 접수
public String niceToMeetYou(Model model) {          // 3. 메서드 수행, 4. 모델 객체 가져오기

        model.addAttribute("username", "홍팍");     // 5. 모델 변수 등록 
        
        return "greetings";                        // 6. 뷰 템플릿 페이지 반환 
    }
}
```