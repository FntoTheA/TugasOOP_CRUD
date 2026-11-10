CREATE TABLE operasi (
    op_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    bil_1 INT NOT NULL,
    opr CHAR(1) NOT NULL CHECK (opr IN ('+', '-', 'x', '/')),
    bil_2 INT NOT NULL
);