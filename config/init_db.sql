CREATE TABLE resume (
  uuid      TEXT   PRIMARY KEY NOT NULL,
  full_name TEXT                 NOT NULL
);

CREATE TABLE contact (
  id          SERIAL,
  resume_uuid TEXT   NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
  type        TEXT     NOT NULL,
  value       TEXT     NOT NULL
);
CREATE UNIQUE INDEX contact_uuid_type_index
ON contact (resume_uuid, type);

CREATE TABLE section (
  id          SERIAL PRIMARY KEY,
  resume_uuid TEXT     NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
  type        TEXT     NOT NULL,
  description TEXT     NOT NULL
);
CREATE UNIQUE INDEX section_idx
  ON section (resume_uuid, type);