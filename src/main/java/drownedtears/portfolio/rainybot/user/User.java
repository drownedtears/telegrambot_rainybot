package drownedtears.portfolio.rainybot.user;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User entity
 */
@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "chat_id")
    private String chatId;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "city")
    private String city;

    @Column(name = "time")
    private String time;
}
