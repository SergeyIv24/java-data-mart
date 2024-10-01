package datamartapp.model;

import datamartapp.model.users.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "sql_queries")
public class SqlQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "empty query")
    private String query;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "empty crated")
    private LocalDateTime created;

}
