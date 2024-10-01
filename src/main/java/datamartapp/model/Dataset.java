package datamartapp.model;

import datamartapp.model.users.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

//todo maybe add new fields, needs to elaborate more detailed

@Data
@Builder
@Entity
@Table(name = "datasets")
public class Dataset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

/*    //Todo relation with connection id
    private Connection connection;*/

    @NotBlank(message = "empty query")
    private String scheme;

    @Column(name = "table_name")
    @NotBlank(message = "empty table name")
    private String tableName;

    @NotNull(message = "empty created")
    private LocalDateTime created;
}
