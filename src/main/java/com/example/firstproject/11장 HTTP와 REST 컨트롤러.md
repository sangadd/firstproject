## 11.1 REST API의 동작 이해하기   
**REST** : HTTP URL로 서버의 자원(resource)을 명시하고 HTTP 메서드로 해당 자원에 대해 CRUD하는 것을 말한다.   
**API** : 클라이언트가 서버의 자원을 요청할 수 있도록 서버에서 제공하는 인터페이스이다.   
`=> REST API란 REST 기반으로 API를 구현한 것`
> REST API를 잘 구현하는 클라이언트가 기기에 구애받지 않고 서버의 자원을 이용할 수 있을 뿐만 아니라 서버가 클라이언트의 요청에 체계적으로 대응할 수 있어서 서버 프로그램의 재사용성과 확장성이 조아짐   

## 11.2 REST API의 구현 과정  
REST API를 구현하려면 REST API의 주소, 즉 URL를 설계해야함   

**클라이언트(JSON) <-> 서버(REST API)**   
1. 조회 요청 : /api/articles 또는 /api/articles/{id}  
-> GET 메서드로 Article 목록 전체 또는 단일 Article을 조회   
2. 생성 요청 : /api/articles  
-> POST 메서드로 새로운 Article을 생성해 목록에 저장    
3. 수정 요청 : /api/articles/{id}  
-> PATCH 메서드로 특정 Article의 내용을 수정함   
4. 삭제 요청 : /api/articles/{id}  
-> DELETE 메서드로 특정 Article을 삭제   

> 이제 주소 설계가 끝났다면 URL 요청을 받아 그 결과를 JSON 으로 반환해 줄 컨트롤러도 만들어야함 !!    
> 근데 게시판을 만들 때는 일반컨트롤러를 사용했지만 REST API로 요청과 응답을 주고 받을 때는 REST 컨트롤러를 사용함     
> 그리고 응답할 때 적절한 상태 코드를 반환하기 위해  ResponseEntity라는 클래스도 활용   


