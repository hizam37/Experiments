package searchengine.config;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAgents {
    private String agent;

    @Override
    public String toString() {
        return agent;
    }


}
