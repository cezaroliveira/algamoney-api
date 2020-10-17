@REM Executa uma imagem no docker montando um volume
@REM Erro: Client does not support authentication protocol requested by server; consider upgrading MySQL client
@REM Solução: Passar o argumento --default-authentication-plugin=mysql_native_password para o MySQL
@REM docker run -d -v %cd%/src/main/resources/db/data:/var/lib/mysql -p 3306:3306 --rm --name mysql-container mysql-image --default-authentication-plugin=mysql_native_password
docker run -d --network algamoney-network -v %cd%/data:/var/lib/mysql -p 3306:3306 --rm --name mysql-container mysql-image --default-authentication-plugin=mysql_native_password