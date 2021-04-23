package agency.july.mailbox;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("mailbox")
@Getter
@Setter
public class MailboxConfig {

    private String protocol;
    private String host;
    private String port;
    private String folder;
    private String login;
    private String password;

    @Override
    public String toString() {
        return "Mailbox{" +
                "protocol='" + protocol + '\'' +
                ", host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", folder='" + folder + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
