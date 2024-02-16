PGDMP                         |            bank_services    12.11    12.11 -    :           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            ;           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            <           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            =           1262    114688    bank_services    DATABASE     �   CREATE DATABASE bank_services WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';
    DROP DATABASE bank_services;
                postgres    false            �            1259    147746    tbl_accounts    TABLE     e  CREATE TABLE public.tbl_accounts (
    id bigint NOT NULL,
    account_no character varying(255) NOT NULL,
    open_date timestamp(6) without time zone NOT NULL,
    acc_type character varying(255) NOT NULL,
    total_balance double precision NOT NULL,
    blocked_date timestamp(6) without time zone,
    acc_status integer NOT NULL,
    user_id bigint
);
     DROP TABLE public.tbl_accounts;
       public         heap    postgres    false            �            1259    147744    tbl_accounts_id_seq    SEQUENCE     |   CREATE SEQUENCE public.tbl_accounts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.tbl_accounts_id_seq;
       public          postgres    false    207            >           0    0    tbl_accounts_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.tbl_accounts_id_seq OWNED BY public.tbl_accounts.id;
          public          postgres    false    206            �            1259    131155    tbl_accounts_seq    SEQUENCE     z   CREATE SEQUENCE public.tbl_accounts_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.tbl_accounts_seq;
       public          postgres    false            �            1259    147759    tbl_registers    TABLE     �  CREATE TABLE public.tbl_registers (
    id bigint NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    dob date NOT NULL,
    gender character varying(255) NOT NULL,
    phone character varying(20) NOT NULL,
    password character varying(255) NOT NULL,
    address character varying(200),
    register_date timestamp(6) without time zone NOT NULL,
    role_id bigint
);
 !   DROP TABLE public.tbl_registers;
       public         heap    postgres    false            �            1259    147757    tbl_registers_id_seq    SEQUENCE     }   CREATE SEQUENCE public.tbl_registers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.tbl_registers_id_seq;
       public          postgres    false    209            ?           0    0    tbl_registers_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.tbl_registers_id_seq OWNED BY public.tbl_registers.id;
          public          postgres    false    208            �            1259    131157    tbl_registers_seq    SEQUENCE     {   CREATE SEQUENCE public.tbl_registers_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.tbl_registers_seq;
       public          postgres    false            �            1259    147772 	   tbl_roles    TABLE     d   CREATE TABLE public.tbl_roles (
    id bigint NOT NULL,
    name character varying(100) NOT NULL
);
    DROP TABLE public.tbl_roles;
       public         heap    postgres    false            �            1259    147770    tbl_roles_id_seq    SEQUENCE     y   CREATE SEQUENCE public.tbl_roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.tbl_roles_id_seq;
       public          postgres    false    211            @           0    0    tbl_roles_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.tbl_roles_id_seq OWNED BY public.tbl_roles.id;
          public          postgres    false    210            �            1259    131159    tbl_roles_seq    SEQUENCE     w   CREATE SEQUENCE public.tbl_roles_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.tbl_roles_seq;
       public          postgres    false            �            1259    147780    tbl_transactions    TABLE       CREATE TABLE public.tbl_transactions (
    id bigint NOT NULL,
    batch_id character varying(255) NOT NULL,
    txn_date timestamp(6) without time zone NOT NULL,
    amount double precision NOT NULL,
    txn_code integer NOT NULL,
    account_id bigint
);
 $   DROP TABLE public.tbl_transactions;
       public         heap    postgres    false            �            1259    147778    tbl_transactions_id_seq    SEQUENCE     �   CREATE SEQUENCE public.tbl_transactions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.tbl_transactions_id_seq;
       public          postgres    false    213            A           0    0    tbl_transactions_id_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.tbl_transactions_id_seq OWNED BY public.tbl_transactions.id;
          public          postgres    false    212            �            1259    131161    tbl_transactions_seq    SEQUENCE     ~   CREATE SEQUENCE public.tbl_transactions_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.tbl_transactions_seq;
       public          postgres    false            �
           2604    147749    tbl_accounts id    DEFAULT     r   ALTER TABLE ONLY public.tbl_accounts ALTER COLUMN id SET DEFAULT nextval('public.tbl_accounts_id_seq'::regclass);
 >   ALTER TABLE public.tbl_accounts ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    206    207    207            �
           2604    147762    tbl_registers id    DEFAULT     t   ALTER TABLE ONLY public.tbl_registers ALTER COLUMN id SET DEFAULT nextval('public.tbl_registers_id_seq'::regclass);
 ?   ALTER TABLE public.tbl_registers ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    208    209    209            �
           2604    147775    tbl_roles id    DEFAULT     l   ALTER TABLE ONLY public.tbl_roles ALTER COLUMN id SET DEFAULT nextval('public.tbl_roles_id_seq'::regclass);
 ;   ALTER TABLE public.tbl_roles ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    211    210    211            �
           2604    147783    tbl_transactions id    DEFAULT     z   ALTER TABLE ONLY public.tbl_transactions ALTER COLUMN id SET DEFAULT nextval('public.tbl_transactions_id_seq'::regclass);
 B   ALTER TABLE public.tbl_transactions ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    213    212    213            1          0    147746    tbl_accounts 
   TABLE DATA           }   COPY public.tbl_accounts (id, account_no, open_date, acc_type, total_balance, blocked_date, acc_status, user_id) FROM stdin;
    public          postgres    false    207   T4       3          0    147759    tbl_registers 
   TABLE DATA           �   COPY public.tbl_registers (id, first_name, last_name, dob, gender, phone, password, address, register_date, role_id) FROM stdin;
    public          postgres    false    209   �4       5          0    147772 	   tbl_roles 
   TABLE DATA           -   COPY public.tbl_roles (id, name) FROM stdin;
    public          postgres    false    211   �5       7          0    147780    tbl_transactions 
   TABLE DATA           `   COPY public.tbl_transactions (id, batch_id, txn_date, amount, txn_code, account_id) FROM stdin;
    public          postgres    false    213   �5       B           0    0    tbl_accounts_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.tbl_accounts_id_seq', 4, true);
          public          postgres    false    206            C           0    0    tbl_accounts_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.tbl_accounts_seq', 2151, true);
          public          postgres    false    202            D           0    0    tbl_registers_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.tbl_registers_id_seq', 3, true);
          public          postgres    false    208            E           0    0    tbl_registers_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.tbl_registers_seq', 201, true);
          public          postgres    false    203            F           0    0    tbl_roles_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.tbl_roles_id_seq', 2, true);
          public          postgres    false    210            G           0    0    tbl_roles_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.tbl_roles_seq', 1, false);
          public          postgres    false    204            H           0    0    tbl_transactions_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.tbl_transactions_id_seq', 3, true);
          public          postgres    false    212            I           0    0    tbl_transactions_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.tbl_transactions_seq', 201, true);
          public          postgres    false    205            �
           2606    147756 (   tbl_accounts tbl_accounts_account_no_key 
   CONSTRAINT     i   ALTER TABLE ONLY public.tbl_accounts
    ADD CONSTRAINT tbl_accounts_account_no_key UNIQUE (account_no);
 R   ALTER TABLE ONLY public.tbl_accounts DROP CONSTRAINT tbl_accounts_account_no_key;
       public            postgres    false    207            �
           2606    147754    tbl_accounts tbl_accounts_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.tbl_accounts
    ADD CONSTRAINT tbl_accounts_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.tbl_accounts DROP CONSTRAINT tbl_accounts_pkey;
       public            postgres    false    207            �
           2606    147769 %   tbl_registers tbl_registers_phone_key 
   CONSTRAINT     a   ALTER TABLE ONLY public.tbl_registers
    ADD CONSTRAINT tbl_registers_phone_key UNIQUE (phone);
 O   ALTER TABLE ONLY public.tbl_registers DROP CONSTRAINT tbl_registers_phone_key;
       public            postgres    false    209            �
           2606    147767     tbl_registers tbl_registers_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.tbl_registers
    ADD CONSTRAINT tbl_registers_pkey PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.tbl_registers DROP CONSTRAINT tbl_registers_pkey;
       public            postgres    false    209            �
           2606    147777    tbl_roles tbl_roles_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.tbl_roles
    ADD CONSTRAINT tbl_roles_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.tbl_roles DROP CONSTRAINT tbl_roles_pkey;
       public            postgres    false    211            �
           2606    147785 &   tbl_transactions tbl_transactions_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public.tbl_transactions
    ADD CONSTRAINT tbl_transactions_pkey PRIMARY KEY (id);
 P   ALTER TABLE ONLY public.tbl_transactions DROP CONSTRAINT tbl_transactions_pkey;
       public            postgres    false    213            �
           2606    147796 !   tbl_transactions tblaccounts_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.tbl_transactions
    ADD CONSTRAINT tblaccounts_fkey FOREIGN KEY (account_id) REFERENCES public.tbl_accounts(id);
 K   ALTER TABLE ONLY public.tbl_transactions DROP CONSTRAINT tblaccounts_fkey;
       public          postgres    false    207    2722    213            �
           2606    147786    tbl_accounts tblregisters_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.tbl_accounts
    ADD CONSTRAINT tblregisters_fkey FOREIGN KEY (user_id) REFERENCES public.tbl_registers(id);
 H   ALTER TABLE ONLY public.tbl_accounts DROP CONSTRAINT tblregisters_fkey;
       public          postgres    false    2726    209    207            �
           2606    147791    tbl_registers tblroles_fkey    FK CONSTRAINT     ~   ALTER TABLE ONLY public.tbl_registers
    ADD CONSTRAINT tblroles_fkey FOREIGN KEY (role_id) REFERENCES public.tbl_roles(id);
 E   ALTER TABLE ONLY public.tbl_registers DROP CONSTRAINT tblroles_fkey;
       public          postgres    false    2728    209    211            1   y   x�]�11D��9���q�.

(v���!���/�i@�uo�U�����H9]?�B��=��y��9��AB��P����-ڸ�g�B��6��ț9
�yR�N�!����?�sJ�D(!�      3   �   x�}��N�0 �s�;p���P��2݆��,��fTmk@����1^|���у���uG�1�Y�cD�ԛ&0�x�<`^~Y�e݃sL�(�������OC�o��*��i�-t�h��fwO��:�(�mf�?#-S��ˀ� ��HV��;'�g�ʪp����$�	d~���Џf-�����Z�(v�Z�j9N��L0�y��c@)=&�J+      5      x�3�tt����2�v����� +��      7   Y   x�e��� ���"�|����:��_�]��1����NUgŸ�-mD�t��Ш����l9�*܍G�-]˿:��2p���[)��LS     