package datamartapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "connections")
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "empty host")
    private String host;

    @NotNull(message = "empty port")
    private Long port;

    @Column(name = "db_name")
    @NotBlank(message = "empty db_name")
    private String dbName;

    @Column(name = "db_user")
    @NotBlank(message = "empty db_user")
    private String dbUser;

    @Column(name = "db_password")
    @NotBlank(message = "empty db_password")
    private String dbPassword;

    @Column(name = "display_name")
    @NotBlank(message = "empty display name")
    private String displayName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "empty created")
    private LocalDateTime created;

}
