# 애플리케이션 배포 스크립트

# 애플리케이션 소스코드 디렉토리로 이동
REPOSITORY=/home/ubuntu/moayoeats

# shellcheck disable=SC2164
cd "$REPOSITORY" || exit

# 압축 해제
unzip -qq -o "$GITHUB_SHA.zip" -d "$REPOSITORY"

# 현재 실행 중인 Java 프로세스 종료
pkill -f "java -jar"

# 가장 최신 JAR 파일 선택
JAR_NAME=$(ls -t "$REPOSITORY"/*.jar | head -n 1)

# 환경변수 권한 부여 및 저장
chmod u+x "$REPOSITORY/env.env"
source "$REPOSITORY/env.env"

# JAR 파일 실행
nohup java -jar "$JAR_NAME" > "$REPOSITORY/nohup.out" 2>&1 &
