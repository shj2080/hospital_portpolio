--회원 테이블 생성
--회원이름, 주민등록번호, 아이디, 비밀번호, 주소, 전화번호
create table membertbl(
   name varchar(20) not null, /* 회원이름 */
   id_num varchar(20) unique,  /* 주민등록번호 */
   id varchar(15) primary key,      /* 아이디(기본키) */
   password varchar(20) not null,   /* 비밀번호 */  
   address varchar(50) not null,    /* 주소 */
   phone varchar(14)       /* 전화번호 */
);

--테이블 문제 발생 시 삭제
drop table membertbl;

--memberTBL 테이블 생성 확인
select * from membertbl;


--진료과 테이블 생성
--진료과 코드, 진료과 이름
create table speciality(
   speciality_code int auto_increment primary key, 
   speciality_name varchar(20)
);

--테이블 문제 발생 시 삭제
drop table speciality;

--speciality 테이블 생성 확인
select * from speciality;

--의료진 테이블 생성
--의사 코드,의사 이름, 진료과 코드
create table doctor(
   doctor_code int auto_increment primary key, /* 의사 코드 */
   doctor_name varchar(20) 	/* 의사 이름 */
);

--테이블 문제 발생 시 삭제
drop table doctor;

--doctor 테이블 생성 확인
select * from doctor;

--진료 테이블 생성
--진료코드 , 진료과 코드, 의사 코드, 아이디, 회원 이름, 진료 날짜 및 시간, 전화번호
create table treatment(
    treatment_code int auto_increment,  /* 진료코드 */
    speciality_code int not null,  /* 진료과 코드 */
    doctor_code int,        /* 의사코드 */
    id varchar(15) not null,     /* 아이디 */
    treatment_date date not null,    /* 진료 날짜 및 시간 */
    phone varchar(14),   /* 전화번호 */

    primary key(treatment_code),
    foreign key(id) REFERENCES member_tbl(id),
    foreign key(speciality_code) REFERENCES speciality(speciality_code),
    foreign key(doctor_code) REFERENCES doctor(doctor_code)
);

--테이블 문제 발생 시 삭제
drop table treatment;

--treatment 테이블 생성 확인
select * from treatment;
/*
--예약 테이블 생성
--예약코드 --진료코드  --아이디 --회원이름 --진료과 코드 --의사 코드 --예약 날짜 및 시간 --전화번호
create table reservation(
    reservation_code int,   
    treatment_code int,
    id varchar(15),
    name varchar(20) not null, 
    speciality_code varchar(5) not null, 
    doctor_code varchar(20) not null, 
    reservation_date date not null, 
    phone varchar(14), 
    
    primary key(reservation_code, id)
);

--reservation 테이블 생성 확인
select * from reservation;

*/

/*
--진료대기 테이블 생성
--진료코드 ,진료과 코드, 의사 코드, 아이디, 회원 이름
create table treatment_waiting(
	treatment_code varchar(5) primary key,
   speciality_code int not null, 
   doctor_code varchar(5), 
   id varchar(15), 
   name varchar(20) not null, 
   foreign key(treatment_code) REFERENCES treatment(treatment_code),
   foreign key(doctor_code) REFERENCES doctor(doctor_code)
);

drop table treatment_waiting;
--treatment_waiting 테이블 생성 확인
select * from treatment_waiting;
*/