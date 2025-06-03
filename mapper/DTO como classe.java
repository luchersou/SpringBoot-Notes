ENTIDADE:

@Entity
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    // Construtor padrão (obrigatório para JPA)
    public Medico() {}

    // Getters e Setters (métodos de acesso)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // ... (outros getters e setters para todos os campos)
}

=========================

DTO - DadosCadastroMedico:

public class DadosCadastroMedico {

    private String nome;
    private String email;
    private String crm;
    private Especialidade especialidade;
    private DadosEndereco endereco;

    // Construtor com todos os campos
    public DadosCadastroMedico(String nome, String email, String crm, 
                              Especialidade especialidade, DadosEndereco endereco) {
        this.nome = nome;
        this.email = email;
        this.crm = crm;
        this.especialidade = especialidade;
        this.endereco = endereco;
    }

    // Getters (obrigatórios para MapStruct/JPA)
    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCrm() {
        return crm;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public DadosEndereco getEndereco() {
        return endereco;
    }

    // Setters (opcionais, dependendo da necessidade)
    public void setNome(String nome) {
        this.nome = nome;
    }

    // ... (outros setters)
}

=========================

DTO - DadosEndereco:

public class DadosEndereco {
    private String logradouro;
    private String bairro;
    private String cidade;

    public DadosEndereco(String logradouro, String bairro, String cidade) {
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
    }

    // Getters
    public String getLogradouro() {
        return logradouro;
    }

    // ... (outros getters e setters)
}

=========================

MAPPER:

@Mapper(componentModel = "spring")
public interface MedicoMapper {

    @Mapping(target = "id", ignore = true)  // Ignora o ID (gerado automaticamente)
    @Mapping(target = "ativo", constant = "true")  // Define valor padrão
    Medico toEntity(DadosCadastroMedico dados);

    // Submapeamento para o endereço
    Endereco dadosEnderecoToEndereco(DadosEndereco dados);
}
