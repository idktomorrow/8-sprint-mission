# 1. 베이스 이미지 설정
FROM amazoncorretto:17

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 프로젝트 파일 복사
COPY . .

# 4. 환경 변수 설정
ENV PROJECT_NAME=discodeit
ENV PROJECT_VERSION=1.2-M8
ENV JVM_OPTS=""

# 5. Gradle Wrapper로 빌드 (실행 권한 부여 포함)
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar

# 6. 80 포트 노출
EXPOSE 80

# 7. 실행 명령어 (환경 변수 활용)
ENTRYPOINT ["sh", "-c", "java ${JVM_OPTS} -jar build/libs/${PROJECT_NAME}-${PROJECT_VERSION}.jar"]