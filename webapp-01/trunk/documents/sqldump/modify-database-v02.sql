ALTER TABLE lda_v02.Configuration MODIFY value VARCHAR(100);

ALTER TABLE lda_v02.configuration ADD PRIMARY KEY (name);

ALTER TABLE lda_v02.configuration DROP id;