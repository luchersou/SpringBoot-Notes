@Mapper(componentModel = "spring")
public interface AuthorMapper {

    // Criação (POST)
    Author toEntity(AuthorDTO dto);

    // Retorno de dados (GET)
    AuthorDTO toDTO(Author author);

    // Atualização parcial (PUT ou PATCH)
    @Mapping(target = "id", ignore = true)  // Evita sobrescrever o ID
    void updateAuthorFromDto(AuthorDTO dto, @MappingTarget Author author);
}