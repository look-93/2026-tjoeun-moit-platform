# 🚀 MOIT (모잇)

> **Meet + It = MOIT**
> 같은 관심사와 목표를 가진 사람들이 연결되어 함께 성장할 수 있도록 지원하는 목적형 커뮤니티 플랫폼입니다.

---

## 📌 프로젝트 소개

MOIT(모잇)은 스터디, 프로젝트, 운동, 취미 활동 등 **공통의 관심사와 목표를 가진 사람들이 모임을 만들고 참여할 수 있는 목적형 커뮤니티 플랫폼**입니다.

- **1차 개발:** MyBatis + JSP 기반의 목적형 소모임 커뮤니티 기본 기능 구축
- **2차 개발:** Spring Boot 전환 및 Open API, 기초 OpenAI GPT 연동
- **3차 개발:** **React + REST API 단일 페이지 아키텍처(SPA) 전환**, **JPA/Spring Data JPA 도입**, **JWT + Redis 기반 보안/동시성 강화**, **LLM RAG 기반 AI 판단 보조 시스템** 및 **토스페이먼츠 결제 연동**으로 enterprise급 완성도 확보

---

## 🎯 3차 고도화 기획 배경 & 목표

3차 개발에서는 기존 서비스 운영 시 발생할 수 있는 **보안 허점, 데이터 동시성 문제, 모임 노쇼 및 신뢰도 저하, 관리자 업무 과중** 문제를 해결하는 데 집중했습니다.

- **아키텍처 및 보안 전면 개편:** SSR(Thymeleaf)에서 **React(SPA) + REST API** 구조로 전환하고, **JWT + Redis** 세션 관리 및 계정 보안 센터(다중 기기 제어)를 구현하여 보안성과 UX를 극대화했습니다.
- **신뢰 기반 커뮤니티 및 노쇼 방지:** 노쇼 패널티(-5점), 완료 시 매너점수 부여(+10점), 당일 취소 제한 등 **정교한 신뢰도/노쇼 산식**을 정의하고, **동일 IP 다중 계정 추적**으로 부정 이용을 차단했습니다.
- **LLM RAG 기반 AI 판단 보조 시스템:** 신고 처리 시 운영 기준 및 과거 유사 사례 PDF를 RAG(검색 증강 생성)로 분석하여 관리자에게 **위험도 점수 산출 및 검토 방향**을 실시간으로 추천합니다.
- **데이터 동시성 및 트랜잭션 안정성:** **Redis Distributed Lock**을 적용하여 중복 신고/결제/처리 연타를 방지하고, `@Async` + `AFTER_COMMIT` 이벤트를 도입해 이메일 발송 지연에 따른 DB 트랜잭션 병목을 해소했습니다.
- **수익 모델(BM) 구축:** 토스 결제 API 연동, 기간/노출 위치별 요금 산정 및 **노출 우선순위(Priority) 가중치 알고리즘**을 도입하여 광고 시스템을 완성했습니다.

---

## 📅 프로젝트 개요

| 항목           | 내용                                                            |
| -------------- | --------------------------------------------------------------- |
| **프로젝트명** | MOIT (모잇)                                                     |
| **개발 형태**  | 팀 프로젝트                                                     |
| **1차 개발**   | 2026.06.16 ~ 2026.06.22 (Spring MVC + MyBatis + JSP)            |
| **2차 개발**   | 2026.07.02 ~ 2026.07.14 (Spring Boot + Thymeleaf)               |
| **3차 개발**   | 2026.08.12 ~ 2026.08.28 (React + Spring Data JPA + JWT + Redis) |

---

## 🔄 기술 스택 전환 (Architecture Evolution)

| 영역                    | 1·2차 개발                 | 3차 고도화 (Present)                          |
| ----------------------- | -------------------------- | --------------------------------------------- |
| **Front-End**           | HTML5, CSS3, JS, Thymeleaf | **React.js, Redux, Ant Design, Axios**        |
| **Back-End**            | Spring Boot, MyBatis       | **Spring Boot, Spring Data JPA, RESTful API** |
| **Security / Auth**     | Spring Security, Session   | **Spring Security, JWT Token, Redis**         |
| **Concurrency / Cache** | -                          | **Redis (Distributed Lock / Token / Cache)**  |
| **AI Engine**           | OpenAI Simple Prompt API   | **OpenAI LLM + RAG (운영 기준/사례 분석)**    |
| **Payment API**         | -                          | **토스페이먼츠 (Toss Payments API)**          |

---

## ✨ 3차 주요 고도화 기능

### 👤 회원 & 보안 센터 (Auth & Security)

- **인증 강화:** 이메일 및 휴대폰 본인 인증 프로세스 도입
- **계정 보안 센터:** 로그인 기기 실시간 조회, 특정/전체 기기 원격 로그아웃(Redis 기반), 신규 기기 로그인 알림
- **실시간 가입 이탈 방지 AI:** LLM 기반 회원가입 중 입력 오류 및 체류 시간 분석 가이드 제공
- **관리자 시스템:** SUPERADMIN 전용 권한 변경, 신규 관리자 승인, 회원 전체 상태(활성/정지/탈퇴) 및 페이징 관리 대시보드

### 🤝 모임 & 노쇼 방지 (Group Management)

- **포인트 기반 끌어올리기:** 포인트 차감 내역 관리 및 7일 1회 제한 쿨다운 적용
- **일일 모임 등록 제한:** 하루 최대 3개 등록 제한 (등록 건수 실시간 검증)
- **정교한 노쇼/매너 산식:**
- 모임 완료 시 비노쇼 회원 매너점수 **+10점**
- 모임 불참/노쇼 판정 시 **-5점** 차감
- 당일 모임 1시간 전 취소 시 패널티 **-5점** 적용 (단, 당일 신청 후 1시간 이내 취소 시 예외)

- **호스트 프로필/이력 노출:** 매너점수, 모임 개설 횟수, 완료율, 노쇼 이력 시각화
- **기상 변동 SMS 알림:** CoolSMS API 연동 스케줄러로 비/우천 예보 시 참가자 자동 SMS 발송

### 📝 후기 & 💬 문의 (Review & QnA)

- **후기 고도화:** 다중 이미지 등록/수정/삭제/확대, 계층형 대댓글(1-Depth/2-Depth), 조회수, 모임 종료 후 3일 뒤 작성 권장 알림, 1인 1후기 중복 제한
- **AI 문의 자동 분류:** OpenAI LLM 기반 문의 내용 분석 ➔ 로그인/결제/계정/신고/버그/기타 자동 카테고리화
- **QnA UX 개선:** 최신 10개 FIFO 알림 보관, 문의 답변 완료 후 만족도(1~5점) 평가, 답변 완료 문의 수정 제한, Redux Loading State + AntD 기반 연타/중복 등록 방지

### 🚨 신고 & AI RAG 스펙 (Report & Risk Management)

- **AI RAG (검색 증강 생성) 판단 보조:** 신고 처리 규정 및 과거 유사 사례 PDF 문서를 RAG로 분석하여 관리자에게 추천 검토 방향 및 참고 조항 제안
- **AI 위험도 점수(0~100점) 산출:** 신고 사유(ABUSE/NOSHOW 등), 90일 이내 승인 횟수, 피신고자 신뢰도 점수를 종합 산출하여 관리자 처리 우선순위 자동 정렬
- **동일 IP 다중 계정 탐지:** 노쇼 제재 승인 대상자의 IP와 동일한 IP를 사용하는 의심 계정 추적 표기
- **신고 처리 이력 Audit Log:** 처리 사유, 신뢰도 변화값, 처리 관리자 로그 기록 (`report_audit_logs`) 및 3년 경과 이력 자동 삭제 스케줄러
- **안정성 및 동시성 방어:**
- **[1차]** Client Redux Loading ➔ 연타 방지
- **[2차]** **Redis Distributed Lock** ➔ 동시 요청 처리 방지
- **[3차]** DB Transaction Lock & State Check ➔ 중복 처리 완전 차단
- `@Async` + `AFTER_COMMIT` 구조로 이메일/SMS 전송 지연이 DB 트랜잭션에 영향을 주지 않도록 분리

### 📢 광고 & 결제 시스템 (Ad & Payment)

- **토스페이먼츠(Toss Payments) 연동:** 승인된 광고 결제 요청 ➔ 토스 결제창 호출 ➔ 결제 완료 시 노출 상태 자동 전환 및 기간 연장 기능
- **광고 요금 관리:** 관리자가 기간별/노출 위치별 기본 요금 및 추가 요금 실시간 수정
- **노출 우선순위(Priority) 알고리즘:**
- CTR 및 최근 노출 빈도를 반영한 가중치 보정
- 특정 광고 과도 노출 시 가중치 차감, 신규/소외 광고 노출 기회 제공
- 동일 Priority 그룹 내 Random 선택으로 공정한 노출 보장

---

## 🛠 기술 스택 (Tech Stack)

### Front-End

- React.js, Redux / Redux Toolkit, Ant Design, Axios, HTML5/CSS3

### Back-End

- Java 17, Spring Boot, **Spring Data JPA**, **Spring Security**, **JWT (JSON Web Token)**
- Spring Async (`@Async`), Spring Scheduler (`@Scheduled`), Spring Event Listener

### Database & Cache

- **Oracle DB**, **Redis** (Token Session, Distributed Lock, Caching)

### AI & External APIs

- **OpenAI GPT-4 API & RAG System** (PDF Document Loader / Vector Search)
- **Toss Payments API** (결제 연동)
- **CoolSMS API** (알림문자)
- HIBP API, 기상청 단기예보 API, VWorld 주소 API, 네이버 MAP API

### DevOps & Tools

- Git, GitHub (GitHub Flow), Notion, Postman

---

## 👥 Team

- 팀 프로젝트 진행
- **GitHub Flow** 기반 코드 리뷰 및 브랜치 전략 준수
- **Notion**을 활용한 WBS, API 명세서, 이슈 트래킹 관리

---

## 🎥 프로젝트 시연

| 기능               | 시연 영상                                                       |
| ------------------ | --------------------------------------------------------------- |
| 회원가입 및 로그인 | [YouTube에서 보기](https://www.youtube.com/watch?v=qlZsblUcrpQ) |
| 모임 등록 및 신청  | [YouTube에서 보기](https://youtu.be/W8uqEza0bNc)                |
| 문의 등록          | [YouTube에서 보기](https://www.youtube.com/watch?v=PCCIJsmrKG8) |
| 후기 등록          | [YouTube에서 보기](https://www.youtube.com/watch?v=pyTIHHSDAqs) |
| 신고 등록          | [YouTube에서 보기](https://www.youtube.com/watch?v=QSb3lZ5VrFA) |
| 광고               | [YouTube에서 보기](https://www.youtube.com/watch?v=NZkUY0mHTUU) |
