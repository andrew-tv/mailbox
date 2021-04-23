package agency.july.mailbox.model;

import lombok.Getter;
import lombok.Setter;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.search.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EmailFilter {
    private String from;
    private String to;
    private String subject;
    private Boolean seen;

    public SearchTerm createSearchTerm() {
        List<SearchTerm> searchTerms = new ArrayList<>();
        if (from != null) {
            searchTerms.add(new FromStringTerm(from));
        }
        if (to != null) {
            searchTerms.add(new RecipientStringTerm(Message.RecipientType.TO, to));
        }
        if (subject != null) {
            searchTerms.add(new SubjectTerm(subject));
        }
        if (seen != null) {
            searchTerms.add(new FlagTerm(new Flags(Flags.Flag.SEEN), seen));
        }
        return new AndTerm (searchTerms.toArray(new SearchTerm[0]));
    }
}
