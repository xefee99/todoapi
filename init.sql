

/* 사용자 생성 */ 
create user 'seoultech'@'localhost' identified by 'seoultech';
/* 데이터베이스 생성 */
create database seoultech default charset = 'utf8';
/* 사용자에게 데이터베이스 권한부여 */
grant all privileges on seoultech.* to 'seoultech'@'localhost';


/** 테이블 생성 */
create table tb_user (
	id int primary key auto_increment,
	name varchar(100) not null,
	login_id varchar(100) not null,
	password varchar(400) not null,
	token varchar(400)
)
  engine = innodb
  default charset = utf8;

create table tb_todo (
	id int primary key auto_increment,
	title varchar(200) not null,
	content text,
	create_at datetime,
	user_id int not null
)
  engine = innodb
  default charset = utf8;

  
/** 초기데이터 */  
insert into tb_user(name, login_id, password, token) values ('서울과기대', 'seoultech', '1234', '65470116-2d52-416e-9d39-9ca785fdcc42'); 
  
insert into tb_todo(title, content, create_at, user_id) values 
('테스트', '테스트내용', '2017-06-28', 1), 
('테스트2', '테스트내용2', '2017-06-28', 1),
('테스트3', '테스트내용3', '2017-06-28', 1),
('테스트4', '테스트내용4', '2017-06-28', 1),
('테스트5', '테스트내용5', '2017-06-28', 1),
('테스트6', '테스트내용6', '2017-06-28', 1),
('테스트7', '테스트내용7', '2017-06-28', 1),
('테스트8', '테스트내용8', '2017-06-28', 1),
('테스트9', '테스트내용9', '2017-06-28', 1),
('테스트10', '테스트내용10', '2017-06-28', 1),
('테스트11', '테스트내용11', '2017-06-28', 1),
('테스트12', '테스트내용12', '2017-06-28', 1),
('테스트13', '테스트내용13', '2017-06-28', 1),
('테스트14', '테스트내용14', '2017-06-28', 1),
('테스트15', '테스트내용15', '2017-06-28', 1),
('테스트16', '테스트내용16', '2017-06-28', 1),
('테스트17', '테스트내용17', '2017-06-28', 1),
('테스트18', '테스트내용18', '2017-06-28', 1),
('테스트19', '테스트내용19', '2017-06-28', 1),
('테스트20', '테스트내용20', '2017-06-28', 1),
('테스트21', '테스트내용21', '2017-06-28', 1);


  
  
  
  
  
  
  