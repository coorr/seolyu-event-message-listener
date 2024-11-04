REPOSITORY=/home/ubuntu/seolyu-message
PROJECT_NAME=seolyu-event-message-listener

# 첫 번째 인스턴스 포트 (8080)
PORT1=8090

log() {
  echo "$1" | tee -a $REPOSITORY/deploy.log
}

log ">------------------------배포 시작 $(date '+%Y-%m-%d %H:%M:%S')------------------------"

# Gradle 빌드 실행
log "> Gradle 빌드 실행"
cd $REPOSITORY/$PROJECT_NAME
./gradlew clean bootJar -Pprofile=prd

# .jar 파일 복사
log "> Build 파일 복사"
cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

# 새 애플리케이션 배포 준비
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)
chmod +x $JAR_NAME

log "> 현재 구동중인 애플리케이션(pid 확인)"
CURRENT_PID1=$(pgrep -f ".*-Dserver.port=$PORT1.*.jar")

log "> 현재 구동중인 애플리케이션 pid : $CURRENT_PID1"

if [ -z $CURRENT_PID1 ]
then
  log "> 애플리케이션이 구동 중이 아니므로 종료하지 않습니다."
else
  log "> 애플리케이션 종료 : kill -9 $CURRENT_PID1"
  sudo kill -9 $CURRENT_PID1
  log "> Waiting for 5 seconds..."
  sleep 5
fi

log "> 새 버전 배포 (PORT: $PORT1)"
nohup java -jar \
  -javaagent:/home/ubuntu/pinpoint/pinpoint-agent-3.0.0/pinpoint-bootstrap-3.0.0.jar \
  -Dpinpoint.agentId=prd-seolyu-message \
  -Dpinpoint.applicationName=prd-seolyu-message \
  -Dspring.config.location=$REPOSITORY/application-config.yaml  \
  -Dspring.config.location=$REPOSITORY/application.yaml  \
  -Dspring.profiles.active=prd  \
  -Dserver.port=$PORT1 \
  $JAR_NAME > $REPOSITORY/nohup1.out 2>&1 &


log "> 헬스 체크 시작"
for i in $(seq 1 10)
do
    RESPONSE=$(curl -s http://localhost:$PORT1/health)
    OK_COUNT=$(echo "$RESPONSE" | grep -c 'ok')

    if [ $OK_COUNT -ge 1 ]
    then
        log "> 성공적으로 시작되었습니다."
        break
    else
        log "> 아직 시작되지 않았습니다. 10초 후 다시 시도합니다."
        sleep 10
    fi

    if [ $i -eq 10 ]
    then
        log "> 시작되지 않았습니다. 배포를 중단합니다."
        exit 1
    fi
done

log ">------------------------배포 종료------------------------"

