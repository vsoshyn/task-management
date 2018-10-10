create sequence hibernate_sequence
  increment by 10;

create table config
(
  config_name   varchar(255) not null,
  boolean_value char,
  int_value     integer,
  string_value  varchar(255),
  value_type    varchar(255),
  constraint config_pkey
  primary key (config_name)
);

create table product
(
  id           bigint not null,
  category     varchar(255),
  "group"      varchar(255),
  product      varchar(255),
  sub_category varchar(255),
  constraint product_pkey
  primary key (id)
);

create table "user"
(
  id                 bigint not null,
  created_date       date,
  email              varchar(255),
  first_name         varchar(255),
  last_modified_date date,
  last_name          varchar(255),
  password           varchar(255),
  photo              char,
  state              varchar(255),
  username           varchar(255),
  constraint user_pkey
  primary key (id),
  constraint username_uk
  unique (username)
);

create table audit
(
  id        bigint not null,
  action    varchar(255),
  date      timestamp,
  new_value varchar(255),
  old_value varchar(255),
  target    varchar(255),
  user_id   bigint,
  constraint audit_pkey
  primary key (id),
  constraint audit_user_fk
  foreign key (user_id) references "user"
);

create table cart
(
  id       bigint not null,
  owner_id bigint,
  constraint cart_pkey
  primary key (id),
  constraint cart_user_fk
  foreign key (owner_id) references "user"
);

create table task
(
  id                  bigint not null,
  created_date        timestamp,
  last_modified_date  timestamp,
  description         varchar(255),
  due_date            date,
  image               oid,
  priority            varchar(255),
  schedule            varchar(255),
  shared              char,
  state               varchar(255),
  target              varchar(255),
  created_by_id       bigint,
  last_modified_by_id bigint,
  parent_id           bigint,
  constraint task_pkey
  primary key (id),
  constraint created_user_fk
  foreign key (created_by_id) references "user",
  constraint modified_user_fk
  foreign key (last_modified_by_id) references "user",
  constraint parent_task_fk
  foreign key (parent_id) references task
);

create table cart_tasks
(
  cart bigint not null,
  task bigint not null,
  constraint task_fk
  foreign key (task) references task,
  constraint cart_fk
  foreign key (cart) references cart
);

create table maintenance
(
  m_cost              integer,
  requires_specialist char,
  company             varchar(255),
  cost                integer,
  name                varchar(255),
  phone               integer,
  task                bigint not null,
  constraint maintenance_pkey
  primary key (task),
  constraint maintenance_task_fk
  foreign key (task) references task
);

create table payment
(
  cost integer,
  task bigint not null,
  constraint payment_pkey
  primary key (task),
  constraint payment_task_fk
  foreign key (task) references task
);

create table purchase
(
  amount     integer,
  price      integer,
  unit       varchar(255),
  task       bigint not null,
  product_id bigint,
  constraint purchase_pkey
  primary key (task),
  constraint purchase_product_fk
  foreign key (product_id) references product,
  constraint purchase_task_fk
  foreign key (task) references task
);

create table statistic
(
  id      bigint not null,
  action  varchar(255),
  cost    integer,
  date    timestamp,
  task_id bigint,
  constraint statistic_pkey
  primary key (id),
  constraint statistic_task_fk
  foreign key (task_id) references task
);

create table user_authorities
(
  user_id     bigint not null,
  authorities varchar(255),
  constraint user_authorities_fk
  foreign key (user_id) references "user"
);


