package agency.july.mailbox.service;

import agency.july.mailbox.MailboxConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import java.util.Properties;

@Service
public class ImapClient {

    @Autowired
    private MailboxConfig mailboxConfig;

    private Folder folder;

    public Folder getFolderInstance() {
        if (folder == null) {
            System.out.println(mailboxConfig);
            try {

                Properties properties = new Properties();

                properties.setProperty("mail.store.protocol", mailboxConfig.getProtocol());
                properties.put("mail.imaps.host", mailboxConfig.getHost());
                properties.put("mail.imaps.port", mailboxConfig.getPort());
//            properties.put("mail.imaps.starttls.enable", "true");
                Session emailSession = Session.getDefaultInstance(properties);

                Store store = emailSession.getStore(mailboxConfig.getProtocol());

                store.connect(mailboxConfig.getHost(), mailboxConfig.getLogin(), mailboxConfig.getPassword());

                folder = store.getFolder(mailboxConfig.getFolder());

            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return folder;
    }

}
