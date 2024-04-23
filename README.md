<h1 align="center">:file_cabinet: LOJA VIRTUAL - MENTORIA - JDEV - JAVA TREINAMENTOS:blue_car: </h1>

## :memo: Descrição

Codding Challenge Open Source da Uber para a construção de um Email Service Provider utilizando a AWS SES que faz parte [desse desafio](https://github.com/uber-archive/coding-challenge-tools/blob/master/coding_challenge.md) para pessoas desenvolvedoras backend.

## :books: Funcionalidades
* <b>Funcionalidade</b>: Transferência entre contas
## :wrench: Tecnologias utilizadas
* [Spring Boot](https://spring.io/projects/spring-boot)
* [AWS SES](https://aws.amazon.com/pt/ses/)
* [AWS IAM](https://aws.amazon.com/pt/iam/?gclid=CjwKCAiA8sauBhB3EiwAruTRJhfg809g4bXpZbzwi2E7PcHnUL7Dr5lfaOa3Jyu092P4E1et3ZQrCRoChIUQAvD_BwE&trk=d0aa6e63-b594-43fc-8101-c312f3d653ac&sc_channel=ps&ef_id=CjwKCAiA8sauBhB3EiwAruTRJhfg809g4bXpZbzwi2E7PcHnUL7Dr5lfaOa3Jyu092P4E1et3ZQrCRoChIUQAvD_BwE:G:s&s_kwcid=AL!4422!3!651510165342!e!!g!!amazon%20iam!19836375520!149589163320)

## :rocket: Práticas adotadas

- Clean Arquiteture
- Adapter Pattern
- Email Service Provider
- Boas práticas
- Tratamento de respostas de erro

### 🔧 Instalação

* Clonar repositório git

```
$ ./mvnw clean package
```
- Executar a aplicação:
```
$ java -jar target/desafio-uberemailsend-0.0.1-SNAPSHOT.jar
```

- Update application.properties puting your AWS Credentials:

```
$ aws.region=us-east-1
$ aws.accessKeyId=1111111
$ aws.secretKey=111111
```

* Usage
Start the application with Maven
The API will be accessible at http://localhost:8080
API Endpoints
The API provides the following endpoints:

## :email:  GET EMAIL

* POST /api/email/send - Send a e-mail from your sender to the destination

- BODY

```
{
  "to": "liveskipperdev@gmail.com",
  "subject": "teste",
  "body": "teste"
}

```
## :soon: Implementação futura

* Implementações de segurança
* Implementações de testes unitários
* Implementações de lock e threads para evitar concorrencia de transações(race conditions)

## :dart: Status do projeto
 * :punch: Andamento

