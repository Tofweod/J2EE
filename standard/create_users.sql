DELIMITER $$

USE `j2ee`$$

DROP PROCEDURE IF EXISTS `create_users`$$
-- start_num 开始的id号,nums生成多少行数据
-- 注意由于primarykey存在，设置start_num前应知道表中有哪些数据
CREATE DEFINER=`root`@`localhost` PROCEDURE `create_users`(start_num INT,nums INT)
BEGIN
DECLARE id CHAR(11);
WHILE start_num < nums DO
SET id = CONCAT(10000000000+start_num,"");
INSERT INTO users(email,user_id,user_name,user_password,major,authority) VALUES(CONCAT(id,"@stu.ecnu.edu.cn"),id,CONCAT("测试人员",start_num),"123456","软件工程",0);
SET start_num = start_num+1;
END WHILE;

	END$$

DELIMITER ;

CALL create_users(1,1000);
