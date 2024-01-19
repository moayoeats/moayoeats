# 애플리케이션 배포 스크립트
# 환경변수 권한 부여 및 저장
chmod u+x /home/ubuntu .env
source /home/ubuntu .env

# 애플리케이션 소스코드 디렉토리로 이동
REPOSITORY=/home/ubuntu/moayoeats
cd $REPOSITORY

# 애플리케이션 이름 정의 #1
APP_NAME=moayoeats
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep '.jar' | tail -n 1) # 가장 최신의 JAR 파일 찾기
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

# 현재 실행 중인 프로세스 확인
CURRENT_PID=$(pgrep -f $APP_NAME)

# 현재 실행 중인 애플리케이션이 없으면 종료하지 않음 #2
if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  sudo kill -15 $CURRENT_PID
  sleep 5
fi

# JAR 파일 배포 #3
echo "> $JAR_PATH 배포"
nohup java -jar /home/ubuntu/moayoeats-0.0.1-SNAPSHOT.jar > /dev/null 2> /dev/null < /dev/null &