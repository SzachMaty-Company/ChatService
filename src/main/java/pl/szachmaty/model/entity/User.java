package pl.szachmaty.model.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.szachmaty.model.value.UserId;

import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`user`", schema = "chat_service")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UserId userId;
    private String username;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_chat",
            schema = "chat_service",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
    private Set<Chat> chats;

    @Override
    public String toString() {
        return userId == null ? "Null Id" : userId.getId();
    }

}
