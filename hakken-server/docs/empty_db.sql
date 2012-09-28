--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.4
-- Dumped by pg_dump version 9.1.4
-- Started on 2012-07-11 16:14:32 BST

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 7 (class 2615 OID 85731)
-- Name: hakken; Type: SCHEMA; Schema: -; Owner: hakken
--

CREATE SCHEMA hakken;


ALTER SCHEMA hakken OWNER TO hakken;

SET search_path = hakken, pg_catalog;

--
-- TOC entry 169 (class 1259 OID 85850)
-- Dependencies: 7
-- Name: dataitem_seq; Type: SEQUENCE; Schema: hakken; Owner: hakken
--

CREATE SEQUENCE dataitem_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hakken.dataitem_seq OWNER TO hakken;

--
-- TOC entry 1958 (class 0 OID 0)
-- Dependencies: 169
-- Name: dataitem_seq; Type: SEQUENCE SET; Schema: hakken; Owner: hakken
--

SELECT pg_catalog.setval('dataitem_seq', 1, false);


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 162 (class 1259 OID 85740)
-- Dependencies: 7
-- Name: dataitems; Type: TABLE; Schema: hakken; Owner: hakken; Tablespace: 
--

CREATE TABLE dataitems (
    di_id bigint NOT NULL,
    name character varying(255),
    pagename character varying(255),
    type character varying(255),
    value character varying(255)
);


ALTER TABLE hakken.dataitems OWNER TO hakken;

--
-- TOC entry 167 (class 1259 OID 85816)
-- Dependencies: 7
-- Name: dc_def_mapping_props; Type: TABLE; Schema: hakken; Owner: hakken; Tablespace: 
--

CREATE TABLE dc_def_mapping_props (
    dc_def_mapping_id bigint NOT NULL,
    value character varying(255),
    key character varying(255) NOT NULL
);


ALTER TABLE hakken.dc_def_mapping_props OWNER TO hakken;

--
-- TOC entry 163 (class 1259 OID 85748)
-- Dependencies: 7
-- Name: dc_def_mappings; Type: TABLE; Schema: hakken; Owner: hakken; Tablespace: 
--

CREATE TABLE dc_def_mappings (
    id bigint NOT NULL,
    dataconnectorname character varying(255),
    taskdefinitionname character varying(255)
);


ALTER TABLE hakken.dc_def_mappings OWNER TO hakken;

--
-- TOC entry 170 (class 1259 OID 85852)
-- Dependencies: 7
-- Name: dc_definition_map_seq; Type: SEQUENCE; Schema: hakken; Owner: hakken
--

CREATE SEQUENCE dc_definition_map_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hakken.dc_definition_map_seq OWNER TO hakken;

--
-- TOC entry 1959 (class 0 OID 0)
-- Dependencies: 170
-- Name: dc_definition_map_seq; Type: SEQUENCE SET; Schema: hakken; Owner: hakken
--

SELECT pg_catalog.setval('dc_definition_map_seq', 1, false);


--
-- TOC entry 171 (class 1259 OID 85854)
-- Dependencies: 7
-- Name: serv_map_seq; Type: SEQUENCE; Schema: hakken; Owner: hakken
--

CREATE SEQUENCE serv_map_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hakken.serv_map_seq OWNER TO hakken;

--
-- TOC entry 1960 (class 0 OID 0)
-- Dependencies: 171
-- Name: serv_map_seq; Type: SEQUENCE SET; Schema: hakken; Owner: hakken
--

SELECT pg_catalog.setval('serv_map_seq', 1, false);


--
-- TOC entry 168 (class 1259 OID 85829)
-- Dependencies: 7
-- Name: service_mapping_dataitems; Type: TABLE; Schema: hakken; Owner: hakken; Tablespace: 
--

CREATE TABLE service_mapping_dataitems (
    mapping_id bigint NOT NULL,
    task_di character varying(255),
    connector_di character varying(255) NOT NULL
);


ALTER TABLE hakken.service_mapping_dataitems OWNER TO hakken;

--
-- TOC entry 164 (class 1259 OID 85756)
-- Dependencies: 7
-- Name: service_mappings; Type: TABLE; Schema: hakken; Owner: hakken; Tablespace: 
--

CREATE TABLE service_mappings (
    id bigint NOT NULL,
    taskdefinitionname character varying(255),
    dc_def_mapping_id bigint
);


ALTER TABLE hakken.service_mappings OWNER TO hakken;

--
-- TOC entry 165 (class 1259 OID 85761)
-- Dependencies: 7
-- Name: submission_dataitems; Type: TABLE; Schema: hakken; Owner: hakken; Tablespace: 
--

CREATE TABLE submission_dataitems (
    submission_id bigint NOT NULL,
    data_item_id bigint NOT NULL
);


ALTER TABLE hakken.submission_dataitems OWNER TO hakken;

--
-- TOC entry 172 (class 1259 OID 85856)
-- Dependencies: 7
-- Name: submission_seq; Type: SEQUENCE; Schema: hakken; Owner: hakken
--

CREATE SEQUENCE submission_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hakken.submission_seq OWNER TO hakken;

--
-- TOC entry 1961 (class 0 OID 0)
-- Dependencies: 172
-- Name: submission_seq; Type: SEQUENCE SET; Schema: hakken; Owner: hakken
--

SELECT pg_catalog.setval('submission_seq', 1, false);


--
-- TOC entry 166 (class 1259 OID 85766)
-- Dependencies: 7
-- Name: submissions; Type: TABLE; Schema: hakken; Owner: hakken; Tablespace: 
--

CREATE TABLE submissions (
    sub_id bigint NOT NULL,
    job_id bigint,
    taskdefinitionname character varying(255),
    username character varying(255)
);


ALTER TABLE hakken.submissions OWNER TO hakken;

--
-- TOC entry 1921 (class 2606 OID 85747)
-- Dependencies: 162 162
-- Name: dataitems_pkey; Type: CONSTRAINT; Schema: hakken; Owner: hakken; Tablespace: 
--

ALTER TABLE ONLY dataitems
    ADD CONSTRAINT dataitems_pkey PRIMARY KEY (di_id);


--
-- TOC entry 1939 (class 2606 OID 85823)
-- Dependencies: 167 167 167
-- Name: dc_def_mapping_props_pkey; Type: CONSTRAINT; Schema: hakken; Owner: hakken; Tablespace: 
--

ALTER TABLE ONLY dc_def_mapping_props
    ADD CONSTRAINT dc_def_mapping_props_pkey PRIMARY KEY (dc_def_mapping_id, key);


--
-- TOC entry 1924 (class 2606 OID 85755)
-- Dependencies: 163 163
-- Name: dc_def_mappings_pkey; Type: CONSTRAINT; Schema: hakken; Owner: hakken; Tablespace: 
--

ALTER TABLE ONLY dc_def_mappings
    ADD CONSTRAINT dc_def_mappings_pkey PRIMARY KEY (id);


--
-- TOC entry 1931 (class 2606 OID 85860)
-- Dependencies: 165 165 165
-- Name: pk_submission_dataitems; Type: CONSTRAINT; Schema: hakken; Owner: hakken; Tablespace: 
--

ALTER TABLE ONLY submission_dataitems
    ADD CONSTRAINT pk_submission_dataitems PRIMARY KEY (submission_id, data_item_id);


--
-- TOC entry 1943 (class 2606 OID 85836)
-- Dependencies: 168 168 168
-- Name: service_mapping_dataitems_pkey; Type: CONSTRAINT; Schema: hakken; Owner: hakken; Tablespace: 
--

ALTER TABLE ONLY service_mapping_dataitems
    ADD CONSTRAINT service_mapping_dataitems_pkey PRIMARY KEY (mapping_id, connector_di);


--
-- TOC entry 1928 (class 2606 OID 85760)
-- Dependencies: 164 164
-- Name: service_mappings_pkey; Type: CONSTRAINT; Schema: hakken; Owner: hakken; Tablespace: 
--

ALTER TABLE ONLY service_mappings
    ADD CONSTRAINT service_mappings_pkey PRIMARY KEY (id);


--
-- TOC entry 1933 (class 2606 OID 85765)
-- Dependencies: 165 165
-- Name: submission_dataitems_data_item_id_key; Type: CONSTRAINT; Schema: hakken; Owner: hakken; Tablespace: 
--

ALTER TABLE ONLY submission_dataitems
    ADD CONSTRAINT submission_dataitems_data_item_id_key UNIQUE (data_item_id);


--
-- TOC entry 1937 (class 2606 OID 85773)
-- Dependencies: 166 166
-- Name: submissions_pkey; Type: CONSTRAINT; Schema: hakken; Owner: hakken; Tablespace: 
--

ALTER TABLE ONLY submissions
    ADD CONSTRAINT submissions_pkey PRIMARY KEY (sub_id);


--
-- TOC entry 1922 (class 1259 OID 85861)
-- Dependencies: 162
-- Name: idx_dataitems; Type: INDEX; Schema: hakken; Owner: hakken; Tablespace: 
--

CREATE INDEX idx_dataitems ON dataitems USING btree (di_id);


--
-- TOC entry 1940 (class 1259 OID 85862)
-- Dependencies: 167
-- Name: idx_dc_def_mapping_props; Type: INDEX; Schema: hakken; Owner: hakken; Tablespace: 
--

CREATE INDEX idx_dc_def_mapping_props ON dc_def_mapping_props USING btree (dc_def_mapping_id);


--
-- TOC entry 1925 (class 1259 OID 85863)
-- Dependencies: 163
-- Name: idx_dc_def_mappings; Type: INDEX; Schema: hakken; Owner: hakken; Tablespace: 
--

CREATE INDEX idx_dc_def_mappings ON dc_def_mappings USING btree (id);


--
-- TOC entry 1926 (class 1259 OID 85865)
-- Dependencies: 164
-- Name: idx_servic_mappings; Type: INDEX; Schema: hakken; Owner: hakken; Tablespace: 
--

CREATE INDEX idx_servic_mappings ON service_mappings USING btree (id);


--
-- TOC entry 1941 (class 1259 OID 85864)
-- Dependencies: 168
-- Name: idx_service_mapping_dataitems; Type: INDEX; Schema: hakken; Owner: hakken; Tablespace: 
--

CREATE INDEX idx_service_mapping_dataitems ON service_mapping_dataitems USING btree (mapping_id);


--
-- TOC entry 1929 (class 1259 OID 85866)
-- Dependencies: 165
-- Name: idx_submission_dataitems; Type: INDEX; Schema: hakken; Owner: hakken; Tablespace: 
--

CREATE INDEX idx_submission_dataitems ON submission_dataitems USING btree (submission_id);


--
-- TOC entry 1934 (class 1259 OID 85867)
-- Dependencies: 166
-- Name: idx_submissions; Type: INDEX; Schema: hakken; Owner: hakken; Tablespace: 
--

CREATE INDEX idx_submissions ON submissions USING btree (sub_id);


--
-- TOC entry 1935 (class 1259 OID 85868)
-- Dependencies: 166
-- Name: idx_submissions-job_id; Type: INDEX; Schema: hakken; Owner: hakken; Tablespace: 
--

CREATE INDEX "idx_submissions-job_id" ON submissions USING btree (job_id);


--
-- TOC entry 1948 (class 2606 OID 85837)
-- Dependencies: 164 1927 168
-- Name: fk3c93691b42913647; Type: FK CONSTRAINT; Schema: hakken; Owner: hakken
--

ALTER TABLE ONLY service_mapping_dataitems
    ADD CONSTRAINT fk3c93691b42913647 FOREIGN KEY (mapping_id) REFERENCES service_mappings(id);


--
-- TOC entry 1944 (class 2606 OID 85787)
-- Dependencies: 1923 164 163
-- Name: fk7b8427af471e1b; Type: FK CONSTRAINT; Schema: hakken; Owner: hakken
--

ALTER TABLE ONLY service_mappings
    ADD CONSTRAINT fk7b8427af471e1b FOREIGN KEY (dc_def_mapping_id) REFERENCES dc_def_mappings(id);


--
-- TOC entry 1945 (class 2606 OID 85792)
-- Dependencies: 1920 162 165
-- Name: fkb3f2b92397f98c61; Type: FK CONSTRAINT; Schema: hakken; Owner: hakken
--

ALTER TABLE ONLY submission_dataitems
    ADD CONSTRAINT fkb3f2b92397f98c61 FOREIGN KEY (data_item_id) REFERENCES dataitems(di_id);


--
-- TOC entry 1946 (class 2606 OID 85797)
-- Dependencies: 166 1936 165
-- Name: fkb3f2b923c2ebde0c; Type: FK CONSTRAINT; Schema: hakken; Owner: hakken
--

ALTER TABLE ONLY submission_dataitems
    ADD CONSTRAINT fkb3f2b923c2ebde0c FOREIGN KEY (submission_id) REFERENCES submissions(sub_id);


--
-- TOC entry 1947 (class 2606 OID 85824)
-- Dependencies: 1923 167 163
-- Name: fkf619d045471e1b; Type: FK CONSTRAINT; Schema: hakken; Owner: hakken
--

ALTER TABLE ONLY dc_def_mapping_props
    ADD CONSTRAINT fkf619d045471e1b FOREIGN KEY (dc_def_mapping_id) REFERENCES dc_def_mappings(id);

CREATE TABLE hakken.logs
(
  id bigint NOT NULL,
  message character varying(255),
  "timestamp" timestamp without time zone,
  username character varying(255),
  CONSTRAINT logs_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE hakken.logs OWNER TO hakken;

CREATE SEQUENCE hakken.log_entry_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 10
  CACHE 1;
ALTER TABLE hakken.log_entry_seq OWNER TO hakken;
