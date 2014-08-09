drop table if exists entries;
create table entries (
  id integer primary key autoincrement,
  title text not null,
  text text not null
);

drop table if exists replies;
create table replies (
  id integer primary key autoincrement,
  title text not null,
  text text not null,
  reply integer
);

drop table if exists bad_words;
create table bad_words (
 	word text not null,
 	replace text not null
);

insert into bad_words (word,replace) values ("fuck","enjoy");
insert into bad_words (word,replace) values ("bitch","best friend");
insert into bad_words (word,replace) values ("shit","chocolate ice cream");
insert into bad_words (word,replace) values ("dick","fuzzy panda");
insert into bad_words (word,replace) values ("Marty","worst TA ever");
insert into bad_words (word,replace) values ("Donald Cha","Awful student");