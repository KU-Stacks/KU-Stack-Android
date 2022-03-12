# KU Ring-Android

#### 걱정마, 쿠링이 알려줄게!

## PlayStore 링크 
https://play.google.com/store/apps/details?id=com.ku_stacks.ku_ring

<p align="center">
<img src="https://github.com/KU-Stacks/KU-Ring-Android/blob/main/preview/%EC%BF%A0%EB%A7%81_%EA%B2%80%EC%83%89_%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7.png" width="30%"/>
<img src="https://github.com/KU-Stacks/KU-Ring-Android/blob/main/preview/%EC%BF%A0%EB%A7%81_%EB%82%B4%EC%95%8C%EB%A6%BC_%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7.png" width="30%"/>
<img src="https://github.com/KU-Stacks/KU-Ring-Android/blob/main/preview/%EC%BF%A0%EB%A7%81_%ED%99%88%ED%99%94%EB%A9%B4_%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7.png" width="30%"/>
</p>

## Tech Stack & Libraries

### Architecture
- MVVM Architecture
- Repository Pattern
- Dagger Hilt - 의존성 주입

### Jetpack Library
- Lifecycle(LiveData)
- DataBinding
- ViewModel
- Paging3 + Coroutine Flow
- Room
- Startup - 앱 시작 시 startup 간소화, 초기화 순서 명시
- CoordinatorLayout

### Async
- RxJava (main)
- Coroutine (sub)

### Network
- Gson
- OkHttp3
- Retrofit2 & RxJava3
- java-WebSocket

### other Library
- Timber - Debug 환경일 때는 Log, Release 환경일 때는 Crashlytics
- Firebase Crashlytics
- Firebase Analytics
- LeakCanary - 메모리 누수 탐지

### Unit Test
 - Junit4
 - Robolectric
 - Mockito

<img src="https://github.com/KU-Stacks/KU-Ring-Android/blob/main/preview/%EC%9C%A0%EB%8B%9B%20%ED%85%8C%EC%8A%A4%ED%8A%B8.JPG" width="90%"/>

## Version Updates

### v.1.1.2
  - 다크모드 추가
  - 앱 안정성 향상
  - 디자인 개선

### v.1.1.1
  - 버전 싱크 수정

### v.1.1.0
  - 첫 사용자를 위한 온보딩 추가
  - 내알림 화면에서 알림 각각 삭제 기능 추가
  - 앱 내 디자인 일부 수정

### v.1.0.2
  - 화면 간 읽음 처리 동기화
  - 내 알림 화면에서 스크롤 시 update 이슈 수정

### v.1.0.1 
  - 홈 화면에서 읽음 처리 버그 수정
  - 푸시 커스텀 관련 버그 수정
  - 검색화면에서 글자 겹침 버그 수정

### v.1.0.0 
  - 쿠링 첫 출시!
