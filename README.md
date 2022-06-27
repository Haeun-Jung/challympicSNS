# 챌림픽(Challympic)🏆
### "모두가 함께하는 도전"
> 친구들과 사소한 대결부터 공익적인 캠페인까지 참여할 수 있는 SNS   
> 나의 관심사를 토대로 태그를 추천해주는 SNS 

<br>

### **📚 Contents**  

1️⃣ [Target](#-target)   
2️⃣ [Main Function](#-main-function)  
3️⃣ [Challympic Service](#-challympic-service)  
4️⃣ [How To Run](#-how-to-run)  
5️⃣ [Team](#-team)  
6️⃣ [Tech Architecture](#-tech-architecture)  
7️⃣ [ERD](#-erd)  
8️⃣ [API](#-api)  
9️⃣ [Schedule](#-schedule)  
🔟 [Cooperation Tool](#-cooperation-tool)  
☑ [Improvement Plan](#-improvement-plan)

> 버전 및 배포 : `exec/포팅 매뉴얼`

<br>

## 😮 Target
    ✔ 숏폼 콘텐츠를 선호하는 사람
    ✔ 사소한 재능을 뽐내고 싶은 사람
    ✔ 캠페인을 통해 선한 영향력을 주고 싶은 사람
    ✔ 홍보를 원하는 인플루언서나 기업

<br>

## 👍 Main Function
|구분|기능|설명|비고|
|:---|:---|:---|:---|
|1|챌린지 서비스|사용자가 직접 챌린지를 생성할 수 있다.<br>사용자가 존재하는 챌린지에 포스트를 업로드할 수 있다.<br>챌린지가 종료되면 좋아요가 많이 받은 포스트 작성자에게 타이틀이 부여된다.<br>챌린지 구독 및 신고, 공유하기가 가능하다.<br>포스트 좋아요 및 댓글, 신고하기가 가능하다.<br>사용자에게 맞춤 태그를 추천해준다.<br>태그와 사용자 검색이 가능하다.||
|2|사용자|회원가입, 로그인이 가능하다.<br>알림을 확인할 수 있다.<br>다크모드 설정이 가능하다.<br>회원 정보(프로필, 타이틀 등)를 수정할 수 있다.<br>탈퇴할 수 있다.||
|3|사용자 피드|사용자가 만들거나 구독한 챌린지, 참여하거나 좋아요한 포스트를 확인할 수 있다.<br>다른 사용자들과 팔로우를 맺어 팔로워가 누군지 확인할 수 있다.||


<br>

## 🏆 Challympic Service
### 📌 Main
- 태그와 사용자 검색 가능
- 인기 챌린지와 도전왕 확인 가능

![Main](https://user-images.githubusercontent.com/53832553/154602710-167b17df-3abd-418f-a936-9448b9e94e97.png)

---

### 📌 Search
- 사용자 검색
- 태그 검색
- 태그 검색 시 관련 챌린지와 포스트 확인 가능

![Search](https://user-images.githubusercontent.com/53832553/154609725-5029781e-9ba4-4439-9daf-8c55785e2ec6.png)

---

### 📌 Tag Recommend
- 회원가입 후 선호 태그 선택
- 사용자의 선호 태그와 검색 데이터를 기준으로 태그 추천

![TagRecommend](https://user-images.githubusercontent.com/53832553/154597825-e77f9d66-055b-45ef-9d98-3bb3e6bc3a11.png)
![TagRecommend2](https://user-images.githubusercontent.com/53832553/154599978-a859687a-9598-4041-a13f-87a5cdf1b336.png)

---

### 📌 Challenge Detail
- 챌린지 상세보기
- 챌린지에 참여된 포스트 확인 가능
- 챌린지 구독 가능
- 포스트 좋아요, 댓글 가능

![ChallengeDetail](https://user-images.githubusercontent.com/53832553/154602025-a04c9237-b470-4477-83e4-de21f2fb820f.png)

---

### 📌 Challenge Upload
- 챌린저 지목 가능(default : 전체 참여)
- 사용자에게 부여될 타이틀 지정
- 종료일 지정
- 파일 형식 지정
- 챌린지 생성자는 포스트 업로드 필수

![ChallengeUpload](https://user-images.githubusercontent.com/53832553/154306702-5d11b7e0-5e38-445f-b894-34fa40a9bf9b.png)

---

### 📌 Post Upload
- 챌린지 지정
- 챌린지를 지정하면 지정된 파일 형식 검사
- 포스트 업로드
- 드래그앤드랍 가능

![Post_Upload](https://user-images.githubusercontent.com/53832553/154306940-5e1aedd8-4f07-4638-91ae-142249120df9.png)

---

### 📌 User Feed
- 사용자 피드
- 참여하거나 좋아요한 포스트 확인 가능
- 만들거나 구독한 챌린지 확인 가능
- 팔로우, 팔로워 확인

![UserFeed](https://user-images.githubusercontent.com/53832553/154307634-d8e5e74a-b74f-47e8-ad4f-13d557e77501.png)

---

### 📌 User Feed - Following
- 본인의 계정의 팔로워, 팔로잉 사용자 확인
![Following](https://user-images.githubusercontent.com/53832553/154600643-cad18a4d-1a13-4a8e-9630-cfd8fbe2fe32.png)

---

### 📌 Dark Mode
- 모든 페이지 다크 모드 구현

![DarkMode](https://user-images.githubusercontent.com/53832553/154602066-8c30127d-1fac-492d-8a56-f314a692e769.png)

---

### 📌 Mobile
- 모든 페이지 모바일 환경 최적화

![Mobile](https://user-images.githubusercontent.com/53832553/154430821-c5c7e9a7-2de8-4c41-b251-5c7c66a2aaad.png)

<br>

## 💾 How To Run
- **Frontend**
```
npm install
npm run serve
```

- **Backend**
> 보안으로 AWS 관련 properties가 생략되었습니다.  
> `BACKEND/src/main/resources/` 하위에, `aws.yml`을 추가해주세요.
```aws.yml
cloud:
  aws:
    credentials:
      access-key: <your-aws-access-key>
      secret-key: <your-aws-secret-key>
    s3:
      bucket: <your-bucket-name> # recommend name is challympic
    region:
      static: ap-northeast-2
    stack:
      auto: false
```
- 이후 Spring Boot를 실행


<br>

## 😀 Team

### Backend
|<img src="https://user-images.githubusercontent.com/53832553/154294418-3be4d2dd-81f5-4376-84a7-89c037ed73f2.png"  width="150" height="150"/>|<img src="https://user-images.githubusercontent.com/53832553/154294666-905e7da1-b8fd-463d-aba8-84b243a71acc.png"  width="150" height="150"/>|<img src="https://user-images.githubusercontent.com/53832553/154294596-5cfd74c8-0b0d-4d12-b965-6395d2949c09.png"  width="150" height="150"/>|
|:---:|:---:|:---:|
|👑곽현준👑|권오범|박민주|

### Frontend
|<img src="https://user-images.githubusercontent.com/53832553/154294716-d449a9af-8419-4cef-906e-a802320217fb.png"  width="150" height="150"/>|<img src="https://user-images.githubusercontent.com/53832553/154294517-5b333d7a-2e80-4159-ac7c-58dd96783827.png"  width="150" height="150"/>|<img src="https://user-images.githubusercontent.com/53832553/154294097-01760928-93ac-479e-b9f9-160ba9d5c1b1.png"  width="150" height="150"/>|
|:---:|:---:|:---:|
|박혜준|원유진|정하은|

<br>

## 👨‍💻 Tech Architecture
![TechArchitecture](https://user-images.githubusercontent.com/53832553/154430527-09bd19d6-993f-4dc0-ae4f-5a5e77220055.png)

<br>

## 🎈 ERD
![ERD](https://user-images.githubusercontent.com/53832553/154911578-1ed14b5d-f2c8-47f2-8f72-9b6ec0790d72.png)

<br>

## 🍏 API
### [API 명세서](https://documenter.getpostman.com/view/18058395/UVkgyesU)

<br>

## 📆 Schedule
![간트차트](https://user-images.githubusercontent.com/53832553/154297830-336f494b-520c-4e0a-afb7-6c6e0a3aacd4.png)


<br>

## 👊 Cooperation Tool
- Notion
- Figma
- Jira
- Webex
- Mattermost
- Discord
- GitLab
- Postman

<br>

## ✅ Improvement Plan
    ✔ 챌린지와 포스트까지 추천  
    ✔ 추천 시스템 모델링
    
<br>

## ⭐ 수상 내역

    🏆 삼성 청년 SW 아카데미(SSAFY) 공통 프로젝트 우수상 수상

<img src="https://user-images.githubusercontent.com/53832553/176002399-5ff45ac6-8666-4d02-82e7-01f99d613e7a.png" width="50%">
