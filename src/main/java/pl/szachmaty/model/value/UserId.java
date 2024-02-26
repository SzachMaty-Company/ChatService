package pl.szachmaty.model.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class UserId {

    @Column(name = "global_user_id")
    private String id;

    public UserId(String id) {
        this.id = id;
    }

    public UserId() {
    }

}
