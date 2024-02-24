package pl.szachmaty.model.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class GlobalUserId {

    @Column(name = "global_user_id")
    private String id;

    public GlobalUserId(String id) {
        this.id = id;
    }

    public GlobalUserId() {

    }

    public static GlobalUserId withId(String id) {
        return new GlobalUserId(id);
    }

}
