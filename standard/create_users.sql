DELIMITER $$

USE `j2ee`$$

DROP PROCEDURE IF EXISTS `create_users`$$

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
