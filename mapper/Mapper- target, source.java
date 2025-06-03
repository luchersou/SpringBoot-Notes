DTO:

public record DadosCadastroMedico(
    String nome,
    DadosEndereco enderecoCompleto  
) {}

================

Entidade:

@Entity
public class Medico {
    private String nome;
    @Embedded
    private Endereco endereco;  
}

================

@Mapper
public interface MedicoMapper {
    
    @Mapping(target = "endereco", source = "enderecoCompleto")
    Medico toEntity(DadosCadastroMedico dados);
}