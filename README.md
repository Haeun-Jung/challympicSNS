# ์ฑ๋ฆผํฝ(Challympic)๐
### "๋ชจ๋๊ฐ ํจ๊ปํ๋ ๋์ "
> ์น๊ตฌ๋ค๊ณผ ์ฌ์ํ ๋๊ฒฐ๋ถํฐ ๊ณต์ต์ ์ธ ์บ ํ์ธ๊น์ง ์ฐธ์ฌํ  ์ ์๋ SNS   
> ๋์ ๊ด์ฌ์ฌ๋ฅผ ํ ๋๋ก ํ๊ทธ๋ฅผ ์ถ์ฒํด์ฃผ๋ SNS 

<br>

### **๐ Contents**  

1๏ธโฃ [Target](#-target)   
2๏ธโฃ [Main Function](#-main-function)  
3๏ธโฃ [Challympic Service](#-challympic-service)  
4๏ธโฃ [How To Run](#-how-to-run)  
5๏ธโฃ [Team](#-team)  
6๏ธโฃ [Tech Architecture](#-tech-architecture)  
7๏ธโฃ [ERD](#-erd)  
8๏ธโฃ [API](#-api)  
9๏ธโฃ [Schedule](#-schedule)  
๐ [Cooperation Tool](#-cooperation-tool)  
โ [Improvement Plan](#-improvement-plan)

> ๋ฒ์  ๋ฐ ๋ฐฐํฌ : `exec/ํฌํ ๋งค๋ด์ผ`

<br>

## ๐ฎ Target
    โ ์ํผ ์ฝํ์ธ ๋ฅผ ์ ํธํ๋ ์ฌ๋
    โ ์ฌ์ํ ์ฌ๋ฅ์ ๋ฝ๋ด๊ณ  ์ถ์ ์ฌ๋
    โ ์บ ํ์ธ์ ํตํด ์ ํ ์ํฅ๋ ฅ์ ์ฃผ๊ณ  ์ถ์ ์ฌ๋
    โ ํ๋ณด๋ฅผ ์ํ๋ ์ธํ๋ฃจ์ธ์๋ ๊ธฐ์

<br>

## ๐ Main Function
|๊ตฌ๋ถ|๊ธฐ๋ฅ|์ค๋ช|๋น๊ณ |
|:---|:---|:---|:---|
|1|์ฑ๋ฆฐ์ง ์๋น์ค|์ฌ์ฉ์๊ฐ ์ง์  ์ฑ๋ฆฐ์ง๋ฅผ ์์ฑํ  ์ ์๋ค.<br>์ฌ์ฉ์๊ฐ ์กด์ฌํ๋ ์ฑ๋ฆฐ์ง์ ํฌ์คํธ๋ฅผ ์๋ก๋ํ  ์ ์๋ค.<br>์ฑ๋ฆฐ์ง๊ฐ ์ข๋ฃ๋๋ฉด ์ข์์๊ฐ ๋ง์ด ๋ฐ์ ํฌ์คํธ ์์ฑ์์๊ฒ ํ์ดํ์ด ๋ถ์ฌ๋๋ค.<br>์ฑ๋ฆฐ์ง ๊ตฌ๋ ๋ฐ ์ ๊ณ , ๊ณต์ ํ๊ธฐ๊ฐ ๊ฐ๋ฅํ๋ค.<br>ํฌ์คํธ ์ข์์ ๋ฐ ๋๊ธ, ์ ๊ณ ํ๊ธฐ๊ฐ ๊ฐ๋ฅํ๋ค.<br>์ฌ์ฉ์์๊ฒ ๋ง์ถค ํ๊ทธ๋ฅผ ์ถ์ฒํด์ค๋ค.<br>ํ๊ทธ์ ์ฌ์ฉ์ ๊ฒ์์ด ๊ฐ๋ฅํ๋ค.||
|2|์ฌ์ฉ์|ํ์๊ฐ์, ๋ก๊ทธ์ธ์ด ๊ฐ๋ฅํ๋ค.<br>์๋ฆผ์ ํ์ธํ  ์ ์๋ค.<br>๋คํฌ๋ชจ๋ ์ค์ ์ด ๊ฐ๋ฅํ๋ค.<br>ํ์ ์ ๋ณด(ํ๋กํ, ํ์ดํ ๋ฑ)๋ฅผ ์์ ํ  ์ ์๋ค.<br>ํํดํ  ์ ์๋ค.||
|3|์ฌ์ฉ์ ํผ๋|์ฌ์ฉ์๊ฐ ๋ง๋ค๊ฑฐ๋ ๊ตฌ๋ํ ์ฑ๋ฆฐ์ง, ์ฐธ์ฌํ๊ฑฐ๋ ์ข์์ํ ํฌ์คํธ๋ฅผ ํ์ธํ  ์ ์๋ค.<br>๋ค๋ฅธ ์ฌ์ฉ์๋ค๊ณผ ํ๋ก์ฐ๋ฅผ ๋งบ์ด ํ๋ก์๊ฐ ๋๊ตฐ์ง ํ์ธํ  ์ ์๋ค.||


<br>

## ๐ Challympic Service
### ๐ Main
- ํ๊ทธ์ ์ฌ์ฉ์ ๊ฒ์ ๊ฐ๋ฅ
- ์ธ๊ธฐ ์ฑ๋ฆฐ์ง์ ๋์ ์ ํ์ธ ๊ฐ๋ฅ

![Main](https://user-images.githubusercontent.com/53832553/154602710-167b17df-3abd-418f-a936-9448b9e94e97.png)

---

### ๐ Search
- ์ฌ์ฉ์ ๊ฒ์
- ํ๊ทธ ๊ฒ์
- ํ๊ทธ ๊ฒ์ ์ ๊ด๋ จ ์ฑ๋ฆฐ์ง์ ํฌ์คํธ ํ์ธ ๊ฐ๋ฅ

![Search](https://user-images.githubusercontent.com/53832553/154609725-5029781e-9ba4-4439-9daf-8c55785e2ec6.png)

---

### ๐ Tag Recommend
- ํ์๊ฐ์ ํ ์ ํธ ํ๊ทธ ์ ํ
- ์ฌ์ฉ์์ ์ ํธ ํ๊ทธ์ ๊ฒ์ ๋ฐ์ดํฐ๋ฅผ ๊ธฐ์ค์ผ๋ก ํ๊ทธ ์ถ์ฒ

![TagRecommend](https://user-images.githubusercontent.com/53832553/154597825-e77f9d66-055b-45ef-9d98-3bb3e6bc3a11.png)
![TagRecommend2](https://user-images.githubusercontent.com/53832553/154599978-a859687a-9598-4041-a13f-87a5cdf1b336.png)

---

### ๐ Challenge Detail
- ์ฑ๋ฆฐ์ง ์์ธ๋ณด๊ธฐ
- ์ฑ๋ฆฐ์ง์ ์ฐธ์ฌ๋ ํฌ์คํธ ํ์ธ ๊ฐ๋ฅ
- ์ฑ๋ฆฐ์ง ๊ตฌ๋ ๊ฐ๋ฅ
- ํฌ์คํธ ์ข์์, ๋๊ธ ๊ฐ๋ฅ

![ChallengeDetail](https://user-images.githubusercontent.com/53832553/154602025-a04c9237-b470-4477-83e4-de21f2fb820f.png)

---

### ๐ Challenge Upload
- ์ฑ๋ฆฐ์  ์ง๋ชฉ ๊ฐ๋ฅ(default : ์ ์ฒด ์ฐธ์ฌ)
- ์ฌ์ฉ์์๊ฒ ๋ถ์ฌ๋  ํ์ดํ ์ง์ 
- ์ข๋ฃ์ผ ์ง์ 
- ํ์ผ ํ์ ์ง์ 
- ์ฑ๋ฆฐ์ง ์์ฑ์๋ ํฌ์คํธ ์๋ก๋ ํ์

![ChallengeUpload](https://user-images.githubusercontent.com/53832553/154306702-5d11b7e0-5e38-445f-b894-34fa40a9bf9b.png)

---

### ๐ Post Upload
- ์ฑ๋ฆฐ์ง ์ง์ 
- ์ฑ๋ฆฐ์ง๋ฅผ ์ง์ ํ๋ฉด ์ง์ ๋ ํ์ผ ํ์ ๊ฒ์ฌ
- ํฌ์คํธ ์๋ก๋
- ๋๋๊ทธ์ค๋๋ ๊ฐ๋ฅ

![Post_Upload](https://user-images.githubusercontent.com/53832553/154306940-5e1aedd8-4f07-4638-91ae-142249120df9.png)

---

### ๐ User Feed
- ์ฌ์ฉ์ ํผ๋
- ์ฐธ์ฌํ๊ฑฐ๋ ์ข์์ํ ํฌ์คํธ ํ์ธ ๊ฐ๋ฅ
- ๋ง๋ค๊ฑฐ๋ ๊ตฌ๋ํ ์ฑ๋ฆฐ์ง ํ์ธ ๊ฐ๋ฅ
- ํ๋ก์ฐ, ํ๋ก์ ํ์ธ

![UserFeed](https://user-images.githubusercontent.com/53832553/154307634-d8e5e74a-b74f-47e8-ad4f-13d557e77501.png)

---

### ๐ User Feed - Following
- ๋ณธ์ธ์ ๊ณ์ ์ ํ๋ก์, ํ๋ก์ ์ฌ์ฉ์ ํ์ธ
![Following](https://user-images.githubusercontent.com/53832553/154600643-cad18a4d-1a13-4a8e-9630-cfd8fbe2fe32.png)

---

### ๐ Dark Mode
- ๋ชจ๋  ํ์ด์ง ๋คํฌ ๋ชจ๋ ๊ตฌํ

![DarkMode](https://user-images.githubusercontent.com/53832553/154602066-8c30127d-1fac-492d-8a56-f314a692e769.png)

---

### ๐ Mobile
- ๋ชจ๋  ํ์ด์ง ๋ชจ๋ฐ์ผ ํ๊ฒฝ ์ต์ ํ

![Mobile](https://user-images.githubusercontent.com/53832553/154430821-c5c7e9a7-2de8-4c41-b251-5c7c66a2aaad.png)

<br>

## ๐พ How To Run
- **Frontend**
```
npm install
npm run serve
```

- **Backend**
> ๋ณด์์ผ๋ก AWS ๊ด๋ จ properties๊ฐ ์๋ต๋์์ต๋๋ค.  
> `BACKEND/src/main/resources/` ํ์์, `aws.yml`์ ์ถ๊ฐํด์ฃผ์ธ์.
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
- ์ดํ Spring Boot๋ฅผ ์คํ


<br>

## ๐ Team

### Backend
|<img src="https://user-images.githubusercontent.com/53832553/154294418-3be4d2dd-81f5-4376-84a7-89c037ed73f2.png"  width="150" height="150"/>|<img src="https://user-images.githubusercontent.com/53832553/154294666-905e7da1-b8fd-463d-aba8-84b243a71acc.png"  width="150" height="150"/>|<img src="https://user-images.githubusercontent.com/53832553/154294596-5cfd74c8-0b0d-4d12-b965-6395d2949c09.png"  width="150" height="150"/>|
|:---:|:---:|:---:|
|๐๊ณฝํ์ค๐|๊ถ์ค๋ฒ|๋ฐ๋ฏผ์ฃผ|

### Frontend
|<img src="https://user-images.githubusercontent.com/53832553/154294716-d449a9af-8419-4cef-906e-a802320217fb.png"  width="150" height="150"/>|<img src="https://user-images.githubusercontent.com/53832553/154294517-5b333d7a-2e80-4159-ac7c-58dd96783827.png"  width="150" height="150"/>|<img src="https://user-images.githubusercontent.com/53832553/154294097-01760928-93ac-479e-b9f9-160ba9d5c1b1.png"  width="150" height="150"/>|
|:---:|:---:|:---:|
|๋ฐํ์ค|์์ ์ง|์ ํ์|

<br>

## ๐จโ๐ป Tech Architecture
![TechArchitecture](https://user-images.githubusercontent.com/53832553/154430527-09bd19d6-993f-4dc0-ae4f-5a5e77220055.png)

<br>

## ๐ ERD
![ERD](https://user-images.githubusercontent.com/53832553/154911578-1ed14b5d-f2c8-47f2-8f72-9b6ec0790d72.png)

<br>

## ๐ API
### [API ๋ช์ธ์](https://documenter.getpostman.com/view/18058395/UVkgyesU)

<br>

## ๐ Schedule
![๊ฐํธ์ฐจํธ](https://user-images.githubusercontent.com/53832553/154297830-336f494b-520c-4e0a-afb7-6c6e0a3aacd4.png)


<br>

## ๐ Cooperation Tool
- Notion
- Figma
- Jira
- Webex
- Mattermost
- Discord
- GitLab
- Postman

<br>

## โ Improvement Plan
    โ ์ฑ๋ฆฐ์ง์ ํฌ์คํธ๊น์ง ์ถ์ฒ  
    โ ์ถ์ฒ ์์คํ ๋ชจ๋ธ๋ง
    
<br>

## โญ ์์ ๋ด์ญ

    ๐ ์ผ์ฑ ์ฒญ๋ SW ์์นด๋ฐ๋ฏธ(SSAFY) ๊ณตํต ํ๋ก์ ํธ ์ฐ์์ ์์

<img src="https://user-images.githubusercontent.com/53832553/176002399-5ff45ac6-8666-4d02-82e7-01f99d613e7a.png" width="50%">
