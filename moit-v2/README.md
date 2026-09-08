# 🚀 MOIT (모잇)

> **Meet + It = MOIT**  
> 같은 관심사와 목표를 가진 사람들이 연결되어 함께 성장할 수 있도록 지원하는 목적형 커뮤니티 플랫폼입니다.

---

## 📌 프로젝트 소개

**MOIT(모잇)**은 스터디, 프로젝트, 운동, 취미 활동 등 **공통의 관심사와 목표를 가진 사람들이 모임을 만들고 참여할 수 있는 목적형 커뮤니티 플랫폼**입니다.

---

## 🎯 2차 고도화 기획 배경

1차 프로젝트에서 목적형 소모임의 기본 기능을 구축한 후, 실제 운영 및 사용성 측면에서 다음과 같은 개선점을 정의하고 2차 고도화를 진행했습니다.

- **기술적 확장성 및 보안 강화:** 기존 Spring Framework 구조의 한계를 극복하기 위해 **Spring Boot로 전환**하고, **Spring Security 및 OAuth2**를 적용하여 인증·인가 및 비밀번호 보안을 강화했습니다.
- **사용자 편의성 향상 (AI 도입):** 모임 생성 시 제목·소개글 작성 부담을 줄이고, 정교한 참가자 신뢰도 평가를 위해 **OpenAI GPT API**를 도입했습니다.
- **클린 커뮤니티 환경 조성:** 후기, 문의, 신고 등 커뮤니티 내 비속어 및 욕설을 **AI 기반으로 자동 필터링**하여 신뢰할 수 있는 환경을 마련했습니다.
- **서비스 자동화 & 외부 연동:** 기상청·지도 API 연동과 **비동기 이벤트, Scheduler, SMTP 메일**을 활용해 사용자 알림 및 게시물 관리 자동화를 구현했습니다.

---

## 📅 프로젝트 개요

| 항목           | 내용                    |
| -------------- | ----------------------- |
| **프로젝트명** | MOIT (모잇)             |
| **개발 형태**  | 팀 프로젝트             |
| **1차 개발**   | 2026.06.16 ~ 2026.06.22 |
| **2차 개발**   | 2026.07.02 ~ 2026.07.14 |

## 🎯 프로젝트 핵심 목표 & 고도화 포인트

- **Spring Boot 리팩토링:** 기존 Spring Framework에서 전환하여 프로젝트 구조 개선, 유지보수성 및 확장성 확보
- **인증 및 보안 강화:** Spring Security, OAuth2 소셜 로그인, BCrypt 암호화, HIBP API 기반 유출 검사
- **AI 기반 지능형 기능 도입:** OpenAI GPT API를 연동하여 자동 생성, 신뢰도 평가, 비속어/욕설 필터링 구현
- **Open API 연동 & 자동화:** 기상청, VWorld, 네이버 MAP API 활용 및 비동기 알림(`@Async`), 스케줄링(`@Scheduled`), SMTP 메일 발송 구현

---

## 🔄 기술 스택 전환 (1차 vs 2차)

| 영역            | 1차 개발         | 2차 개발 (고도화)                     |
| --------------- | ---------------- | ------------------------------------- |
| **Framework**   | Spring Framework | **Spring Boot**                       |
| **Database**    | MySQL            | **Oracle**                            |
| **View Engine** | JSP              | **Thymeleaf**                         |
| **Security**    |                  | **Spring Security + OAuth2 + BCrypt** |

---

## ✨ 주요 기능

### 👤 회원

- OAuth2 기반 소셜 로그인 및 BCrypt 비밀번호 암호화
- HIBP (Have I Been Pwned) API 기반 비밀번호 유출 여부 검사
- 관심사 태그 기반 사용자 정보 관리

### 🤝 모임

- **AI 기능:** OpenAI GPT API 기반 모임 제목·카테고리·소개글 자동 생성 및 참가자 신뢰도 평가
- **날씨 & 지도:** 기상청 단기예보 API 기반 모임 날씨 알림, VWorld 주소 검색 및 네이버 MAP API 연동
- **운영:** 좋아요, 끌어올리기, 모집/신청 상태 관리

### 📝 후기 & 📨 문의

- OpenAI GPT API 기반 욕설·비방·비속어 필터링
- OpenAI GPT API 기반 개설자 후기 분석
- 비동기 이벤트 기반 문의 답변 알림

### 🚨 신고 & 📢 광고

- OpenAI GPT API 기반 신고 사유 문장 및 광고 콘텐츠 자동 생성
- Scheduler 기반 광고 게시 상태 자동 관리
- SMTP 기반 신고 결과 및 광고 종료 예약 메일 발송

---

## 🛠 기술 스택 (Tech Stack)

### Front-End

- HTML5, CSS3, JavaScript, Thymeleaf

### Back-End

- Java 17+, Spring Boot, Spring Security, OAuth2, MyBatis, Spring Async & Scheduling

### Database

- Oracle

### AI & External APIs

- OpenAI GPT API
- Have I Been Pwned (HIBP) API
- 기상청 단기예보 API
- VWorld 주소 검색 API
- 네이버 MAP API
- JavaMailSender (SMTP)

### DevOps & Collaboration

- Git, GitHub (GitHub Flow), Notion

---

## 👥 Team

- 팀 프로젝트 진행
- **GitHub Flow** 기반 버전 관리 및 협업
- **Notion**을 활용한 일정 및 업무 관리

---

## 🎥 프로젝트 시연 영상

| 기능               | 링크                                                            |
| ------------------ | --------------------------------------------------------------- |
| 회원가입 및 로그인 | [YouTube에서 보기](https://www.youtube.com/watch?v=jCiTv0grZYE) |
| 모임 등록 및 신청  | [YouTube에서 보기](https://www.youtube.com/watch?v=WLSxFhWPIRs) |
| 문의               | [YouTube에서 보기](https://www.youtube.com/watch?v=eWmBrzBqTeU) |
| 후기               | [YouTube에서 보기](https://www.youtube.com/watch?v=vFFOV-ELUPY) |
| 신고               | [YouTube에서 보기](https://www.youtube.com/watch?v=BbsZr3dRHZ0) |
| 광고               | [YouTube에서 보기](https://www.youtube.com/watch?v=iv0MOgaqSUI) |
