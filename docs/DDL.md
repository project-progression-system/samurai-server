```sql
create table assignment
(
  assignment_id CHAR(16) FOR BIT DATA not null,
  created       timestamp             not null,
  name          varchar(1024)         not null,
  value         integer,
  teacher_id    CHAR(16) FOR BIT DATA not null,
  primary key (assignment_id)
);
create table complete
(
  completion_id CHAR(16) FOR BIT DATA not null,
  created       timestamp             not null,
  points        integer,
  assignment_id CHAR(16) FOR BIT DATA,
  student_id    CHAR(16) FOR BIT DATA not null,
  primary key (completion_id)
);
create table user_info
(
  user_id CHAR(16) FOR BIT DATA not null,
  created timestamp             not null,
  level   integer               not null,
  name    varchar(1024)         not null,
  subject varchar(255),
  type    integer               not null,
  primary key (user_id)
);
alter table assignment
  add constraint UK_8dfi9eaxdcxkv4op2o31umwa7 unique (name);
alter table user_info
  add constraint UK_21gcrpxwqst2mvhvq4mo8f6uy unique (name);
create unique index UK_cea1wsw9xij8rabqcx61dxx7q on user_info (subject);
alter table assignment
  add constraint FK64hpi9ugiftbdpv9vwlpnd3b3 foreign key (teacher_id) references user_info;
alter table complete
  add constraint FKe0rjly9hwtne4uwjib3gddygt foreign key (assignment_id) references assignment on delete cascade;
alter table complete
  add constraint FKi3rom5e8d21bohjdo3crw9vnj foreign key (student_id) references user_info on delete cascade;
create table assignment
(
  assignment_id CHAR(16) FOR BIT DATA not null,
  created       timestamp             not null,
  name          varchar(1024)         not null,
  value         integer,
  teacher_id    CHAR(16) FOR BIT DATA not null,
  primary key (assignment_id)
);
create table complete
(
  completion_id CHAR(16) FOR BIT DATA not null,
  created       timestamp             not null,
  points        integer,
  assignment_id CHAR(16) FOR BIT DATA,
  student_id    CHAR(16) FOR BIT DATA not null,
  primary key (completion_id)
);
create table user_info
(
  user_id CHAR(16) FOR BIT DATA not null,
  created timestamp             not null,
  level   integer               not null,
  name    varchar(1024)         not null,
  subject varchar(255),
  type    integer               not null,
  primary key (user_id)
);
alter table assignment
  add constraint UK_8dfi9eaxdcxkv4op2o31umwa7 unique (name);
alter table user_info
  add constraint UK_21gcrpxwqst2mvhvq4mo8f6uy unique (name);
create unique index UK_cea1wsw9xij8rabqcx61dxx7q on user_info (subject);
alter table assignment
  add constraint FK64hpi9ugiftbdpv9vwlpnd3b3 foreign key (teacher_id) references user_info;
alter table complete
  add constraint FKe0rjly9hwtne4uwjib3gddygt foreign key (assignment_id) references assignment on delete cascade;
alter table complete
  add constraint FKi3rom5e8d21bohjdo3crw9vnj foreign key (student_id) references user_info on delete cascade
```