#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=tehyoyee-springboot

echo "> Build 파일 복사"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 현재 구동 중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fl tehyoyee-springboot | grep jar | awk '{print $1}')
# 현재 수행중인 스프링 부트 애플리케이션의 프로세스 ID를 찾는다
# 실행중이면 종료하기 위해서다
# 다른 비슷한 이름일 수도 있어서 조건문으로 찾는다

echo "> 현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CUREENT_PID" ]; then
  echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CUREENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR_NAME: $JAR_NAME"

echo "> $JAR_NAME 에 실행 권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

nohup java -jar \
  -Dspring.config.location=classpath:/application.properties,classpath:/application-real.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
  -Dspring.profiles.active=real \
  $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
  # 2>&1 표준에러를 표준출력으로 바꾼다는 뜻
  # 이렇게 하지 않으면 CodeDeploy 로그에 입출력이 출력된다
  # 마지막 &는 실행작업을 background로 실행한다는 뜻