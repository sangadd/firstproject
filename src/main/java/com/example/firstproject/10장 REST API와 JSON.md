## 10.1 REST API와 JSON의 등장 배경 
웹 서비스를 사용하는 클라이언트에는 웹 브라우저만 있는 게 아니다.  

스마트폰, 스마트워치, 태블릿 등이 모두 클라이언트다.   
IT 기기 발전에 따라 지금도 수많은 클라이언트가 만들어지고 있다.     

서버는 이러한 모든 클라이언트의 요청에 응답해야한다.   
웹 브라우저뿐만 아니라 어떤 기기가 와도 기기에 맞는 뷰 페이지를 응답해야한다.     

그런데 이런 기기 들이 앞으로 끊없이 나올텐데 그떄마다 서버가 일일이 대응하기란 쉽지 않다.     

좋은 방법이 없을까?    
바로 **REST API** 사용하자 !    

`REST API (Representational State Transfer API)`   
서버의 자원을 클라이언트에 구애받지 않고 사용할 수 있게 하는 설계 방식   
REST API 방식에서는 HTTP 요청에 따라 응답으로 서버의 자원을 반환한다.   
서버에서 보내는 응답이 특정 기기에 종속되지 않도록 모든 기기에서 통용될 수 있는 데이터를 반환한다.   

서버는 클라이언트의 요청에 대한 응답으로 화면이 아닌 데이터를 전송한다.   
이때 사용하는 응답 데이터는 JSON(JavaScript Object Notation)이다.   
괴거에는 응답 데이터로 xml을 많이 사용했지만 최근에는 json으로 통일되는 추세   

`API (Application Programming Interface)`  
애플리케이션을 간편히 사용할 수 있게 하는,   
미리 정해진 일종의 약속으로 사용자와 프로그램 간 상호작용을 돕는다.   

`JSON 형식`  
```
{
    "id": 1,
    "title": "가가가",
    "content": "1111",
}
```

> 키와 값으로 구성된 정렬되지 않은 속성의 집합이다.  
> 키는 문자열이므로 항상 큰따옴표로 감싸고 값은 문자열인 경우에만 큰따옴표로 감싼다.   


**연습용 REST API 서버**   
`https://jsonplaceholder.typicode.com/`  

여기 들어가보면   

```
fetch('https://jsonplaceholder.typicode.com/todos/1')
      .then(response => response.json())
      .then(json => console.log(json))
```
`'https://jsonplaceholder.typicode.com/todos/1'` 이게 HTTP 요청 코드고   

- 결과  
```
{
  "userId": 1,
  "id": 1,
  "title": "delectus aut autem",
  "completed": false
}
```
이게 JSON 데이터 응답  

**새 글 등록하는 예**   
```
fetch('https://jsonplaceholder.typicode.com/posts', {
  method: 'POST',
  body: JSON.stringify({
    title: 'foo',
    body: 'bar',
    userId: 1,
  }),
  headers: {
    'Content-type': 'application/json; charset=UTF-8',
  },
})
  .then((response) => response.json())
  .then((json) => console.log(json));
```

- 응답   
```
{
  id: 101,
  title: 'foo',
  body: 'bar',
  userId: 1
}
```
여기서는 POST 메서드를 통해 지정된 URL로 요청을 보내면 게시판에 새 글이 등록되고 새로 생성된 데이터는 JSON 형태로 반환된다는 것만 기억하자 !   

- method가 put과 patch면   
> **PUT** : 기존 데이터를 전부 새 내용으로 변경, 만약 기존 데이터가 없다면 새로 생성한다.     
> **PATCH** : 기존 데이터 중에서 일부만 새 내용으로 변경한다.   

------ 

**HTTP 상태 코드**  

| 상태 코드 | 설명 |
|---|---|
| `1XX (정보)` | 요청이 수신돼 처리 중 |
| `2XX (성공)` | 요청이 정상적으로 처리됨 |
| `3XX (리다이렉션 메시지)` | 요청을 완료하려면 추가 행동이 필요함 |
| `4XX (클라이언트 요청 오류)` | 클라이언트의 요청이 잘못돼 서버가 요청을 수행할 수 없다 |
| `5XX (서버 응답 오류)` | 서버 내부에서 에러가 발생해 클라이언트 요청에 대해 적절히 수행하지 못함 |

