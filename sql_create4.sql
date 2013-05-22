--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.4
-- Dumped by pg_dump version 9.1.4
-- Started on 2013-05-21 21:16:24

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = adsmanager, pg_catalog;

--
-- TOC entry 2114 (class 0 OID 0)
-- Dependencies: 248
-- Name: ads_size_seq; Type: SEQUENCE SET; Schema: adsmanager; Owner: xenovon
--

SELECT pg_catalog.setval('ads_size_seq', 20, true);


--
-- TOC entry 2115 (class 0 OID 0)
-- Dependencies: 249
-- Name: campaign_seq; Type: SEQUENCE SET; Schema: adsmanager; Owner: xenovon
--

SELECT pg_catalog.setval('campaign_seq', 1, false);


--
-- TOC entry 2116 (class 0 OID 0)
-- Dependencies: 251
-- Name: user_contact_seq; Type: SEQUENCE SET; Schema: adsmanager; Owner: xenovon
--

SELECT pg_catalog.setval('user_contact_seq', 1, false);


--
-- TOC entry 2117 (class 0 OID 0)
-- Dependencies: 250
-- Name: user_seq; Type: SEQUENCE SET; Schema: adsmanager; Owner: xenovon
--

SELECT pg_catalog.setval('user_seq', 20, true);


--
-- TOC entry 2108 (class 0 OID 30314)
-- Dependencies: 244
-- Data for Name: ads_size; Type: TABLE DATA; Schema: adsmanager; Owner: xenovon
--

COPY ads_size (id_ads_size, name, width, height, description) FROM stdin;
1	anu	333	111	Untuk ukuran ngasal sumpah
\.


--
-- TOC entry 2109 (class 0 OID 30322)
-- Dependencies: 245
-- Data for Name: campaign; Type: TABLE DATA; Schema: adsmanager; Owner: xenovon
--

COPY campaign (id_campaign, id_user_id_user, campaign_type, start_date, end_date, pricing_model, limit_impression, limit_click, current_impression, current_click, bid_price, is_activated, is_deleted, ganteng) FROM stdin;
\.


--
-- TOC entry 2110 (class 0 OID 30329)
-- Dependencies: 246
-- Data for Name: user; Type: TABLE DATA; Schema: adsmanager; Owner: xenovon
--

COPY "user" (id_user, email, password, front_name, last_name, company, role, join_date, current_balance, is_active, profile_photo, city, country, validation_key) FROM stdin;
1	komputok@gmail.com	ac76fed42d81e9587024c1d19e369585	Subyek	Predikat	\N	administrator	2013-05-21 21:14:18.47	0	f	\N	\N	\N	25e9d6c591a35d6fe8ee4984d423b706
\.


--
-- TOC entry 2111 (class 0 OID 30338)
-- Dependencies: 247
-- Data for Name: user_contact; Type: TABLE DATA; Schema: adsmanager; Owner: xenovon
--

COPY user_contact (id_user_contact, id_user_id_user, contact_value, contact_type, contact_description) FROM stdin;
\.


-- Completed on 2013-05-21 21:16:25

--
-- PostgreSQL database dump complete
--

