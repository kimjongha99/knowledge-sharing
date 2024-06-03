# Knowledge-sharing ⚡️
### 내가 공부하는 지식을 잘 습득하기 위해 웹사이트에 정리된 내용을 피드에 올리고 퀴즈형식으로 공부하는 웹사이트
#
#
## 프로젝트 소개 📢

- 프로젝트 기간 :
    - 2024.01.24 ~ 2024.03.06
  
- 프로젝트 설명 :
    - 학습 할때 퀴즈형식과 아티클을 보며 학습할때 효과가 좋은사람들의 니즈를 충족하기 위한  아티클 피드 , 퀴즈 학습 웹사이트입니다.







#
## 기술 스택 🔨
<div align=center> 
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/Springjpa-4FC08D?style=for-the-badge&logo=jpa&logoColor=white"> 
<img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">

  <br>
  <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> 
<img src="https://img.shields.io/badge/React.ts-20232A?style=for-the-badge&logo=react&logoColor=61DAFB">
  <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> 
  <br>

<img src="https://img.shields.io/badge/Add_Vultr_Instance-blue?style=for-the-badge">
  <img src="https://img.shields.io/badge/amazon rds-61DAFB?style=for-the-badge&logo=amazonrds&logoColor=white"> 
  <img src="https://img.shields.io/badge/amazon s3-E34F26?style=for-the-badge&logo=amazons3&logoColor=white">
<img src="https://img.shields.io/badge/Axios-5A29E4?style=for-the-badge">

  <br>

  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
  <img src="https://img.shields.io/badge/redis-DD0031?style=for-the-badge&logo=redis&logoColor=white">
  <img src="https://img.shields.io/badge/github action-000000?style=for-the-badge&logo=githubaction&logoColor=white">
<br>
  <img src="https://img.shields.io/badge/junit5-F05032?style=for-the-badge&logo=junit5&logoColor=white">
<img src="https://img.shields.io/badge/docker--compose-2496ED?style=for-the-badge&logo=docker&logoColor=white">

</div>

#
## API 문서 링크
[📝 Knowledge-sharing API ](https://www.notion.so/jonghakim99/API-6d30f6b9820e4896828f2f427d49dab3)<br>


#
## ERD 🎇

<img width="1400" alt="" src="https://github.com/kimjongha99/knowledge-sharing/assets/95283879/8777e64c-3cc4-49f7-b48b-ad9996a54b35">

#
## 서비스 아키텍처 🧩
<img width="1400" alt="" src="https://github.com/kimjongha99/knowledge-sharing/assets/95283879/4e686967-6f5b-4798-b477-9eea46d21fa7">


# 프로젝트 실행 방법




## 백엔드

```shell
git clone https://github.com/kimjongha99/knowledge-sharing.git

cd /knowledge-sharing
```

### properties 파일 설정

```shell
vim src/main/resources/application.properties
```


```properties
 아래  암호회되어있는 yml을 보며 본인의  properties 파일을 작성한다.
```

### 프로젝트 실행

```shell
./gradlew build

java -jar build/libs/knowledge-sharing-0.0.1-SNAPSHOT.jar
```

---
## 프론트엔드

```shell
git clone https://github.com/kimjongha99/knowledge-sharing.git

cd front-end
```

### env파일 설정

env 파일을 생성한다.

```shell
mkdir env

vim env/local.env
```

- local.env 파일 내부에 다음과 같이 설정한다.
- 카카오 rest_key는 [카카오 로그인 문서](https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api)를 확인하고 적용해야 한다.



### 실행

```shell
npm install

npm run local
```
