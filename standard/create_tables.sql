CREATE DATABASE j2ee;
USE j2ee;

CREATE TABLE users(
                      email CHAR(27) PRIMARY KEY,
                      user_id CHAR(11) UNIQUE,
                      user_name VARCHAR(11) NOT NULL,
                      user_password VARCHAR(15) NOT NULL,
                      major VARCHAR(8) NOT NULL,
                      authority INT NOT NULL) ENGINE INNODB;

CREATE TABLE paper_states(
                             paper_state_id INT PRIMARY KEY,
                             state VARCHAR(5) NOT NULL);
INSERT INTO paper_states VALUES(0,"待审核"),(1,"审核通过"),(-1,"审核不通过"),(2,"申请修改中"),(-2,"待修改");

CREATE TABLE papers(
                       paper_id INT PRIMARY KEY AUTO_INCREMENT,
                       paper_title VARCHAR(30) NOT NULL,
                       paper_author VARCHAR(20) NOT NULL,
                       paper_summary VARCHAR(400), -- 论文摘要
                       paper_keywords VARCHAR(30) NOT NULL,
                       paper_state_id INT NOT NULL,
                       paper_prestate_id INT DEFAULT NULL,
                       paper_url VARCHAR(100),
                       FOREIGN KEY (paper_state_id) REFERENCES paper_states(paper_state_id),
                       FOREIGN KEY (paper_prestate_id) REFERENCES paper_states(paper_state_id)) ENGINE INNODB;

CREATE TABLE project_classes(
                                project_class_id INT PRIMARY KEY AUTO_INCREMENT,
                                project_class VARCHAR(4) NOT NULL);
INSERT INTO project_classes VALUES(1,"国创"),(2,"省创"),(3,"校创");

CREATE TABLE project_states(
                               project_state_id INT PRIMARY KEY,
                               project_state VARCHAR(5) NOT NULL);
INSERT INTO project_states VALUES(1,"开发中"),(2,"待答辩"),(3,"答辩失败"),(4,"答辩成功"),(5,"申请延期中"),(6,"结项");

CREATE TABLE projects(
                         project_id INT PRIMARY KEY AUTO_INCREMENT,
                         project_name VARCHAR(30) NOT NULL,
                         project_charge_person_id CHAR(11) NOT NULL, -- 使用学工号作外键
                         project_other_people_info VARCHAR(100) NOT NULL DEFAULT "",
                         project_funds_up INT,  -- 经费
                         project_about VARCHAR(300) NOT NULL DEFAULT "", -- 项目简介
                         project_paper_id INT DEFAULT NULL,
                         project_class_id INT NOT NULL,
                         project_state_id INT NOT NULL,
                         project_prestate_id INT DEFAULT NULL,
                         project_start_time DATE NOT NULL,
                         project_end_time DATE NOT NULL, -- 项目是管理员生成的，截止时间是根据项目类型自动生成的，项目类型则是从项目申请书中获取
                         FOREIGN KEY (project_charge_person_id) REFERENCES users(user_id),
                         FOREIGN KEY (project_paper_id) REFERENCES papers(paper_id),
                         FOREIGN KEY (project_class_id) REFERENCES project_classes(project_class_id),
                         FOREIGN KEY (project_state_id) REFERENCES project_states(project_state_id),
                         FOREIGN KEY (project_prestate_id) REFERENCES project_states(project_state_id));

CREATE TABLE messages(
                         message_id INT PRIMARY KEY AUTO_INCREMENT,
                         message_topic varchar(50) NOT NULL,
                         message_user_id CHAR(11) NOT NULL,
                         message_raw_data TEXT NOT NULL,
                         message_time TIMESTAMP, -- 因为显示消息按顺序排序，使用timestamp方便计算
                         message_hasread INT); -- 0未读，1已读
CREATE INDEX user_id_find ON messages(message_user_id); -- 创建索引

CREATE TABLE project_types(
                              project_type_id INT PRIMARY KEY AUTO_INCREMENT,
                              project_class_id INT NOT NULL,
                              project_type_name VARCHAR(20) NOT NULL,
                              project_type_start_time DATE NOT NULL,
                              project_type_end_time DATE NOT NULL,
                              FOREIGN KEY (project_class_id) REFERENCES project_classes(project_class_id));

INSERT INTO project_types VALUES
                              (1,1,"22届挑战杯","2022-03-01","2022-12-31"),
                              (2,1,"22届全国大学生创新创业大赛","2022-09-01","2023-06-01"),
                              (3,2,"22届软件杯","2022-07-01","2023-01-12"),
                              (4,3,"22届大夏杯","2022-06-01","2022-12-20"),
                              (5,1,"22届ACM","2022-10-01","2022-12-31");

