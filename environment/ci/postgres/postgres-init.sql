create user teamcity encrypted password '12345678';
create database teamcity;
grant all privileges on database teamcity to teamcity;

create user task_manager encrypted password '12345678';
grant all privileges on schema public to task_manager;