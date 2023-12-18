package dev.mentoria.lojavirtual_mentoria.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String login;

    private String senha;

    @Temporal(TemporalType.DATE)
    private Date dataAtualSenha;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuarios_acessos",
            uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "acesso_id"},
            name = "unique_acesso_user"),
            joinColumns = @JoinColumn(name = "usuario_id" , referencedColumnName = "id", table = "usuario", unique = false,
            foreignKey = @ForeignKey(name = "usuario_fk", value = ConstraintMode.CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "acesso_id", unique = false, referencedColumnName = "id", table = "acesso",
            foreignKey = @ForeignKey(name = "acesso_fk", value = ConstraintMode.CONSTRAINT))

    )
    private List<Acesso> acessos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //* Autoridades = São os acessos/papeis/autoridades/perfil, ou seja ROLE_ADMIN, ROLE_SECRETARIO, ROLE_FINANCEIRO ETC.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.acessos;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
