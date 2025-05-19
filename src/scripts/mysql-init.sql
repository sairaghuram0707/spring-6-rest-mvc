DROP DATABASE IF EXISTS restdb;
DROP USER IF EXISTS `restadmin`@`%`;
CREATE DATABASE IF NOT EXISTS restdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS `restadmin`@`%` IDENTIFIED BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW,
CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `restdb`.* TO `restadmin`@`%`;


# mysql-server is the container name (PODMAN)

# podman stop mysql-server
# podman rm mysql-server

# Pulls the image runs it on the container and exposes it for connection
# podman run --name mysql-server -e MYSQL_ROOT_PASSWORD=yourpassword -p 3306:3306 -d docker.io/library/mysql:9.3
# Directly starting from podman not exposing port(Issues)

# Enter MySQL from your container:
# podman exec -it mysql-server mysql -u root -p

# Then execute the above commands (SQL COMMANDS)
# Connect to dbeaver with the above credentials