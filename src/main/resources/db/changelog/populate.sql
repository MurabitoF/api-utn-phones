-- liquibase sql formatted

-- chageset franco:1
insert into provinces(province_name) values ('Buenos Aires'),
                                            ('Catamarca'),
                                            ('Chaco'),
                                            ('Chubut'),
                                            ('Córdoba'),
                                            ('Corrientes'),
                                            ('Entre Ríos'),
                                            ('Formosa'),
                                            ('Jujuy'),
                                            ('La Pampa'),
                                            ('La Rioja'),
                                            ('Mendoza'),
                                            ('Misiones'),
                                            ('Neuquén'),
                                            ('Río Negro'),
                                            ('Salta'),
                                            ('San Juan'),
                                            ('San Luis'),
                                            ('Santa Cruz'),
                                            ('Santa Fe'),
                                            ('Santiago del Estero'),
                                            ('Tierra del Fuego'),
                                            ('Tucumán');

-- rollback truncate table provinces;
