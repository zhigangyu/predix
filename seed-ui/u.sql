--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.1
-- Dumped by pg_dump version 9.5.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: lathe_machine; Type: TABLE; Schema: public; Owner: lathe
--

CREATE TABLE lathe_machine (
    c_lathe_code character varying(20) NOT NULL,
    d_create_date date,
    n_status integer
);


ALTER TABLE lathe_machine OWNER TO lathe;

--
-- Data for Name: lathe_machine; Type: TABLE DATA; Schema: public; Owner: lathe
--

COPY lathe_machine (c_lathe_code, d_create_date, n_status) FROM stdin;
M02	2016-03-18	2
M04	2016-03-25	0
CA6140B	2016-03-25	0
M01	2016-03-18	1
CA6140A	2016-03-25	3
M05	2016-03-25	0
M06	2016-03-25	0
M07	2016-03-25	0
M08	2016-03-25	0
M09	2016-03-25	0
M10	2016-03-25	0
M11	2016-03-25	0
M12	2016-03-25	0
\.


--
-- Name: lathe_machine_pkey; Type: CONSTRAINT; Schema: public; Owner: lathe
--

ALTER TABLE ONLY lathe_machine
    ADD CONSTRAINT lathe_machine_pkey PRIMARY KEY (c_lathe_code);


--
-- PostgreSQL database dump complete
--

