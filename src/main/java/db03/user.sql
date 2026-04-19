/*SQL 무결성 제약조건(CONSTRAINTS) */
/*
TABLE에 저장될 데이터에 조건을 부여하여 잘못된 데이터 입력 방지 하는 제약조건
1. PRIMARY KEY 설정 : 기본키 설정
- NOT NULL + UNIQUE 기본 적용
- CREATE TABLE 안에 한번만 설정 가능
2. NOT NULL / NULL : 필수 입력, 컬럼단위 제약조건
3. UNIQUE : 중복방지
4. CHEKC : 값의 범위 지정, DOMAIN 제약 조건
5. FOREIGN KEY : 외래키 제약조건
*/
CREATE TABLE TUSER(
	USERID VARCHAR(20) NOT NULL PRIMARY KEY,
	USERNAME VARCHAR(30) NOT NULL,
	EMAIL VARCHAR(320) UNIQUE
);
/* 데이터 추가 */
INSERT INTO TUSER(USERID, USERNAME, EMAIL) VALUES('aaaa', 'sana', 'sana@gmail.com');
INSERT INTO TUSER VALUES('abcd', 'nana', 'nana@gmail.com');
INSERT INTO TUSER VALUES('bbbb', 'hina', 'hina@gmail.com');
INSERT INTO TUSER VALUES('cccc', 'kana', 'kana@gmail.com');
INSERT INTO TUSER VALUES('oooo', 'ponyo', 'ponyo@gmail.com');
commit;

/* 데이터 수정 */
UPDATE TUSER SET USERNAME = ?, EMAIL = ?
WHERE USERID = 'eeee';
/* 데이터 삭제 */
DELETE FROM TMEM WHERE USERID = 'aaaa';







