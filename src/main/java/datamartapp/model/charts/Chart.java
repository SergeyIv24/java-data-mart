package datamartapp.model.charts;

import datamartapp.common.ChartType;
import datamartapp.model.users.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "chart")
@Data
public class Chart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank(message = "Name must exist")
    private String name;

    @NotBlank(message = "TableName must exist")
    @Column(name = "table_name")
    private String tableName;

    @Enumerated(EnumType.STRING)
    @Column(name = "chart_type")
    private ChartType chartType;

    @NotNull(message = "Limit must exist")
    @Column(name = "row_limit")
    private Integer limit;
}
