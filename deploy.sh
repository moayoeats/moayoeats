# 애플리케이션 배포 스크립트

NOHUP_OUT_FILE=$REPOSITORY/nohup.out

# 애플리케이션 소스코드 디렉토리로 이동
REPOSITORY=/home/ubuntu/moayoeats

# shellcheck disable=SC2164
cd $REPOSITORY

# shellcheck disable=SC2046
kill -9 `ps -ef|grep java|awk '{print $2}'`

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

ls -ld $REPOSITORY
ls -ld $JAR_NAME
ls -ld /home/ubuntu/moayoeats

echo "> JAR NAME: $JAR_NAME"
chown ubuntu:ubuntu $JAR_NAME

echo "> $JAR_NAME 에 실행권한 추가"

chmod +rx $JAR_NAME

echo "> $JAR_NAME 실행"

chmod +w $NOHUP_OUT_FILE
# 환경변수 권한 부여 및 저장
chmod u+x /home/ubuntu/moayoeats/env.env
source /home/ubuntu/moayoeats/env.env

nohup java -jar $JAR_NAME > $REPOSITORY/nohup.out 2>&1 & #여기가 문제ㅔㅔㅔㅔㅔㅔㅔ!?