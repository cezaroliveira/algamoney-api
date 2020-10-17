@REM Executa uma imagem em um container no docker, compartilhando a mesma rede
docker run -d -p 8080:8080 --name algamoney-api-container algamoney-api-image