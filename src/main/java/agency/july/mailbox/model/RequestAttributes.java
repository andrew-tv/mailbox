package agency.july.mailbox.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestAttributes {
    private EmailFilter filter;
    private String selector;
    private String attribute;
}
