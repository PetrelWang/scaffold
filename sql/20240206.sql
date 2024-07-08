CREATE TABLE `default`.daily_production (
                                            daily_date DateTime,
                                            well_name VARCHAR(50),
                                            production_time FLOAT,
                                            oil_pressure FLOAT,
                                            casing_pressure FLOAT,
                                            gas FLOAT,
                                            water FLOAT,
                                            in_pressure FLOAT,
                                            remark VARCHAR(100),
                                            id VARCHAR(50),
                                            well_id VARCHAR(100)
) ENGINE = Log;
