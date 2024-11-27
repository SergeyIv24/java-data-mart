package datamartapp.model;

import datamartapp.model.users.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "datasets")
@AllArgsConstructor
@NoArgsConstructor
public class Dataset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "connection_id")
    private Connection connection;

    @NotBlank(message = "empty scheme")
    private String scheme;

    @Column(name = "table_name")
    @NotBlank(message = "empty table name")
    private String tableName;

    @NotNull(message = "empty created")
    private String created;
}
