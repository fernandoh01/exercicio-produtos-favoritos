# Projeto Web para gestão de produtos favoritos

Este projeto objetiva um prototipo para fazer interface de usuários com seus produtos favoritos

## Informações do Projeto

### Tecnologia
- Spring Framework (MVC, JPA, Security)
- Banco de Dados: PostgreSQL
- Java Open JDK 17

### Construção da aplicação
1. Instalar Java Open JDK 17
2. Instalar ultima versão do maven
3. Editar arquivo src/main/resources/application.properties e configurar banco de dados
4. executar `mvn clean install` para construção da aplicação
5. executar `mvn spring-boot:run` para execucao da aplicação (modo desenvolvimento)

**Observações:**
- pode-se executar `mvn test` para rodar testes unitários
- todos endpoints são protegidos por **x-api-key**. Para este projeto está setado hardcoded como **access_token**. Necessário adicionar no header do cliente.

### Débitos
1. Perfil de construção da aplicação em modo de produção
2. Imagem Docker para execução da aplicação
3. Integração do Swagger para visualização das API's