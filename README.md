# todoapi
	seoultech 안드로이드앱용 API 서버

# 환경
	JDK 1.8
	Tomcat 8.0
	MySQL 5.6 이상

# URL

## 회원
  * 가입 - POST /join
    * loginId : 로그인 아이디
    * password : 패스워드
    * name : 이름
  
  * login - POST /login
    * loginId : 로그인 아이디 
    * password : 패스워드
    
  * logout - POST /logout
  
## TODO
  * TODO 목록 - GET /todo
    * maxId : maxId 까지 조회
    * sinceId : sinceId 이후 조회
    * limit : 조회갯수
    
  * TODO 조회 - GET /todo/show
    * id :  조회할 TODO 아이디
    
  * TODO 등록 - POST /todo/insert
  	* title : 제목
  	* content : 내용
  	
  * TODO 수정 - POST /todo/update
    * id : 수정할 TODO 아이디
    * title : 제목
    * content : 내용

  * TODO 삭제 - POST /todo/delete
    * id : 삭제할 TODO 아이디
  	
  	
  
  