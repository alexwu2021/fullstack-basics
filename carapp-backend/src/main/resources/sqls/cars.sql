# use test_db;
#
# create table cars
# (
#     id MEDIUMINT NOT NULL AUTO_INCREMENT,
#     make varchar(50) not  null,
#     model varchar(50) not null,
#     description varchar(200) null,
#     is_new boolean default False,
#
#     primary key (id)
# );



# CREATE USER 'test_user_1'@'localhost' IDENTIFIED BY 'Test_user_1_p';
# GRANT ALL PRIVILEGES ON test_db.* TO 'test_user_1'@'localhost';
# FLUSH PRIVILEGES;