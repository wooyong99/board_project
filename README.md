## 모두의 게시판

### 프로젝트 개요
- `프로젝트 명` : 모두의 게시판
- `프로젝트 소개` : 해당 프로젝트는  자유, 운동, 연애, 공부에 대하여 서로의 생각을 공유할 수 있는 온라인 게시판
- `개발 인원` : 1명
- `개발 기간` : 2024.08.26 ~ 2024.09.06
- `목적` : 해당 프로젝트는  자유, 운동, 연애, 공부에 대하여 서로의 생각을 공유하는 것을 목표로 합니다.
- `이용 대상` 
  - 다양한 주제에 대하여 정보를 공유하기를 원하는 사람들.
  - 운동, 공부, 연애에 대해 조언을 구하고 싶은 사람들
  - 자기 계발에 관심이 있는 사람들
- `기대 효과`
  - 공통 관심사를 가진 사람들 간 네트워크 형성
  - 다양한 사람들로부터 경험과 조언을 통해 문제 해결


<br>

### 사용 기술
- Java 17, SpringBoot 3.3.3, Spring Data JPA, Thymeleaf, Gradle, MySQL, 

<br>

### 간트차트

<img width="646" alt="image" src="https://github.com/user-attachments/assets/e497473e-78c8-4757-8d77-e373225492b2">





<br><br>

### 사용자 스토리

##### 회원

|                  유저스토리                  | 인증 |      부가사항      |
|:---------------------------------------:| :--: |:--------------:|
| 나는 방문자로서 커뮤니티 기능을 이용하기 위해서 회원가입을 하고 싶다. | ❌ | 닉네임, 이메일, 비밀번호 |
| 나는 방문자로서 커뮤니티 기능을 이용하기 위해서 로그인을 하고 싶다.  | ❌ |   이메일, 비밀번호    |
| 나는 회원 사용자로서 닉네임을 변경하기 위해 회원정보를 수정하고 싶다. | ✅ |      닉네임       |
| 나는 회원 사용자로서 비밀번호 변경하기 위해 회원정보를 수정하고 싶다. | ✅ |      비밀번호      |
| 나는 회원 사용자로서 사이트 기능을 더 이상 이용하지 않기 위해 회원탈퇴를 하고 싶다. | ✅ | |
| 나는 차단된 사용자로서 사이트 기능을 계속해서 사용하기 위해 차단 해제 문의를 하고 싶다. | ✅ | |

##### 관리자

|                   유저스토리                    | 인증 | 부가사항 |
|:------------------------------------------:|:--:|:----:|
|   나는 관리자로서 특정 회원을 확인하기 위해서 회원 검색을 하고 싶다.   |  ✅  |
| 나는 관리자로서 욕설을하는 사용자를 제재하기 위해서 회원 차단을 하고 싶다. |  ✅  |
| 나는 관리자로서 차단 사용자의 제재 해제를 위해서 회원 차단해제를 하고 싶다. |  ✅  |

##### 게시글

|                      유저스토리                      | 인증 | 부가사항 |
|:-----------------------------------------------:|:--:|:----:|
|   나는 방문자로서 관심사에 대한 정보를 획득하기 위해 게시글 검색을 하고 싶다.   |  ❌  |
|   나는 회원 사용자로서 문제해결, 질문을 하기 위해 게시글 작성을 하고 싶다.    | ✅  | 제목, 내용, 카테고리
|   나는 게시글 작성자로서 게시글 내용을 수정하기 위해 게시글 수정을 하고 싶다.   | ✅  | 제목, 내용
| 나는 게시글 작성자로서 잘못된 정보의 게시글을 삭제하기 위해 게시글 삭제 하고 싶다. | ✅  |
|       나는 방문자로서 게시글을 공감하기 위해 게시글 좋아요 기능을 이용 하고 싶다.       | ✅  |
| 나는 회원 사용자로서 부적절한 게시글을 관리자가 검토할 수 있도록 신고하기 위해 게시글 작성자를 신고하고 싶다. | ✅ | |



##### 댓글

|                     유저스토리                     | 인증 | 부가사항 |
|:---------------------------------------------:|:--:|:----:|
| 나는 회원 사용자로서 게시글에 의견, 답변을 작성하기 위해 댓글을 작성하고 싶다. |  ✅  | 내용
|     나는 답변 작성자로서 내용을 수정하기 위해 댓글을 수정하고 싶다.      |  ✅  | 내용
|     나는 답변 작성자로서 잘못 작성한 답변을 삭제하기 위해 댓글을 삭제하고 싶다.      |  ✅  | 내용
| 나는 회원 사용자로서 부적절한 댓글을 관리자가 검토할 수 있도록 신고하기 위해 댓글 작성자를 신고하고 싶다. | ✅ | |

<br><br>

  
### 요구사항
#### 기능 요구사항

<img width="701" alt="image" src="https://github.com/user-attachments/assets/d087ac2a-7e8e-40af-8207-bcfb7b637418">



#### 비기능 요구사항
<img width="666" alt="image" src="https://github.com/user-attachments/assets/3f8fbd0c-87a8-413b-a658-499ba78367da">


<br><br>

### ERD

<img width="681" alt="image" src="https://github.com/user-attachments/assets/98fb7777-8a3c-481d-9a9f-c1cdceb559be">




