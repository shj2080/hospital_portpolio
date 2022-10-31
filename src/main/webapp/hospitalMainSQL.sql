/*회원 테이블 생성 */
create table membertbl(
   name varchar(20) not null, /* 회원이름 */
   id_num varchar(20) unique,  /* 주민등록번호 */
   id varchar(15) primary key,      /* 아이디(기본키) */
   password varchar(64) not null,   /* 비밀번호 (SHA256 방식으로 암호화된 비밀번호를 저장하기 위해 컬럼크기 64지정)*/
   address1 Nvarchar(60) not null,    /* 주소 */
   address2 Nvarchar(60),    /* 주소 */
   address3 Nvarchar(60),    /* 주소 */
   postcode int not null,
   phone varchar(14)       /* 전화번호 */
);


/* 회원가입 비밀번호 허용 자리수 (20?) */
/* 회원정보 테스트 데이터 */
insert into membertbl values('홍길동', '960101-1234567', 'test001', 'eb508df11dd58cf4bb4e8ed2c5629c2d6fcb6455913c1e0e3ce2cd11a9cd7e20', '대구 달서구 달구벌대로', '테스트', '테스트데이터', 0, '010-0000-0000');

/* 암호화 비밀번호 저장을 위해 컬럼크기 변경 */
alter table membertbl
modify password varchar(64) not null;

/* 주소 컬럼 데이터형식 변경(한글60자까지 허용) */
alter table membertbl
modify address1 Nvarchar(60) not null;
alter table membertbl
modify address1 Nvarchar(60);
alter table membertbl
modify address1 Nvarchar(60);

/* Mysql 제약조건 확인(membertbl 테이블) */
select * from information_schema.table_constraints where table_name in ('membertbl');

/* 테스트데이터 업데이트 */
update membertbl set password = 'eb508df11dd58cf4bb4e8ed2c5629c2d6fcb6455913c1e0e3ce2cd11a9cd7e20' where id = 'test001';
commit;

/* 테이블 문제 발생 시 삭제 */
drop table membertbl;

/*memberTBL 테이블 생성 확인*/
select * from membertbl;

/*진료과 테이블 생성*/
/*진료과 코드, 진료과 이름*/
create table speciality(
   speciality_code int auto_increment primary key, 
   speciality_name varchar(20)
);

/* 진료과 샘플데이터 */
insert into speciality(speciality_name) values('내과');
insert into speciality(speciality_name) values('외과');
/*insert into speciality(speciality_name) values('신경과');*/
insert into speciality(speciality_name) values('산부인과');
insert into speciality(speciality_name) values('영상의학과');
insert into speciality(speciality_name) values('마취통증의학과');

/*테이블 문제 발생 시 삭제*/
drop table speciality;
delete from speciality;

/* auto_increment 초기화 */
alter table speciality auto_increment = 1;

/*speciality 테이블 생성 확인*/
select * from speciality;

/*의료진 테이블 생성
의사 코드,의사 이름, 진료과 코드*/
create table doctor(
   doctor_code int auto_increment primary key, /* 의사 코드 */
   doctor_name varchar(20) not null, 	/* 의사 이름 */
   speciality_code int REFERENCES speciality(speciality_code) /* 진료과 코드 */
);

/* 테스트를 위한 의료진 테이블 정보 입력 */
insert into doctor(doctor_name,speciality_code)
values('김이박',1);
insert into doctor(doctor_name,speciality_code)
values('박이김',1);
insert into doctor(doctor_name,speciality_code)
values('이김박',2);
insert into doctor(doctor_name,speciality_code)
values('박김이',2);
insert into doctor(doctor_name,speciality_code)
values('이박김',3);
insert into doctor(doctor_name,speciality_code)
values('최전오',3);
insert into doctor(doctor_name,speciality_code)
values('오전최',4);
insert into doctor(doctor_name,speciality_code)
values('전최오',4);
insert into doctor(doctor_name,speciality_code)
values('전오최',5);
insert into doctor(doctor_name,speciality_code)
values('오최전',5);

/*테이블 문제 발생 시 삭제*/
drop table doctor;
delete from doctor;

/* auto_increment 초기화 */
alter table doctor auto_increment = 1;

/* 데이터가 있는 상태에서 auto_increment 재정렬 */
ALTER TABLE `doctor` AUTO_INCREMENT=1;
SET @COUNT = 0;
UPDATE `doctor` SET doctor_code = @COUNT:=@COUNT+1;

/*doctor 테이블 생성 확인*/
select * from doctor;

/*진료 테이블 생성*/
/*진료코드 , 진료과 코드, 의사 코드, 아이디, 회원 이름, 진료 날짜 및 시간, 전화번호*/
create table treatment(
    treatment_code int auto_increment,  /* 진료코드 */
    speciality_code int not null,  /* 진료과 코드 */
    doctor_code int,        /* 의사코드 */
    id varchar(15) not null,     /* 아이디 */
    treatment_date datetime not null,    /* 진료 날짜 및 시간 */
    phone varchar(14),   /* 전화번호 */

    primary key(treatment_code),
    foreign key(id) REFERENCES membertbl(id),
    foreign key(speciality_code) REFERENCES speciality(speciality_code),
    foreign key(doctor_code) REFERENCES doctor(doctor_code)
);

/*테이블 문제 발생 시 삭제*/
drop table treatment;
delete from treatment;

/* auto_increment 초기화 */
alter table treatment auto_increment = 1;

/*treatment 테이블 생성 확인*/
select * from treatment;

/* treatment_date 컬럼 타입 변경 : datetime */
alter table treatment
modify treatment_date datetime not null;


/* 테스트를 위한 진료 테이블 정보 입력 */
insert into treatment(speciality_code, doctor_code, id, treatment_date, phone)
values(1,1,'wjsdudsgns','2022-10-25','010-2455-5707');
insert into treatment(speciality_code,doctor_code,id,treatment_date,phone)
values(1,2,'dleogus','2022/10/25','010-2455-5707');
insert into treatment(speciality_code,doctor_code,id,treatment_date,phone)
values(2,3,'dleogus','2022/10/25','010-2455-5707');
insert into treatment(speciality_code,doctor_code,id,treatment_date,phone)
values(2,3,'wjsdudsgns','2022/10/25','010-2455-5707');
insert into treatment(speciality_code,doctor_code,id,treatment_date,phone)
values(3,5,'rladlqkr','2022/10/25','010-2455-5707');
insert into treatment(speciality_code,doctor_code,id,treatment_date,phone)
values(3,5,'rladlqkr','2022/10/25/10:15','010-2455-5707');
insert into treatment(speciality_code,doctor_code,id,treatment_date,phone)
values(3,5,'rladlqkr','2022/10/28/10:15','010-2455-5707');

/*----------------------------------------------------------------------*/
/** ?가 들어간 SQL문은 DAO 작업 시 PrepareStatement 사용됨 **/

/* 특정 회원의 정보를 가져오는 SQL문  (HospitalDAO - selectUserInfo)*/
/* 이 메서드에선 id, name , phone만 가져옴 */
select * from membertbl where id = ?

/* 회원가입(HospitalDAO - join) */
insert into membertbl(name,id_num,id,password,address1,address2,address3,postcode,phone)
values(?,?,?,?,?,?,?,?,?)

/* 회원수정폼에서 회원 정보를 가져와 세팅해놓음 (HospitalDAO - selectMemberInfo) */
select * from membertbl where id = ?

/* 회원정보 수정[비밀번호 변경하지 않는 경우 SQL] (HospitalDAO - updateMemberInfo) */
update membertbl
set name = ?, address1 = ?, address2 = ?, address3 = ?, postcode = ?, phone = ?
where id = ?;

/* 특정 진료과의 진료과명을 얻어오는 SQL (HospitalDAO - selectSpeciality) */
select * from speciality where speciality_code = ?

/* 특정 진료과의 의사 정보 조회 (HospitalDAO - selectDoctorInfo) */
select * from doctor where speciality_code = ?;

/* 현재시간 이후의 진료예약리스트 전체 조회 (HospitalDAO - selectTreatmentList) */
select treatment_date, name, doctor_name, speciality_name
from treatment t LEFT JOIN membertbl m ON t.id = m.id
LEFT JOIN speciality spec ON t.speciality_code = spec.speciality_code
LEFT JOIN doctor d ON t.doctor_code = d.doctor_code
WHERE treatment_date > now() order by treatment_date asc;

/* 현재시간 이후의 진료예약리스트 중 특정 진료과 조회 (HospitalDAO - treatmentListSearch) */
select treatment_date, name, doctor_name, speciality_name
from treatment t LEFT JOIN membertbl m ON t.id = m.id
LEFT JOIN speciality spec ON t.speciality_code = spec.speciality_code
LEFT JOIN doctor d ON t.doctor_code = d.doctor_code
WHERE treatment_date > now() AND speciality_name = '마취통증의학과' order by treatment_date asc;

/* 진료내역 전체 조회 (사용된 곳 없음) */
select treatment_date, doctor_name, speciality_name
from treatment t LEFT JOIN membertbl m ON t.id = m.id
LEFT JOIN speciality spec ON t.speciality_code = spec.speciality_code
LEFT JOIN doctor d ON t.doctor_code = d.doctor_code
WHERE t.id = 'wjsdudsgns' order by treatment_date asc;
/**************************************************************************/
/* 게시판 관련 SQL */

