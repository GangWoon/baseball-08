package dev.codesquad.java.baseball08.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("player_log")
public class Log {
    @Id
    private Long id;

}
