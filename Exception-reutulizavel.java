import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class TratadorDeErros {

    private static final Logger logger = LoggerFactory.getLogger(TratadorDeErros.class);

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<DadosErroResposta> tratarErroRegraDeNegocio(ValidacaoException ex) {
        return ResponseEntity.badRequest()
                .body(new DadosErroResposta(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<DadosErroResposta> tratarErro404() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new DadosErroResposta(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "Recurso não encontrado"
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DadosErroResposta> tratarErro400(MethodArgumentNotValidException ex) {
        List<DadosErroValidacao> erros = ex.getFieldErrors().stream()
                .map(DadosErroValidacao::new)
                .toList();
        return ResponseEntity.badRequest()
                .body(new DadosErroResposta(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Erro de validação",
                        erros
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<DadosErroResposta> tratarErro400(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest()
                .body(new DadosErroResposta(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Erro na leitura do corpo da requisição. Verifique o JSON enviado."
                ));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<DadosErroResposta> tratarErroBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new DadosErroResposta(
                        LocalDateTime.now(),
                        HttpStatus.UNAUTHORIZED.value(),
                        "Credenciais inválidas"
                ));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<DadosErroResposta> tratarErroAuthentication() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new DadosErroResposta(
                        LocalDateTime.now(),
                        HttpStatus.UNAUTHORIZED.value(),
                        "Falha na autenticação"
                ));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<DadosErroResposta> tratarErroAcessoNegado() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new DadosErroResposta(
                        LocalDateTime.now(),
                        HttpStatus.FORBIDDEN.value(),
                        "Acesso negado"
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DadosErroResposta> tratarErro500(Exception ex) {
        logger.error("Erro interno no servidor", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new DadosErroResposta(
                        LocalDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Erro interno no servidor: " + ex.getLocalizedMessage()
                ));
    }

    // DTO padronizado para erros simples e complexos
    private record DadosErroResposta(
            LocalDateTime timestamp,
            int status,
            String mensagem,
            List<DadosErroValidacao> erros
    ) {
        public DadosErroResposta(LocalDateTime timestamp, int status, String mensagem) {
            this(timestamp, status, mensagem, null);
        }
    }

    // DTO para erros de validação de campos
    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
