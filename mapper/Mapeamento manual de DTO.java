@Service
public class UsuarioMapper {
    
    public UsuarioDTO toDTO(Usuario usuario) {
        return UsuarioDTO.builder()
            .nome(usuario.getNome())
            .email(usuario.getEmail())
            .idade(usuario.getIdade())
            .build();
    }
    
    public Usuario toEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setIdade(dto.getIdade());
        return usuario;
    }
}