# OY VR STORE:올리브영 가상 매장

## Description

### a-frame 기반 vr 매장

# TODAYWORKING
## 오늘은 출근킹 사용법
1) data 폴더에 data.txt 파일 생성한다.
2) data.txt 파일 안에 "@"를 구분자로 한 데이터를 입력한다.
3) todayworking.exe 를 실행하여 샘플 데이터가 정상적으로 세팅되는지 확인한다.
4) 출근 입력 시스템에 로그인한다. (전체화면)
5) 출근 입력 페이지의 달력 선택 화면으로 이동한다.
6) 달력 아이콘 정중앙의 좌표를 오늘도 출근킹의 좌표얻기 기능을 사용하여 구한다.
7) 달력 아이콘을 클릭하여 달력이 표시되도록 한다.
8) 오늘도 출근킹의 좌표얻기 기능을 사용하여 입력하고자 하는 날짜의 좌표를 구한다.
9) 출근 입력 페이지가 전체화면으로 보이도록 한다.
10) 달력인식 버튼을 클릭하여 달력 이미지가 정상적으로 인식되는지 확인한다.
11) START 버튼 클릭하여 입력 시작한다.
12) 정상적으로 입력이 되고 있는지 확인하고 입력중에는 별도의 컴퓨터 조작을 하지 않는다.
    점심시간을 활용하세요~^^

## 데이터 샘플
### 구분자 "@"로 구분(이름/출근 확인방법/내용)
    남기훈@유선@5.30. 9:55/정수현 부장/010-9433-0803/근로확인
    김현정@유선@5.31. 13:42/김수진 사장/010-9887-1111/근로확인
    정해인@모바일@5.31. 13:42/이민혁 대리/010-1234-3121/근로확인

## 파일 구성
### 1) 설정 프로퍼티
    ./config/config.properties
### 2) 입력 데이터
    ./data/data.txt
### 3) 이미지
    ./img/cwmaa.png
    ./img/cwmaa_background.png
    ./img/cal_icon.png
### 4) 실행 파일
    ./todayworking.exe
