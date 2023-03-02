## 팀원소개
|김대욱|박성현|유영우|김관희|
|:---:|:---:|:---:|:---:|
|dae-wook|seonghyun519|HEUKWU|PracticingGit|
|리더 / BE|BE|BE|BE|

## 목표
### 주제
- 게시판 형식을 구현
- 자랑자료를 올리는 이미지 게시판
- 레퍼런스 페이지 : 인스타그램, 구글이미지

### 구현목표기능
- 회원가입, 로그인 일체(회원가입, 로그인, 중복아이디, 중복 닉네임, 스프링 시큐리티를 활용한 인증 구현)
- 최신순, 인기순 정렬
- 댓글, 포스트 수정삭제
- 마이페이지, 댓글 리스트 분리
- 폼데이터 형식으로 S3서버에 이미지 업로드 기능 구현
- 페이징 처리에서 페이지 객체를 그대로 반환하지 않고 List형 Dto에 담아서 반환하도록 처리. (일반적 Dto 사용 취지와 동일)
- 리프레시 토큰을 활용한 엑세스 토큰 보안성 강화

## 트러블 슈팅
- S3 이미지 업로드시 폼데이터 한글 인코딩 이슈<br>
String origName = new String(filePath.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
- '좋아요 버튼'선택여부 true, false변환시 put과 delete대상 불일치<br>
테이블에서 탐색 대상을 UserId 에 추가로 PostId를 사용해 대상 명확히 설정
- 클라이언트 단에서 불필요한 리렌더링 현상 발생<br>
데이터 갱신시 변경된 부분만 적용할 수 있도록 GetMapping 대상을 분리(Post와 Comment)
- '좋아요 개수'변경에 따른 Post 테이블 내부 필드값 변경에 따라 Post의 최종 수정일자가 함께 변경되는 현상<br>
쿼리문을 따로 보내서 포스트 조회 메소드에서 발생하는 ModifiedAt 일자 변경을 방지<br>
(다른 대안 : '좋아요'자체를 따로 관리하는 테이블 생성해서 ModifiedAt의 영향과 분리)

### 협업관련
- Restful API URL 구축
- commit 컨벤션 준수
- GitFlow 전략 준수


## 추가자료
### 외부링크
- 시연 사이트 주소 : http://jaranghalram.s3-website.ap-northeast-2.amazonaws.com/
- 사이트 소개 영상(유튜브) : https://youtu.be/sx55BSeJgdE
- API 명세서(노션) : https://repeated-dandelion-a3f.notion.site/A-4-SA-c681d8514bdf4e8989b86cbd060859e5
- 변수이름설명(노션) : https://www.notion.so/789648d35f2741cf8ef0fbcca554cab8?v=a02e66d86cfc41788eb1fba9b51271cc
- 에러 코드(노션) : https://www.notion.so/9473e4f56cfe4b1189798ac7125fb419?v=6014e49585b3480ebd572e42ee9d9f38
- ERD(노션) : https://www.notion.so/A-4-SA-c681d8514bdf4e8989b86cbd060859e5


## 기술스택
### FE
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white"> <img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=React&logoColor=white"/> <img src="https://img.shields.io/badge/CreateReactApp-09D3AC?style=for-the-badge&logo=CreateReactApp&logoColor=white"/>  <img src="https://img.shields.io/badge/Axios-5A29E4?style=for-the-badge&logo=Axios&logoColor=white"/>  <img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black"/>  <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"/>  <img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white"/>  <img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white"/>

### BE
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>  <img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white"/>  <img src="https://img.shields.io/badge/JSONWebToken-000000?style=for-the-badge&logo=JSONWebTokens&logoColor=white"/>  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"/>  <img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black"/>  <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white"/>  <img src="https://img.shields.io/badge/LINUX-FCC624?style=for-the-badge&logo=linux&logoColor=black"/>  <img src="https://img.shields.io/badge/Ubuntu-E95420?style=for-the-badge&logo=Ubuntu&logoColor=white"/>  <img src="https://img.shields.io/badge/AmazonEC2-FF9900?style=for-the-badge&logo=AmazonEC2&logoColor=white"/>  <img src="https://img.shields.io/badge/AmazonS3-569A31?style=for-the-badge&logo=AmazonS3&logoColor=white"/>  <img src="https://img.shields.io/badge/AmazonRDS-527FFF?style=for-the-badge&logo=AmazonRDS&logoColor=white"/>  <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"/>  <img src="https://img.shields.io/badge/IntelliJIDEA-000000?style=for-the-badge&logo=IntelliJIDEA&logoColor=white"/>  <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white"/>  <img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white"/> 
