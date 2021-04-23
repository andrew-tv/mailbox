package agency.july.mailbox.controller;

import agency.july.mailbox.model.EmailFilter;
import agency.july.mailbox.model.RequestAttributes;
import agency.july.mailbox.service.ImapClient;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EmailController {

    @Autowired
    private ImapClient imapClient;

    @GetMapping("/hc")
    @ResponseBody
    @SneakyThrows
    String hc() {
        Folder f = imapClient.getFolderInstance();
        f.open(Folder.READ_ONLY);
        int messageCount = f.getMessageCount();
        f.close(false);
        return "Success: " + messageCount + " messages in total";
    }

    @PostMapping("/seen")
    @ResponseBody
    @SneakyThrows
    Integer setAsSeen(@RequestBody EmailFilter emailFilter) {
        Folder f = imapClient.getFolderInstance();
        f.open(Folder.READ_WRITE);
        Message[] messages = f.search( emailFilter.createSearchTerm() );
        Integer count = messages.length;
        System.out.println("messages.length = " + count);

        for (int i = 0; i < count; i++) {
            Message message = messages[i];
            message.setFlag(Flags.Flag.SEEN, true);
            System.out.println("---------------------------------");
            System.out.println("Email Number " + (i + 1));
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + message.getFrom()[0]);
            System.out.println("To: " + message.getRecipients(Message.RecipientType.TO)[0]);
            System.out.println("Seen: " + message.getFlags().contains(Flags.Flag.SEEN));
        }
        f.close(false);
        return count;
    }

    @PostMapping("/attributes")
    @ResponseBody
    @SneakyThrows
    List<List<String>> attributes(@RequestBody RequestAttributes requestAttributes) {
        List<String> result = new ArrayList<>();
        List<List<String>> result2 = new ArrayList<>();

        Folder f = imapClient.getFolderInstance();
        f.open(Folder.READ_ONLY);
        Message[] messages = f.search( requestAttributes.getFilter().createSearchTerm() );
        System.out.println("messages.length = " + messages.length);

        for (int i = 0, n = messages.length; i < n; i++) {
            Message message = messages[i];
            System.out.println("---------------------------------");
            System.out.println("Email Number " + (i + 1));
            System.out.println("Date: " + message.getReceivedDate());
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + message.getFrom()[0]);
            System.out.println("To: " + message.getRecipients(Message.RecipientType.TO)[0]);
            System.out.println("Seen: " + message.getFlags().contains(Flags.Flag.SEEN));

            String content = message.getContent().toString();
            Document doc = Jsoup.parse(content);
            String attr = doc.select(requestAttributes.getSelector()).first().attr(requestAttributes.getAttribute());
            Elements elements = doc.select(requestAttributes.getSelector());
            result2.add(elements.eachAttr(requestAttributes.getAttribute()));

            System.out.println("Attribute: " + attr);
            result.add(attr);
        }

        f.close(false);
        return result2;
    }
}
