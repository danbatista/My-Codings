CREATE database db_smime;
USE db_smime;
CREATE TABLE arquivo (
   id int auto_increment not null,
   chave_publica longblob not null,
   email varchar(255) not null,
   primary key (id)
);




