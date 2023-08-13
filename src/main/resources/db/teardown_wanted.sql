-- 모든 제약 조건 비활성화
SET REFERENTIAL_INTEGRITY FALSE;
truncate table user_tb;
truncate table board_tb;
SET REFERENTIAL_INTEGRITY TRUE;
-- 모든 제약 조건 활성화

INSERT INTO `user_tb` (`id`,`email`,`password`,`roles`)  VALUES (1,'betest@nate.com','{bcrypt}$2a$10$tMrHyaQ56E36.K8vppRUvuP5cmil5IsNzn3IqTd8bjvsFNx2LdjxG','ROLE_USER'),(2,'betest22@nate.com','{bcrypt}$2a$10$6BBue75CzS8xh0Xnods.8u3kAsPuIxsw4V1eV4ZhylEn9GX6yYOz2','ROLE_USER'),(3,'betest13122@nate.com','{bcrypt}$2a$10$Jpx.ztC0rhX.Y./C.3P7SOiDAidQ1gj5x8Omu3kZUvB1knNqu1s2K','ROLE_USER'),(4,'123@nate.com','{bcrypt}$2a$10$kil3W1qvDXGbuTFtgn.Zl.0s5V.9XjIO54L5//bGYqiVPZ2dTjqoi','ROLE_USER');

INSERT INTO `board_tb` (`id`,`content`,`title`,`user_id`)  VALUES (1,'테스트2','ㅋㅋㅋㅋ',1),(2,'tnwjd','수정2',1),(3,'테스트2','ㅋㅋㅋㅋ',1),(4,'테스트2','ㅋㅋㅋㅋ',1),(5,'테스트2','ㅋㅋㅋㅋ',1),(6,'테스트2','다른작성자 22',2),(7,'테스트2','다른작성자 22',2),(8,'테스트2','다른작성자 22',2);