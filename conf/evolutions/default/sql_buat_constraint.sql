create table adsmanager.user_user_permission (
user_id_user                   integer not null,
user_permission_id_permission  integer not null,
constraint pk_user_user_permission primary key (user_id_user, user_permission_id_permission));


create sequence adsmanager.ads_seq;
create sequence adsmanager.ads_action_seq;
create sequence adsmanager.ads_placement_seq;
create sequence adsmanager.ads_size_seq;
create sequence adsmanager.ads_type_seq;
create sequence adsmanager.ads_transaction_seq;

create sequence adsmanager.campaign_seq;

create sequence adsmanager.deposito_seq;

create sequence adsmanager.impression_seq;

create sequence adsmanager.notificaton_seq;

create sequence adsmanager.system_preferences_seq;

create sequence adsmanager.user_seq;

create sequence adsmanager.user_contact_seq;

create sequence adsmanager.user_permission_seq;

create sequence adsmanager.user_role_seq;

create sequence adsmanager.zone_seq;

create sequence adsmanager.zone_channel_seq;

alter table adsmanager.ads add constraint fk_ads_campaign_1 foreign key (campaign_id_campaign) references adsmanager.campaign (id_campaign);
create index ix_ads_campaign_1 on adsmanager.ads (campaign_id_campaign);
alter table adsmanager.ads add constraint fk_ads_adsSize_2 foreign key (ads_size_id_ads_size) references adsmanager.ads_size (id_ads_size);
create index ix_ads_adsSize_2 on adsmanager.ads (ads_size_id_ads_size);
alter table adsmanager.ads add constraint fk_ads_adsType_3 foreign key (ads_type_id_ad_type) references adsmanager.ads_type (id_ad_type);
create index ix_ads_adsType_3 on adsmanager.ads (ads_type_id_ad_type);
alter table adsmanager.ads_action add constraint fk_ads_action_impre_4 foreign key (impression_id_impression) references adsmanager.impression (id_impression);
create index ix_ads_action_impre_4 on adsmanager.ads_action (impression_id_impression);
alter table adsmanager.ads_placement add constraint fk_ads_placement_ad_5 foreign key (ads_id_ads) references adsmanager.ads (id_ads);
create index ix_ads_placement_ad_5 on adsmanager.ads_placement (ads_id_ads);
alter table adsmanager.ads_placement add constraint fk_ads_placement_zo_6 foreign key (zone_id_zone) references adsmanager.zone (id_zone);
create index ix_ads_placement_zo_6 on adsmanager.ads_placement (zone_id_zone);
alter table adsmanager.campaign add constraint fk_campaign_id_user_7 foreign key (id_user_id_user) references adsmanager.user (id_user);
create index ix_campaign_id_user_7 on adsmanager.campaign (id_user_id_user);
alter table adsmanager.deposito add constraint fk_deposito_user_8 foreign key (user_id_user) references adsmanager.user (id_user);
create index ix_deposito_user_8 on adsmanager.deposito (user_id_user);
alter table adsmanager.deposito add constraint fk_deposito_user_va_9 foreign key (user_validator_id_user) references adsmanager.user (id_user);
create index ix_deposito_user_va_9 on adsmanager.deposito (user_validator_id_user);
alter table adsmanager.impression add constraint fk_impression_adsP_10 foreign key (ads_placement_id_ads_placement) references adsmanager.ads_placement (id_ads_placement);
create index ix_impression_adsP_10 on adsmanager.impression (ads_placement_id_ads_placement);
alter table adsmanager.notificaton add constraint fk_notificaton_use_11 foreign key (user_id_user) references adsmanager.user (id_user);
create index ix_notificaton_use_11 on adsmanager.notificaton (user_id_user);
alter table adsmanager.user_contact add constraint fk_user_contact_us_12 foreign key (user_id_user) references adsmanager.user (id_user);
create index ix_user_contact_us_12 on adsmanager.user_contact (user_id_user);
alter table adsmanager.zone add constraint fk_zone_zone_chann_13 foreign key (zone_channel_id_zone_channel) references adsmanager.zone_channel (id_zone_channel);
create index ix_zone_zone_chann_13 on adsmanager.zone (zone_channel_id_zone_channel);
alter table adsmanager.zone add constraint fk_zone_ads_size_14 foreign key (ads_size_id_ads_size) references adsmanager.ads_size (id_ads_size);
create index ix_zone_ads_size_14 on adsmanager.zone (ads_size_id_ads_size);
alter table adsmanager.ads_transaction add constraint fk_ads_transaction__7 foreign key (ads_id_ads) references adsmanager.ads (id_ads);
create index ix_ads_transaction__7 on adsmanager.ads_transaction (ads_id_ads);

alter table adsmanager.user add constraint fk_user_role_13 foreign key (role_id_role) references adsmanager.user_role (id_role);
create index ix_user_role_13 on adsmanager.user (role_id_role);


alter table adsmanager.user_user_permission add constraint fk_user_user_permi_01 foreign key (user_id_user) references adsmanager.user (id_user);
alter table adsmanager.user_user_permission add constraint fk_user_user_permi_02 foreign key (user_permission_id_permission) references adsmanager.user_permission (id_permission);



