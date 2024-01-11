# spring-base

## 기본 설정

---
### plugins
```groovy
id 'java'
id 'org.springframework.boot' version '3.2.0'
id 'io.spring.dependency-management' version '1.1.4'
```
JDK 17 버전 사용

### dependencies
```dependencies
implementation 'org.springframework.boot:spring-boot-starter-web'
testImplementation 'org.springframework.boot:spring-boot-starter-test'

compileOnly("org.projectlombok:lombok")
annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
annotationProcessor("org.projectlombok:lombok")
```
## RabbitMQ

---
비동기화 작업 처리를 위해 RabbitMQ 사용 <br>
23.12.27 RabbitMQ 설치 및 설정 <br>
https://velog.io/@choidongkuen/%EC%84%9C%EB%B2%84-SpringBoot-%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%9C-RabbitMQ-%EA%B5%AC%EC%B6%95%ED%95%98%EA%B8%B0


### plugins
```groovy
implementation 'org.springframework.boot:spring-boot-starter-amqp'
```
