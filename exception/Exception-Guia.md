# Guia de Códigos HTTP e Exceções Java

Este guia apresenta os códigos de status HTTP mais comuns e as exceções Java que tipicamente resultam em cada código.

## ✅ Códigos de Sucesso (2xx)

### 200 - OK
**Descrição:** Requisição bem-sucedida com dados retornados no corpo da resposta.

**Quando usar:** GET, PUT que retorna dados, operações de consulta.



---

### 201 - Created
**Descrição:** Recurso criado com sucesso.

**Quando usar:** POST para criação de novos recursos.



---

### 204 - No Content
**Descrição:** Operação bem-sucedida sem conteúdo no corpo da resposta.

**Quando usar:** DELETE, PUT que não retorna dados.



---

## ❌ Códigos de Erro do Cliente (4xx)

### 400 - Bad Request
**Descrição:** Dados inválidos, malformados ou requisição mal estruturada.

**Exceções Comuns:**
- `MethodArgumentNotValidException` - Validação @Valid falhou
- `BindException` - Erro no binding de dados
- `HttpMessageNotReadableException` - JSON malformado
- `MissingServletRequestParameterException` - Parâmetro obrigatório ausente
- `ConstraintViolationException` - Bean Validation
- `IllegalArgumentException` - Argumentos inválidos



---

### 401 - Unauthorized
**Descrição:** Usuário não autenticado - token ausente, inválido ou expirado.

**Exceções Comuns:**
- `AuthenticationException` - Spring Security geral
- `BadCredentialsException` - Credenciais inválidas
- `InsufficientAuthenticationException` - Token ausente/inválido
- `JwtException` - JWT inválido
- `UsernameNotFoundException` - Usuário não encontrado



---

### 403 - Forbidden
**Descrição:** Usuário autenticado mas sem permissão para acessar o recurso.

**Exceções Comuns:**
- `AccessDeniedException` - Spring Security
- `InsufficientAuthenticationException` - Sem permissão adequada
- `AuthorizationServiceException` - Autorização negada



---

### 404 - Not Found
**Descrição:** Recurso solicitado não foi encontrado.

**Exceções Comuns:**
- `EntityNotFoundException` - JPA/Hibernate
- `NoSuchElementException` - Java padrão
- `ResourceNotFoundException` - Custom/Spring
- `EmptyResultDataAccessException` - Spring Data



---

### 409 - Conflict
**Descrição:** Conflito de estado - recurso já existe ou violação de regra de negócio.

**Exceções Comuns:**
- `DataIntegrityViolationException` - Violação de constraint do BD
- `DuplicateKeyException` - Chave duplicada
- `OptimisticLockingFailureException` - Conflito de concorrência



---

## 💥 Códigos de Erro do Servidor (5xx)

### 500 - Internal Server Error
**Descrição:** Erro interno não tratado da aplicação.

**Exceções Comuns:**
- `RuntimeException` - Qualquer runtime exception não capturada
- `NullPointerException` - NPE não tratada
- `DataAccessException` - Erro de acesso a dados
- `TransactionSystemException` - Problemas de transação



---

## 📋 Resumo Prático

| Código | Nome | Significado | Categoria |
|--------|------|-------------|-----------|
| **200** | OK | Sucesso com dados | ✅ Sucesso |
| **201** | Created | Recurso criado | ✅ Sucesso |
| **204** | No Content | Sucesso sem dados | ✅ Sucesso |
| **400** | Bad Request | Dados inválidos | ❌ Cliente |
| **401** | Unauthorized | Não autenticado | ❌ Cliente |
| **403** | Forbidden | Sem permissão | ❌ Cliente |
| **404** | Not Found | Não encontrado | ❌ Cliente |
| **409** | Conflict | Conflito de estado | ❌ Cliente |
| **500** | Internal Server Error | Erro do servidor | 💥 Servidor |

## 🛠️ Exemplo de Handler Global

```java
@ControllerAdvice
public class GlobalExceptionHandler {

    // 400 - Bad Request
    @ExceptionHandler({
        MethodArgumentNotValidException.class,
        IllegalArgumentException.class,
        BindException.class
    })
    public ResponseEntity<?> handleBadRequest(Exception ex) {
        return ResponseEntity.status(400).body("Requisição inválida");
    }

    // 401 - Unauthorized
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleUnauthorized(AuthenticationException ex) {
        return ResponseEntity.status(401).body("Credenciais inválidas");
    }

    // 403 - Forbidden
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleForbidden(AccessDeniedException ex) {
        return ResponseEntity.status(403).body("Acesso negado");
    }

    // 404 - Not Found
    @ExceptionHandler({
        EntityNotFoundException.class,
        NoSuchElementException.class
    })
    public ResponseEntity<?> handleNotFound(Exception ex) {
        return ResponseEntity.status(404).body("Recurso não encontrado");
    }

    // 409 - Conflict
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleConflict(DataIntegrityViolationException ex) {
        return ResponseEntity.status(409).body("Conflito de dados");
    }

    // 500 - Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleInternalError(Exception ex) {
        return ResponseEntity.status(500).body("Erro interno");
    }
}
```

---

## 💡 Dicas Importantes

- **Múltiplas exceções** podem resultar no mesmo código HTTP
- **Use @ControllerAdvice** para tratamento global de exceções
- **Retorne mensagens claras** para ajudar o cliente a entender o erro
- **Log erros 5xx** para debugging
- **Não exponha detalhes internos** em mensagens de erro para o cliente