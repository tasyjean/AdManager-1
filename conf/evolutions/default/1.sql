# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table adsmanager.users (
  id_user                   integer not null,
  email                     varchar(255),
  password                  varchar(255),
  front_name                varchar(255),
  last_name                 varchar(255),
  company                   varchar(255),
  role                      varchar(255),
  join_date                 timestamp,
  current_balance           integer,
  is_active                 boolean,
  profile_photo             varchar(255),
  city                      varchar(255),
  country                   varchar(255),
  validation_key            varchar(255),
  constraint pk_users primary key (id_user))
;

create sequence adsmanager.users_seq;




# --- !Downs

drop table if exists adsmanager.users cascade;

drop sequence if exists adsmanager.users_seq;
