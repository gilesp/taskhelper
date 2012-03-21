--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.1
-- Dumped by pg_dump version 9.1.1
-- Started on 2011-12-15 17:06:16 GMT

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 172 (class 3079 OID 11676)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 1959 (class 0 OID 0)
-- Dependencies: 172
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 169 (class 1259 OID 57366)
-- Dependencies: 5
-- Name: data_item; Type: TABLE; Schema: public; Owner: taskhelper; Tablespace: 
--

CREATE TABLE data_item (
    id bigint NOT NULL,
    name character varying(255),
    page_name character varying(255),
    type character varying(255),
    value character varying(255),
    version integer
);


ALTER TABLE public.data_item OWNER TO taskhelper;

--
-- TOC entry 165 (class 1259 OID 57295)
-- Dependencies: 5
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: taskhelper
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO taskhelper;

--
-- TOC entry 1960 (class 0 OID 0)
-- Dependencies: 165
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: taskhelper
--

SELECT pg_catalog.setval('hibernate_sequence', 19, true);


--
-- TOC entry 161 (class 1259 OID 57039)
-- Dependencies: 5
-- Name: job; Type: TABLE; Schema: public; Owner: taskhelper; Tablespace: 
--

CREATE TABLE job (
    id bigint NOT NULL,
    version integer,
    person bigint NOT NULL,
    task bigint NOT NULL,
    created timestamp without time zone,
    due timestamp without time zone,
    status integer
);


ALTER TABLE public.job OWNER TO taskhelper;

--
-- TOC entry 166 (class 1259 OID 57322)
-- Dependencies: 5
-- Name: page_item; Type: TABLE; Schema: public; Owner: taskhelper; Tablespace: 
--

CREATE TABLE page_item (
    id bigint NOT NULL,
    label character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    type integer,
    value character varying(255),
    version integer
);


ALTER TABLE public.page_item OWNER TO taskhelper;

--
-- TOC entry 162 (class 1259 OID 57052)
-- Dependencies: 5
-- Name: person; Type: TABLE; Schema: public; Owner: taskhelper; Tablespace: 
--

CREATE TABLE person (
    id bigint NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    version integer
);


ALTER TABLE public.person OWNER TO taskhelper;

--
-- TOC entry 170 (class 1259 OID 57374)
-- Dependencies: 5
-- Name: submission; Type: TABLE; Schema: public; Owner: taskhelper; Tablespace: 
--

CREATE TABLE submission (
    id bigint NOT NULL,
    job_id integer NOT NULL,
    username character varying(255),
    version integer
);


ALTER TABLE public.submission OWNER TO taskhelper;

--
-- TOC entry 171 (class 1259 OID 57379)
-- Dependencies: 5
-- Name: submission_dataitems; Type: TABLE; Schema: public; Owner: taskhelper; Tablespace: 
--

CREATE TABLE submission_dataitems (
    submission bigint NOT NULL,
    dataitems bigint NOT NULL
);


ALTER TABLE public.submission_dataitems OWNER TO taskhelper;

--
-- TOC entry 164 (class 1259 OID 57267)
-- Dependencies: 5
-- Name: task_definition; Type: TABLE; Schema: public; Owner: taskhelper; Tablespace: 
--

CREATE TABLE task_definition (
    id bigint NOT NULL,
    description character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    version integer
);


ALTER TABLE public.task_definition OWNER TO taskhelper;

--
-- TOC entry 168 (class 1259 OID 57351)
-- Dependencies: 5
-- Name: task_definition_pages; Type: TABLE; Schema: public; Owner: taskhelper; Tablespace: 
--

CREATE TABLE task_definition_pages (
    task_definition bigint NOT NULL,
    pages bigint NOT NULL,
    index integer NOT NULL
);


ALTER TABLE public.task_definition_pages OWNER TO taskhelper;

--
-- TOC entry 163 (class 1259 OID 57073)
-- Dependencies: 5
-- Name: task_page; Type: TABLE; Schema: public; Owner: taskhelper; Tablespace: 
--

CREATE TABLE task_page (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    version integer
);


ALTER TABLE public.task_page OWNER TO taskhelper;

--
-- TOC entry 167 (class 1259 OID 57336)
-- Dependencies: 5
-- Name: task_page_items; Type: TABLE; Schema: public; Owner: taskhelper; Tablespace: 
--

CREATE TABLE task_page_items (
    task_page bigint NOT NULL,
    items bigint NOT NULL,
    index integer NOT NULL
);


ALTER TABLE public.task_page_items OWNER TO taskhelper;

--
-- TOC entry 1951 (class 0 OID 57366)
-- Dependencies: 169
-- Data for Name: data_item; Type: TABLE DATA; Schema: public; Owner: taskhelper
--

COPY data_item (id, name, page_name, type, value, version) FROM stdin;
\.


--
-- TOC entry 1944 (class 0 OID 57039)
-- Dependencies: 161
-- Data for Name: job; Type: TABLE DATA; Schema: public; Owner: taskhelper
--

COPY job (id, version, person, task, created, due, status) FROM stdin;
16	0	1	15	2011-12-14 00:00:00	2011-12-24 00:00:00	0
17	0	1	15	2011-12-14 00:00:00	2011-12-30 00:00:00	0
\.


--
-- TOC entry 1948 (class 0 OID 57322)
-- Dependencies: 166
-- Data for Name: page_item; Type: TABLE DATA; Schema: public; Owner: taskhelper
--

COPY page_item (id, label, name, type, value, version) FROM stdin;
1	Please enter your name	enter_name	0		0
2	This is a sample task. Please provide all the information requested	sample_intro	0		0
3	Name of Resident	residents_name	1		0
4	Date of Birth	residents_dob	3		0
5	Number of occupants	number_occupants	2		0
6	Children under 5	number_children	2		0
7	Number of Smokers	number_smokers	2		0
8	These questions relate to the people who live in the house permanently.	household_intro	0		0
9	Number of disabled residents	number_disabled	2		0
10	Number of alarms fitted	smokealarms_fitted	2		0
\.


--
-- TOC entry 1945 (class 0 OID 57052)
-- Dependencies: 162
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: taskhelper
--

COPY person (id, first_name, last_name, password, username, version) FROM stdin;
1	Giles	Paterson	password	giles.paterson	0
2	Tim	Needham	password	tim.needham	0
3	Ryan	Pickett	password	ryan.pickett	0
\.


--
-- TOC entry 1952 (class 0 OID 57374)
-- Dependencies: 170
-- Data for Name: submission; Type: TABLE DATA; Schema: public; Owner: taskhelper
--

COPY submission (id, job_id, username, version) FROM stdin;
\.


--
-- TOC entry 1953 (class 0 OID 57379)
-- Dependencies: 171
-- Data for Name: submission_dataitems; Type: TABLE DATA; Schema: public; Owner: taskhelper
--

COPY submission_dataitems (submission, dataitems) FROM stdin;
\.


--
-- TOC entry 1947 (class 0 OID 57267)
-- Dependencies: 164
-- Data for Name: task_definition; Type: TABLE DATA; Schema: public; Owner: taskhelper
--

COPY task_definition (id, description, name, version) FROM stdin;
15	Home Safety Check	hsc	2
\.


--
-- TOC entry 1950 (class 0 OID 57351)
-- Dependencies: 168
-- Data for Name: task_definition_pages; Type: TABLE DATA; Schema: public; Owner: taskhelper
--

COPY task_definition_pages (task_definition, pages, index) FROM stdin;
15	11	0
15	12	1
15	13	2
\.


--
-- TOC entry 1946 (class 0 OID 57073)
-- Dependencies: 163
-- Data for Name: task_page; Type: TABLE DATA; Schema: public; Owner: taskhelper
--

COPY task_page (id, name, version) FROM stdin;
11	hsc_page1	2
13	hsc_page3	2
12	hsc_page2	1
\.


--
-- TOC entry 1949 (class 0 OID 57336)
-- Dependencies: 167
-- Data for Name: task_page_items; Type: TABLE DATA; Schema: public; Owner: taskhelper
--

COPY task_page_items (task_page, items, index) FROM stdin;
11	2	0
11	3	1
11	4	2
13	10	0
12	9	4
12	8	0
12	5	1
12	6	2
12	7	3
\.


--
-- TOC entry 1929 (class 2606 OID 57373)
-- Dependencies: 169 169
-- Name: data_item_pkey; Type: CONSTRAINT; Schema: public; Owner: taskhelper; Tablespace: 
--

ALTER TABLE ONLY data_item
    ADD CONSTRAINT data_item_pkey PRIMARY KEY (id);


--
-- TOC entry 1915 (class 2606 OID 57043)
-- Dependencies: 161 161
-- Name: job_pkey; Type: CONSTRAINT; Schema: public; Owner: taskhelper; Tablespace: 
--

ALTER TABLE ONLY job
    ADD CONSTRAINT job_pkey PRIMARY KEY (id);


--
-- TOC entry 1923 (class 2606 OID 57329)
-- Dependencies: 166 166
-- Name: page_item_pkey; Type: CONSTRAINT; Schema: public; Owner: taskhelper; Tablespace: 
--

ALTER TABLE ONLY page_item
    ADD CONSTRAINT page_item_pkey PRIMARY KEY (id);


--
-- TOC entry 1917 (class 2606 OID 57059)
-- Dependencies: 162 162
-- Name: person_pkey; Type: CONSTRAINT; Schema: public; Owner: taskhelper; Tablespace: 
--

ALTER TABLE ONLY person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


--
-- TOC entry 1933 (class 2606 OID 57385)
-- Dependencies: 171 171
-- Name: submission_dataitems_dataitems_key; Type: CONSTRAINT; Schema: public; Owner: taskhelper; Tablespace: 
--

ALTER TABLE ONLY submission_dataitems
    ADD CONSTRAINT submission_dataitems_dataitems_key UNIQUE (dataitems);


--
-- TOC entry 1935 (class 2606 OID 57383)
-- Dependencies: 171 171 171
-- Name: submission_dataitems_pkey; Type: CONSTRAINT; Schema: public; Owner: taskhelper; Tablespace: 
--

ALTER TABLE ONLY submission_dataitems
    ADD CONSTRAINT submission_dataitems_pkey PRIMARY KEY (submission, dataitems);


--
-- TOC entry 1931 (class 2606 OID 57378)
-- Dependencies: 170 170
-- Name: submission_pkey; Type: CONSTRAINT; Schema: public; Owner: taskhelper; Tablespace: 
--

ALTER TABLE ONLY submission
    ADD CONSTRAINT submission_pkey PRIMARY KEY (id);


--
-- TOC entry 1927 (class 2606 OID 57355)
-- Dependencies: 168 168 168
-- Name: task_definition_pages_pkey; Type: CONSTRAINT; Schema: public; Owner: taskhelper; Tablespace: 
--

ALTER TABLE ONLY task_definition_pages
    ADD CONSTRAINT task_definition_pages_pkey PRIMARY KEY (task_definition, pages);


--
-- TOC entry 1921 (class 2606 OID 57274)
-- Dependencies: 164 164
-- Name: task_definition_pkey; Type: CONSTRAINT; Schema: public; Owner: taskhelper; Tablespace: 
--

ALTER TABLE ONLY task_definition
    ADD CONSTRAINT task_definition_pkey PRIMARY KEY (id);


--
-- TOC entry 1925 (class 2606 OID 57340)
-- Dependencies: 167 167 167
-- Name: task_page_items_pkey; Type: CONSTRAINT; Schema: public; Owner: taskhelper; Tablespace: 
--

ALTER TABLE ONLY task_page_items
    ADD CONSTRAINT task_page_items_pkey PRIMARY KEY (task_page, items);


--
-- TOC entry 1919 (class 2606 OID 57077)
-- Dependencies: 163 163
-- Name: task_page_pkey; Type: CONSTRAINT; Schema: public; Owner: taskhelper; Tablespace: 
--

ALTER TABLE ONLY task_page
    ADD CONSTRAINT task_page_pkey PRIMARY KEY (id);


--
-- TOC entry 1937 (class 2606 OID 57083)
-- Dependencies: 162 1916 161
-- Name: fk19bbdabd25d6; Type: FK CONSTRAINT; Schema: public; Owner: taskhelper
--

ALTER TABLE ONLY job
    ADD CONSTRAINT fk19bbdabd25d6 FOREIGN KEY (person) REFERENCES person(id);


--
-- TOC entry 1936 (class 2606 OID 57317)
-- Dependencies: 164 161 1920
-- Name: fk19bbdda56c821; Type: FK CONSTRAINT; Schema: public; Owner: taskhelper
--

ALTER TABLE ONLY job
    ADD CONSTRAINT fk19bbdda56c821 FOREIGN KEY (task) REFERENCES task_definition(id);


--
-- TOC entry 1939 (class 2606 OID 57346)
-- Dependencies: 1918 163 167
-- Name: fk4a08310ac8903ba1; Type: FK CONSTRAINT; Schema: public; Owner: taskhelper
--

ALTER TABLE ONLY task_page_items
    ADD CONSTRAINT fk4a08310ac8903ba1 FOREIGN KEY (task_page) REFERENCES task_page(id);


--
-- TOC entry 1938 (class 2606 OID 57341)
-- Dependencies: 167 1922 166
-- Name: fk4a08310af7142e6; Type: FK CONSTRAINT; Schema: public; Owner: taskhelper
--

ALTER TABLE ONLY task_page_items
    ADD CONSTRAINT fk4a08310af7142e6 FOREIGN KEY (items) REFERENCES page_item(id);


--
-- TOC entry 1941 (class 2606 OID 57361)
-- Dependencies: 168 164 1920
-- Name: fka75545528719b2e9; Type: FK CONSTRAINT; Schema: public; Owner: taskhelper
--

ALTER TABLE ONLY task_definition_pages
    ADD CONSTRAINT fka75545528719b2e9 FOREIGN KEY (task_definition) REFERENCES task_definition(id);


--
-- TOC entry 1940 (class 2606 OID 57356)
-- Dependencies: 1918 163 168
-- Name: fka7554552c4219a5c; Type: FK CONSTRAINT; Schema: public; Owner: taskhelper
--

ALTER TABLE ONLY task_definition_pages
    ADD CONSTRAINT fka7554552c4219a5c FOREIGN KEY (pages) REFERENCES task_page(id);


--
-- TOC entry 1942 (class 2606 OID 57386)
-- Dependencies: 171 1928 169
-- Name: fkb3f2b92323728fb; Type: FK CONSTRAINT; Schema: public; Owner: taskhelper
--

ALTER TABLE ONLY submission_dataitems
    ADD CONSTRAINT fkb3f2b92323728fb FOREIGN KEY (dataitems) REFERENCES data_item(id);


--
-- TOC entry 1943 (class 2606 OID 57391)
-- Dependencies: 171 170 1930
-- Name: fkb3f2b9235bd56160; Type: FK CONSTRAINT; Schema: public; Owner: taskhelper
--

ALTER TABLE ONLY submission_dataitems
    ADD CONSTRAINT fkb3f2b9235bd56160 FOREIGN KEY (submission) REFERENCES submission(id);


--
-- TOC entry 1958 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2011-12-15 17:06:17 GMT

--
-- PostgreSQL database dump complete
--

