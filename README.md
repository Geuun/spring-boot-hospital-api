# 전국 병의원 정보 API

### Data 출처
- [공공데이터포털](https://www.data.go.kr/data/15045024/fileData.do)


### DataBase 설계

<details>
<summary>nation_wide_hospitals Table</summary>
<div>

| no | 컬럼명 | 타입 | 설명 |
| --- | --- | --- | --- |
| 1 | id(pk) | Int | 번호 |
| 2 | open_service_name | VARCHAR(10) | 개방서비스명 |
| 3 | open_local_government_code | int | 개방자치단체코드 |
| 4 | management_number(unique) | varchar(40) | 관리번호 |
| 5 | license_date | datetime | 인허가일자 |
| 6 | business_status | tinyint(2) | 1: 영업/정상2: 휴업3: 폐업4: 취소/말소영업상태구분 |
| 7 | business_status_code | tinyint(2) | 영업상태코드2: 휴업3: 폐업13: 영업중 |
| 8 | phone | varchar(20) | 소재지전화 |
| 9 | full_address | VARCHAR(200) | 소재지전체주소 |
| 10 | road_name_address | VARCHAR(200) | 도로명전체주소 |
| 11 | hospital_name | VARCHAR(20) | 사업장명(병원이름) |
| 12 | business_type_name | VARCHAR(10) | 업태구분명 |
| 13 | healthcare_provider_count | tinyint(2) | 의료인수 |
| 14 | patient_room_count | tinyint(2) | 입원실수 |
| 15 | total_number_of_beds | tinyint(2) | 병상수 |
| 16 | total_area_size | float | 총면적 |
|  |  | 이 테이블에 만들지 않음 | 진료과목내용ID(테이블 분리필요) |

</div>
</details>


### Errors

<details>
<summary>OS 문제...?</summary>
<div>

- 데이터를 읽어와보니 csv 기준 컬럼의 로우마다 `"` 가 찍혀있지않았다.
  - ex) "1", "에러", "짱시룸"
- 따라서 기존 `",` 로 split 하는 로직으로 매핑을 할 수 없음
- `,`으로 split 하려고 했으나 `도로명주소` 컬럼에 ,가 여러개 포함되어 있어서 추가 전처리 작업이 필요
  - ex) 서울시 송파구 무슨로, 123층 123,1234호 (우리집길, 우리집)
- Python Pandas를 통해 전처리 완료


</div>
</details>

<details>
<summary>object or int 를  Float으로 읽어오는 경우</summary>
<div>

- TestCode를 통과하지 못하는 문제 발생
  - Error Code : `java.lang.NumberFormatException: For input String: '1.0'`
- 처음에는 VO에서 Float으로 해당 데이터의 자료형을 변경 후 Float으로 파싱
- 하지만 생각해보니 DB에는 int로 table이 생성되어 있으니 차후 insert시 문제가 생길 것 같다는 생각
- 앞선 Python에서 전처리 과정 중 형변환이 자동으로 일어나 float으로 되었을것이라는 가정을 세우고 진입
- 실제로 데이터 타입을 찍어보니 float형태의 object였음
- 문제가 발생한 특정 컬럼의 데이터를 Pandas를 통해 전처리 후 타입 캐스팅 시도
  - int로 최종 데이터 타입 확인
- java에서 읽어왔을 때 같은 문제가 발생
- java에서 csv를 읽어올 때 발생하는 문제라 판단하고 해당 컬럼의 Float.parser의 반환 값을 int로 타입 캐스팅

</div>
</details>