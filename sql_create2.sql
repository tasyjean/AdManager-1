--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.4
-- Dumped by pg_dump version 9.1.4
-- Started on 2013-05-20 21:30:18

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 7 (class 2615 OID 29137)
-- Name: adsmanager; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA adsmanager;


ALTER SCHEMA adsmanager OWNER TO postgres;

SET search_path = adsmanager, pg_catalog;

--
-- TOC entry 546 (class 1247 OID 29908)
-- Dependencies: 7
-- Name: action_type_enum; Type: TYPE; Schema: adsmanager; Owner: postgres
--

CREATE TYPE action_type_enum AS ENUM (
    'click',
    'hide',
    'like'
);


ALTER TYPE adsmanager.action_type_enum OWNER TO postgres;

--
-- TOC entry 523 (class 1247 OID 29699)
-- Dependencies: 7
-- Name: campaign_type_enum; Type: TYPE; Schema: adsmanager; Owner: postgres
--

CREATE TYPE campaign_type_enum AS ENUM (
    'contract',
    'exclusive'
);


ALTER TYPE adsmanager.campaign_type_enum OWNER TO postgres;

--
-- TOC entry 519 (class 1247 OID 29668)
-- Dependencies: 7
-- Name: contact_type_enum; Type: TYPE; Schema: adsmanager; Owner: postgres
--

CREATE TYPE contact_type_enum AS ENUM (
    'address''email',
    'private_phone',
    'company_phone',
    'alternative_phone',
    'social_profile',
    'other'
);


ALTER TYPE adsmanager.contact_type_enum OWNER TO postgres;

--
-- TOC entry 526 (class 1247 OID 29704)
-- Dependencies: 7
-- Name: pricing_model_enum; Type: TYPE; Schema: adsmanager; Owner: postgres
--

CREATE TYPE pricing_model_enum AS ENUM (
    'cpm',
    'cpa',
    'flat'
);


ALTER TYPE adsmanager.pricing_model_enum OWNER TO postgres;

--
-- TOC entry 515 (class 1247 OID 29649)
-- Dependencies: 7
-- Name: user_role_enum; Type: TYPE; Schema: adsmanager; Owner: postgres
--

CREATE TYPE user_role_enum AS ENUM (
    'administrator',
    'management',
    'advertiser'
);


ALTER TYPE adsmanager.user_role_enum OWNER TO postgres;

--
-- TOC entry 536 (class 1247 OID 29836)
-- Dependencies: 7
-- Name: zone_default_view_enum; Type: TYPE; Schema: adsmanager; Owner: postgres
--

CREATE TYPE zone_default_view_enum AS ENUM (
    'empty',
    'default_code',
    'default_ads'
);


ALTER TYPE adsmanager.zone_default_view_enum OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 29747)
-- Dependencies: 7
-- Name: ads_seq; Type: SEQUENCE; Schema: adsmanager; Owner: postgres
--

CREATE SEQUENCE ads_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adsmanager.ads_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 173 (class 1259 OID 29749)
-- Dependencies: 1973 1974 1975 1976 1977 1978 7
-- Name: ads; Type: TABLE; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

CREATE TABLE ads (
    id_ads integer DEFAULT nextval('ads_seq'::regclass) NOT NULL,
    id_campaign integer DEFAULT 1,
    id_ads_size integer DEFAULT 1,
    id_ads_type integer DEFAULT 1,
    name character varying(45),
    description text,
    title character varying(45),
    content_text character varying(45),
    content_link character varying(200),
    target character varying(200),
    alt_text character varying(250),
    weight integer,
    impression_count integer,
    click_count integer,
    like_count integer,
    hide_count integer,
    isactive boolean DEFAULT true,
    isdeleted boolean DEFAULT false
);


ALTER TABLE adsmanager.ads OWNER TO xenovon;

--
-- TOC entry 186 (class 1259 OID 29905)
-- Dependencies: 7
-- Name: id_ads_action_seq; Type: SEQUENCE; Schema: adsmanager; Owner: postgres
--

CREATE SEQUENCE id_ads_action_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adsmanager.id_ads_action_seq OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 29915)
-- Dependencies: 2000 2001 7 546
-- Name: ads_action; Type: TABLE; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

CREATE TABLE ads_action (
    id_ads_action integer DEFAULT nextval('id_ads_action_seq'::regclass) NOT NULL,
    id_impression integer DEFAULT 1,
    action_type action_type_enum,
    "timestamp" timestamp without time zone
);


ALTER TABLE adsmanager.ads_action OWNER TO xenovon;

--
-- TOC entry 183 (class 1259 OID 29867)
-- Dependencies: 1994 1995 1996 7
-- Name: ads_placement; Type: TABLE; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

CREATE TABLE ads_placement (
    id_ads_placement integer DEFAULT nextval('ads_placement'::regclass) NOT NULL,
    id_ads integer DEFAULT 1,
    id_zone integer DEFAULT 1
);


ALTER TABLE adsmanager.ads_placement OWNER TO xenovon;

--
-- TOC entry 182 (class 1259 OID 29865)
-- Dependencies: 7
-- Name: ads_placement_seq; Type: SEQUENCE; Schema: adsmanager; Owner: postgres
--

CREATE SEQUENCE ads_placement_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adsmanager.ads_placement_seq OWNER TO postgres;

--
-- TOC entry 168 (class 1259 OID 29725)
-- Dependencies: 7
-- Name: ads_size_seq; Type: SEQUENCE; Schema: adsmanager; Owner: postgres
--

CREATE SEQUENCE ads_size_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adsmanager.ads_size_seq OWNER TO postgres;

--
-- TOC entry 169 (class 1259 OID 29727)
-- Dependencies: 1971 7
-- Name: ads_size; Type: TABLE; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

CREATE TABLE ads_size (
    id_ads_size integer DEFAULT nextval('ads_size_seq'::regclass) NOT NULL,
    name character varying(45),
    width integer,
    height integer,
    description text
);


ALTER TABLE adsmanager.ads_size OWNER TO xenovon;

--
-- TOC entry 174 (class 1259 OID 29778)
-- Dependencies: 7
-- Name: id_ads_transaction_seq; Type: SEQUENCE; Schema: adsmanager; Owner: postgres
--

CREATE SEQUENCE id_ads_transaction_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adsmanager.id_ads_transaction_seq OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 29780)
-- Dependencies: 1979 1980 1981 1982 7 526
-- Name: ads_transaction; Type: TABLE; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

CREATE TABLE ads_transaction (
    id_ads_transaction integer DEFAULT nextval('id_ads_transaction_seq'::regclass) NOT NULL,
    id_user integer DEFAULT 1,
    id_ads integer DEFAULT 1,
    transaction_type pricing_model_enum,
    amount integer,
    current_balance integer,
    "timestamp" timestamp without time zone,
    isdeleted boolean DEFAULT false
);


ALTER TABLE adsmanager.ads_transaction OWNER TO xenovon;

--
-- TOC entry 170 (class 1259 OID 29736)
-- Dependencies: 7
-- Name: ads_type_seq; Type: SEQUENCE; Schema: adsmanager; Owner: postgres
--

CREATE SEQUENCE ads_type_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adsmanager.ads_type_seq OWNER TO postgres;

--
-- TOC entry 171 (class 1259 OID 29738)
-- Dependencies: 1972 7
-- Name: ads_type; Type: TABLE; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

CREATE TABLE ads_type (
    id_ads_type integer DEFAULT nextval('ads_type_seq'::regclass) NOT NULL,
    name character varying(45),
    description text,
    code text
);


ALTER TABLE adsmanager.ads_type OWNER TO xenovon;

--
-- TOC entry 166 (class 1259 OID 29696)
-- Dependencies: 7
-- Name: id_campaign_seq; Type: SEQUENCE; Schema: adsmanager; Owner: postgres
--

CREATE SEQUENCE id_campaign_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adsmanager.id_campaign_seq OWNER TO postgres;

--
-- TOC entry 167 (class 1259 OID 29711)
-- Dependencies: 1967 1968 1969 1970 523 7 526
-- Name: campaign; Type: TABLE; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

CREATE TABLE campaign (
    id_campaign integer DEFAULT nextval('id_campaign_seq'::regclass) NOT NULL,
    id_user integer DEFAULT 1,
    campaign_type campaign_type_enum,
    start_date date,
    end_date date,
    pricing_model pricing_model_enum,
    limit_impression integer,
    limit_click integer,
    current_impression integer,
    current_click integer,
    bid_price integer,
    isactivated boolean DEFAULT true,
    isdeleted boolean DEFAULT false
);


ALTER TABLE adsmanager.campaign OWNER TO xenovon;

--
-- TOC entry 176 (class 1259 OID 29799)
-- Dependencies: 7
-- Name: id_deposito_seq; Type: SEQUENCE; Schema: adsmanager; Owner: postgres
--

CREATE SEQUENCE id_deposito_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adsmanager.id_deposito_seq OWNER TO postgres;

--
-- TOC entry 177 (class 1259 OID 29801)
-- Dependencies: 1983 1984 1985 1986 1987 7
-- Name: deposito; Type: TABLE; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

CREATE TABLE deposito (
    id_deposito integer DEFAULT nextval('id_deposito_seq'::regclass) NOT NULL,
    id_user integer DEFAULT 1,
    id_user_validator integer DEFAULT 1,
    amount integer,
    current_balance integer,
    description integer,
    timestamp_created timestamp without time zone,
    timestamp_validated timestamp without time zone,
    transfer_evidence_file character varying(200),
    payment_method character varying(45),
    isvalidated boolean DEFAULT false,
    isdeleted boolean DEFAULT false
);


ALTER TABLE adsmanager.deposito OWNER TO xenovon;

--
-- TOC entry 184 (class 1259 OID 29885)
-- Dependencies: 7
-- Name: id_impression_seq; Type: SEQUENCE; Schema: adsmanager; Owner: postgres
--

CREATE SEQUENCE id_impression_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adsmanager.id_impression_seq OWNER TO postgres;

--
-- TOC entry 164 (class 1259 OID 29665)
-- Dependencies: 7
-- Name: id_user_contact_seq; Type: SEQUENCE; Schema: adsmanager; Owner: postgres
--

CREATE SEQUENCE id_user_contact_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adsmanager.id_user_contact_seq OWNER TO postgres;

--
-- TOC entry 162 (class 1259 OID 29646)
-- Dependencies: 7
-- Name: id_user_seq; Type: SEQUENCE; Schema: adsmanager; Owner: postgres
--

CREATE SEQUENCE id_user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adsmanager.id_user_seq OWNER TO postgres;

--
-- TOC entry 178 (class 1259 OID 29821)
-- Dependencies: 7
-- Name: id_zone_channel_seq; Type: SEQUENCE; Schema: adsmanager; Owner: postgres
--

CREATE SEQUENCE id_zone_channel_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adsmanager.id_zone_channel_seq OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 29833)
-- Dependencies: 7
-- Name: id_zone_seq; Type: SEQUENCE; Schema: adsmanager; Owner: postgres
--

CREATE SEQUENCE id_zone_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adsmanager.id_zone_seq OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 29887)
-- Dependencies: 1997 1998 1999 7
-- Name: impression; Type: TABLE; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

CREATE TABLE impression (
    id_impression integer DEFAULT nextval('id_impression_seq'::regclass) NOT NULL,
    id_ads integer DEFAULT 1,
    id_zone integer DEFAULT 1,
    "timestamp" timestamp without time zone,
    viewer_ip character varying(45),
    viewer_source character varying(400)
);


ALTER TABLE adsmanager.impression OWNER TO xenovon;

--
-- TOC entry 190 (class 1259 OID 29938)
-- Dependencies: 7
-- Name: notification_seq; Type: SEQUENCE; Schema: adsmanager; Owner: postgres
--

CREATE SEQUENCE notification_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adsmanager.notification_seq OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 29940)
-- Dependencies: 2003 2004 7
-- Name: notification; Type: TABLE; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

CREATE TABLE notification (
    id_notification integer DEFAULT nextval('notification_seq'::regclass) NOT NULL,
    id_user integer,
    notification_type integer,
    "timestamp" timestamp without time zone,
    link character varying(200),
    isread boolean DEFAULT false
);


ALTER TABLE adsmanager.notification OWNER TO xenovon;

--
-- TOC entry 188 (class 1259 OID 29927)
-- Dependencies: 7
-- Name: system_preferences_seq; Type: SEQUENCE; Schema: adsmanager; Owner: postgres
--

CREATE SEQUENCE system_preferences_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE adsmanager.system_preferences_seq OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 29929)
-- Dependencies: 2002 7
-- Name: system_preferences; Type: TABLE; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

CREATE TABLE system_preferences (
    id_system_preferences integer DEFAULT nextval('system_preferences_seq'::regclass) NOT NULL,
    system_preference_key character varying(45),
    value text
);


ALTER TABLE adsmanager.system_preferences OWNER TO xenovon;

--
-- TOC entry 163 (class 1259 OID 29655)
-- Dependencies: 1963 1964 7 515
-- Name: user; Type: TABLE; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

CREATE TABLE "user" (
    id_user integer DEFAULT nextval('id_user_seq'::regclass) NOT NULL,
    email character varying(45),
    password character varying(80),
    front_name character varying(45),
    last_name character varying(45),
    company character varying(45),
    role user_role_enum,
    join_date date,
    current_balance integer,
    isactive boolean DEFAULT true,
    profile_photo character varying(200),
    city character varying(45),
    country character varying(45),
    validation_key character varying(100)[]
);


ALTER TABLE adsmanager."user" OWNER TO xenovon;

--
-- TOC entry 165 (class 1259 OID 29681)
-- Dependencies: 1965 1966 519 7
-- Name: user_contact; Type: TABLE; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

CREATE TABLE user_contact (
    id_user_contact integer DEFAULT nextval('id_user_contact_seq'::regclass) NOT NULL,
    id_user integer DEFAULT 1,
    contact_value character varying(100),
    contact_type contact_type_enum,
    contact_description text
);


ALTER TABLE adsmanager.user_contact OWNER TO xenovon;

--
-- TOC entry 181 (class 1259 OID 29843)
-- Dependencies: 1990 1991 1992 1993 536 7
-- Name: zone; Type: TABLE; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

CREATE TABLE zone (
    id_zone integer DEFAULT nextval('id_zone_seq'::regclass) NOT NULL,
    id_zone_channel integer DEFAULT 1,
    id_ads_size integer DEFAULT 1,
    zone_type character varying(40),
    description text,
    zone_default_view zone_default_view_enum,
    zone_default_code text,
    isdeleted boolean DEFAULT false
);


ALTER TABLE adsmanager.zone OWNER TO xenovon;

--
-- TOC entry 179 (class 1259 OID 29823)
-- Dependencies: 1988 1989 7
-- Name: zone_channel; Type: TABLE; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

CREATE TABLE zone_channel (
    id_zone_channel integer DEFAULT nextval('id_zone_channel_seq'::regclass) NOT NULL,
    channel_name character varying(45),
    channel_description text,
    isdeleted boolean DEFAULT false
);


ALTER TABLE adsmanager.zone_channel OWNER TO xenovon;

--
-- TOC entry 2030 (class 2606 OID 29921)
-- Dependencies: 187 187
-- Name: ads_action_pkey; Type: CONSTRAINT; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

ALTER TABLE ONLY ads_action
    ADD CONSTRAINT ads_action_pkey PRIMARY KEY (id_ads_action);


--
-- TOC entry 2016 (class 2606 OID 29762)
-- Dependencies: 173 173
-- Name: ads_pkey; Type: CONSTRAINT; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

ALTER TABLE ONLY ads
    ADD CONSTRAINT ads_pkey PRIMARY KEY (id_ads);


--
-- TOC entry 2026 (class 2606 OID 29874)
-- Dependencies: 183 183
-- Name: ads_placement_pkey; Type: CONSTRAINT; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

ALTER TABLE ONLY ads_placement
    ADD CONSTRAINT ads_placement_pkey PRIMARY KEY (id_ads_placement);


--
-- TOC entry 2012 (class 2606 OID 29735)
-- Dependencies: 169 169
-- Name: ads_size_pkey; Type: CONSTRAINT; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

ALTER TABLE ONLY ads_size
    ADD CONSTRAINT ads_size_pkey PRIMARY KEY (id_ads_size);


--
-- TOC entry 2018 (class 2606 OID 29788)
-- Dependencies: 175 175
-- Name: ads_transaction_pkey; Type: CONSTRAINT; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

ALTER TABLE ONLY ads_transaction
    ADD CONSTRAINT ads_transaction_pkey PRIMARY KEY (id_ads_transaction);


--
-- TOC entry 2014 (class 2606 OID 29746)
-- Dependencies: 171 171
-- Name: ads_type_pkey; Type: CONSTRAINT; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

ALTER TABLE ONLY ads_type
    ADD CONSTRAINT ads_type_pkey PRIMARY KEY (id_ads_type);


--
-- TOC entry 2010 (class 2606 OID 29719)
-- Dependencies: 167 167
-- Name: campaign_pkey; Type: CONSTRAINT; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

ALTER TABLE ONLY campaign
    ADD CONSTRAINT campaign_pkey PRIMARY KEY (id_campaign);


--
-- TOC entry 2020 (class 2606 OID 29810)
-- Dependencies: 177 177
-- Name: deposito_pkey; Type: CONSTRAINT; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

ALTER TABLE ONLY deposito
    ADD CONSTRAINT deposito_pkey PRIMARY KEY (id_deposito);


--
-- TOC entry 2028 (class 2606 OID 29894)
-- Dependencies: 185 185
-- Name: impression_pkey; Type: CONSTRAINT; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

ALTER TABLE ONLY impression
    ADD CONSTRAINT impression_pkey PRIMARY KEY (id_impression);


--
-- TOC entry 2034 (class 2606 OID 29946)
-- Dependencies: 191 191
-- Name: notification_pkey; Type: CONSTRAINT; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

ALTER TABLE ONLY notification
    ADD CONSTRAINT notification_pkey PRIMARY KEY (id_notification);


--
-- TOC entry 2032 (class 2606 OID 29937)
-- Dependencies: 189 189
-- Name: system_preferences_pkey; Type: CONSTRAINT; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

ALTER TABLE ONLY system_preferences
    ADD CONSTRAINT system_preferences_pkey PRIMARY KEY (id_system_preferences);


--
-- TOC entry 2008 (class 2606 OID 29690)
-- Dependencies: 165 165
-- Name: user_contact_pkey; Type: CONSTRAINT; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

ALTER TABLE ONLY user_contact
    ADD CONSTRAINT user_contact_pkey PRIMARY KEY (id_user_contact);


--
-- TOC entry 2006 (class 2606 OID 29664)
-- Dependencies: 163 163
-- Name: user_pkey; Type: CONSTRAINT; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id_user);


--
-- TOC entry 2022 (class 2606 OID 29832)
-- Dependencies: 179 179
-- Name: zone_channel_pkey; Type: CONSTRAINT; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

ALTER TABLE ONLY zone_channel
    ADD CONSTRAINT zone_channel_pkey PRIMARY KEY (id_zone_channel);


--
-- TOC entry 2024 (class 2606 OID 29854)
-- Dependencies: 181 181
-- Name: zone_pkey; Type: CONSTRAINT; Schema: adsmanager; Owner: xenovon; Tablespace: 
--

ALTER TABLE ONLY zone
    ADD CONSTRAINT zone_pkey PRIMARY KEY (id_zone);


--
-- TOC entry 2050 (class 2606 OID 29922)
-- Dependencies: 187 2027 185
-- Name: ads_action_id_impression_fkey; Type: FK CONSTRAINT; Schema: adsmanager; Owner: xenovon
--

ALTER TABLE ONLY ads_action
    ADD CONSTRAINT ads_action_id_impression_fkey FOREIGN KEY (id_impression) REFERENCES impression(id_impression) ON DELETE CASCADE;


--
-- TOC entry 2037 (class 2606 OID 29953)
-- Dependencies: 173 169 2011
-- Name: ads_id_ads_size_fkey; Type: FK CONSTRAINT; Schema: adsmanager; Owner: xenovon
--

ALTER TABLE ONLY ads
    ADD CONSTRAINT ads_id_ads_size_fkey FOREIGN KEY (id_ads_size) REFERENCES ads_size(id_ads_size);


--
-- TOC entry 2038 (class 2606 OID 29958)
-- Dependencies: 2013 173 171
-- Name: ads_id_ads_type_fkey; Type: FK CONSTRAINT; Schema: adsmanager; Owner: xenovon
--

ALTER TABLE ONLY ads
    ADD CONSTRAINT ads_id_ads_type_fkey FOREIGN KEY (id_ads_type) REFERENCES ads_type(id_ads_type);


--
-- TOC entry 2039 (class 2606 OID 29963)
-- Dependencies: 2009 167 173
-- Name: ads_id_campaign_fkey; Type: FK CONSTRAINT; Schema: adsmanager; Owner: xenovon
--

ALTER TABLE ONLY ads
    ADD CONSTRAINT ads_id_campaign_fkey FOREIGN KEY (id_campaign) REFERENCES campaign(id_campaign) ON DELETE CASCADE;


--
-- TOC entry 2046 (class 2606 OID 29875)
-- Dependencies: 2015 183 173
-- Name: ads_placement_id_ads_fkey; Type: FK CONSTRAINT; Schema: adsmanager; Owner: xenovon
--

ALTER TABLE ONLY ads_placement
    ADD CONSTRAINT ads_placement_id_ads_fkey FOREIGN KEY (id_ads) REFERENCES ads(id_ads) ON DELETE CASCADE;


--
-- TOC entry 2047 (class 2606 OID 29880)
-- Dependencies: 183 2023 181
-- Name: ads_placement_id_zone_fkey; Type: FK CONSTRAINT; Schema: adsmanager; Owner: xenovon
--

ALTER TABLE ONLY ads_placement
    ADD CONSTRAINT ads_placement_id_zone_fkey FOREIGN KEY (id_zone) REFERENCES zone(id_zone) ON DELETE CASCADE;


--
-- TOC entry 2041 (class 2606 OID 29794)
-- Dependencies: 173 175 2015
-- Name: ads_transaction_id_ads_fkey; Type: FK CONSTRAINT; Schema: adsmanager; Owner: xenovon
--

ALTER TABLE ONLY ads_transaction
    ADD CONSTRAINT ads_transaction_id_ads_fkey FOREIGN KEY (id_ads) REFERENCES ads(id_ads) ON DELETE CASCADE;


--
-- TOC entry 2040 (class 2606 OID 29789)
-- Dependencies: 2005 163 175
-- Name: ads_transaction_id_user_fkey; Type: FK CONSTRAINT; Schema: adsmanager; Owner: xenovon
--

ALTER TABLE ONLY ads_transaction
    ADD CONSTRAINT ads_transaction_id_user_fkey FOREIGN KEY (id_user) REFERENCES "user"(id_user) ON DELETE CASCADE;


--
-- TOC entry 2036 (class 2606 OID 29720)
-- Dependencies: 163 2005 167
-- Name: campaign_id_user_fkey; Type: FK CONSTRAINT; Schema: adsmanager; Owner: xenovon
--

ALTER TABLE ONLY campaign
    ADD CONSTRAINT campaign_id_user_fkey FOREIGN KEY (id_user) REFERENCES "user"(id_user) ON DELETE SET DEFAULT;


--
-- TOC entry 2042 (class 2606 OID 29811)
-- Dependencies: 177 2005 163
-- Name: deposito_id_user_fkey; Type: FK CONSTRAINT; Schema: adsmanager; Owner: xenovon
--

ALTER TABLE ONLY deposito
    ADD CONSTRAINT deposito_id_user_fkey FOREIGN KEY (id_user) REFERENCES "user"(id_user) ON DELETE CASCADE;


--
-- TOC entry 2043 (class 2606 OID 29816)
-- Dependencies: 2005 163 177
-- Name: deposito_id_user_validator_fkey; Type: FK CONSTRAINT; Schema: adsmanager; Owner: xenovon
--

ALTER TABLE ONLY deposito
    ADD CONSTRAINT deposito_id_user_validator_fkey FOREIGN KEY (id_user_validator) REFERENCES "user"(id_user) ON DELETE SET DEFAULT;


--
-- TOC entry 2048 (class 2606 OID 29895)
-- Dependencies: 185 2015 173
-- Name: impression_id_ads_fkey; Type: FK CONSTRAINT; Schema: adsmanager; Owner: xenovon
--

ALTER TABLE ONLY impression
    ADD CONSTRAINT impression_id_ads_fkey FOREIGN KEY (id_ads) REFERENCES ads(id_ads) ON DELETE CASCADE;


--
-- TOC entry 2049 (class 2606 OID 29900)
-- Dependencies: 181 2023 185
-- Name: impression_id_zone_fkey; Type: FK CONSTRAINT; Schema: adsmanager; Owner: xenovon
--

ALTER TABLE ONLY impression
    ADD CONSTRAINT impression_id_zone_fkey FOREIGN KEY (id_zone) REFERENCES zone(id_zone) ON DELETE CASCADE;


--
-- TOC entry 2051 (class 2606 OID 29947)
-- Dependencies: 2005 163 191
-- Name: notification_id_user_fkey; Type: FK CONSTRAINT; Schema: adsmanager; Owner: xenovon
--

ALTER TABLE ONLY notification
    ADD CONSTRAINT notification_id_user_fkey FOREIGN KEY (id_user) REFERENCES "user"(id_user) ON DELETE CASCADE;


--
-- TOC entry 2035 (class 2606 OID 29691)
-- Dependencies: 165 2005 163
-- Name: user_contact_id_user_fkey; Type: FK CONSTRAINT; Schema: adsmanager; Owner: xenovon
--

ALTER TABLE ONLY user_contact
    ADD CONSTRAINT user_contact_id_user_fkey FOREIGN KEY (id_user) REFERENCES "user"(id_user) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2045 (class 2606 OID 29860)
-- Dependencies: 2011 181 169
-- Name: zone_id_ads_size_fkey; Type: FK CONSTRAINT; Schema: adsmanager; Owner: xenovon
--

ALTER TABLE ONLY zone
    ADD CONSTRAINT zone_id_ads_size_fkey FOREIGN KEY (id_ads_size) REFERENCES ads_size(id_ads_size) ON DELETE SET DEFAULT;


--
-- TOC entry 2044 (class 2606 OID 29855)
-- Dependencies: 2021 181 179
-- Name: zone_id_zone_channel_fkey; Type: FK CONSTRAINT; Schema: adsmanager; Owner: xenovon
--

ALTER TABLE ONLY zone
    ADD CONSTRAINT zone_id_zone_channel_fkey FOREIGN KEY (id_zone_channel) REFERENCES zone_channel(id_zone_channel) ON DELETE SET DEFAULT;


-- Completed on 2013-05-20 21:30:19

--
-- PostgreSQL database dump complete
--

