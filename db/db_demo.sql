--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: categorias; Type: TABLE; Schema: public; Owner: cbustamante; Tablespace: 
--

CREATE TABLE categorias (
    categoria_id integer NOT NULL,
    descripcion character varying(200)
);


ALTER TABLE public.categorias OWNER TO cbustamante;

--
-- Name: categorias_categoria_id_seq; Type: SEQUENCE; Schema: public; Owner: cbustamante
--

CREATE SEQUENCE categorias_categoria_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.categorias_categoria_id_seq OWNER TO cbustamante;

--
-- Name: categorias_categoria_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cbustamante
--

ALTER SEQUENCE categorias_categoria_id_seq OWNED BY categorias.categoria_id;


--
-- Name: clientes; Type: TABLE; Schema: public; Owner: cbustamante; Tablespace: 
--

CREATE TABLE clientes (
    cliente_id integer NOT NULL,
    razon_social character varying(100) NOT NULL,
    telefono character varying(100),
    direccion character varying(100),
    logo_url character varying(1000),
    contacto character varying(400),
    latitud double precision,
    longitud double precision,
    categoria_id integer,
    fecha_inicio date,
    estado_contractual boolean
);


ALTER TABLE public.clientes OWNER TO cbustamante;

--
-- Name: clientes_cliente_id_seq; Type: SEQUENCE; Schema: public; Owner: cbustamante
--

CREATE SEQUENCE clientes_cliente_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.clientes_cliente_id_seq OWNER TO cbustamante;

--
-- Name: clientes_cliente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cbustamante
--

ALTER SEQUENCE clientes_cliente_id_seq OWNED BY clientes.cliente_id;


--
-- Name: roles; Type: TABLE; Schema: public; Owner: cbustamante; Tablespace: 
--

CREATE TABLE roles (
    id integer NOT NULL,
    descripcion character varying(45) NOT NULL
);


ALTER TABLE public.roles OWNER TO cbustamante;

--
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: cbustamante
--

CREATE SEQUENCE roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.roles_id_seq OWNER TO cbustamante;

--
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cbustamante
--

ALTER SEQUENCE roles_id_seq OWNED BY roles.id;


--
-- Name: usuarios; Type: TABLE; Schema: public; Owner: cbustamante; Tablespace: 
--

CREATE TABLE usuarios (
    usuario_id integer NOT NULL,
    username character varying(45) NOT NULL,
    password character varying(255) NOT NULL,
    roles integer NOT NULL,
    empresa_id integer,
    version_id integer
);


ALTER TABLE public.usuarios OWNER TO cbustamante;

--
-- Name: usuarios_usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: cbustamante
--

CREATE SEQUENCE usuarios_usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuarios_usuario_id_seq OWNER TO cbustamante;

--
-- Name: usuarios_usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: cbustamante
--

ALTER SEQUENCE usuarios_usuario_id_seq OWNED BY usuarios.usuario_id;


--
-- Name: categoria_id; Type: DEFAULT; Schema: public; Owner: cbustamante
--

ALTER TABLE ONLY categorias ALTER COLUMN categoria_id SET DEFAULT nextval('categorias_categoria_id_seq'::regclass);


--
-- Name: cliente_id; Type: DEFAULT; Schema: public; Owner: cbustamante
--

ALTER TABLE ONLY clientes ALTER COLUMN cliente_id SET DEFAULT nextval('clientes_cliente_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: cbustamante
--

ALTER TABLE ONLY roles ALTER COLUMN id SET DEFAULT nextval('roles_id_seq'::regclass);


--
-- Name: usuario_id; Type: DEFAULT; Schema: public; Owner: cbustamante
--

ALTER TABLE ONLY usuarios ALTER COLUMN usuario_id SET DEFAULT nextval('usuarios_usuario_id_seq'::regclass);


--
-- Data for Name: categorias; Type: TABLE DATA; Schema: public; Owner: cbustamante
--

COPY categorias (categoria_id, descripcion) FROM stdin;
1	Pyme
2	Corporativo
3	Potenciales
\.


--
-- Name: categorias_categoria_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cbustamante
--

SELECT pg_catalog.setval('categorias_categoria_id_seq', 3, true);


--
-- Data for Name: clientes; Type: TABLE DATA; Schema: public; Owner: cbustamante
--

COPY clientes (cliente_id, razon_social, telefono, direccion, logo_url, contacto, latitud, longitud, categoria_id, fecha_inicio, estado_contractual) FROM stdin;
2	Pymes Guru	(021) 617-332	\N	http://www.adelantos.com.py/static/pymesguru.png	Elvis Ratzlaf	-25.2886311513368831	-57.586628450662829	\N	2016-06-08	t
3	Lasca	(021) 444-312	\N	http://www.portalguarani.com/userfiles/images/Mariano%20Llano%20hijo/IDEA-NACIONAL-por-MARIANO-LLANO-hijo-lasca-portalguarani.jpg	Jose Castillo	-25.3336338715202665	-57.5362029198731761	\N	2016-10-05	t
6	Tigo	(021) 334-543	\N	https://upload.wikimedia.org/wikipedia/commons/thumb/6/69/Logo_Tigo.svg/245px-Logo_Tigo.svg.png	Recepcion	-25.3286362561990366	-57.5550385401584208	\N	2016-05-17	t
1	Chacomer	(021) 442-112	\N	http://logoroga.com/wp-content/uploads/2012/05/chacomer1.jpg	Juan Perez	-25.3757549691974589	-57.6448520366102457	\N	2016-09-12	t
5	Burger King	(021) 554-223	\N	https://lh5.googleusercontent.com/-9S1xjYZdK4E/AAAAAAAAAAI/AAAAAAAAIhU/UejKR8SqPUI/s0-c-k-no-ns/photo.jpg	Recepcion	-25.3356481985578625	-57.5146293640136719	\N	2016-05-10	f
4	Personal	(021) 332-121	\N	http://www.designals.net/wp-content/uploads/2011/11/LogoPersonal-01.jpg	Recepcion	-25.2866107511725744	-57.5877141952514648	\N	2016-10-06	f
\.


--
-- Name: clientes_cliente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cbustamante
--

SELECT pg_catalog.setval('clientes_cliente_id_seq', 6, true);


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: cbustamante
--

COPY roles (id, descripcion) FROM stdin;
1	ADMIN
\.


--
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cbustamante
--

SELECT pg_catalog.setval('roles_id_seq', 1, true);


--
-- Data for Name: usuarios; Type: TABLE DATA; Schema: public; Owner: cbustamante
--

COPY usuarios (usuario_id, username, password, roles, empresa_id, version_id) FROM stdin;
1	invitado	a6ae8a143d440ab8c006d799f682d48d	1	1	1
\.


--
-- Name: usuarios_usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: cbustamante
--

SELECT pg_catalog.setval('usuarios_usuario_id_seq', 1, true);


--
-- Name: clientes_pkey; Type: CONSTRAINT; Schema: public; Owner: cbustamante; Tablespace: 
--

ALTER TABLE ONLY clientes
    ADD CONSTRAINT clientes_pkey PRIMARY KEY (cliente_id);


--
-- Name: pk_categorias; Type: CONSTRAINT; Schema: public; Owner: cbustamante; Tablespace: 
--

ALTER TABLE ONLY categorias
    ADD CONSTRAINT pk_categorias PRIMARY KEY (categoria_id);


--
-- Name: roles_pkey; Type: CONSTRAINT; Schema: public; Owner: cbustamante; Tablespace: 
--

ALTER TABLE ONLY roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: usuarios_pkey; Type: CONSTRAINT; Schema: public; Owner: cbustamante; Tablespace: 
--

ALTER TABLE ONLY usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (usuario_id);


--
-- Name: fk_clientes_categorias; Type: FK CONSTRAINT; Schema: public; Owner: cbustamante
--

ALTER TABLE ONLY clientes
    ADD CONSTRAINT fk_clientes_categorias FOREIGN KEY (categoria_id) REFERENCES categorias(categoria_id);


--
-- Name: fk_usuarios_roles1; Type: FK CONSTRAINT; Schema: public; Owner: cbustamante
--

ALTER TABLE ONLY usuarios
    ADD CONSTRAINT fk_usuarios_roles1 FOREIGN KEY (roles) REFERENCES roles(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- Name: roles; Type: ACL; Schema: public; Owner: cbustamante
--

REVOKE ALL ON TABLE roles FROM PUBLIC;
REVOKE ALL ON TABLE roles FROM cbustamante;
GRANT ALL ON TABLE roles TO cbustamante;


--
-- PostgreSQL database dump complete
--

