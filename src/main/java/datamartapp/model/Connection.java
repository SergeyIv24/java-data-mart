package datamartapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import datamartapp.model.users.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "connections")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "db_type")
    @NotBlank(message = "db type is empty")
    private String dbType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "empty created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
}
