# SpringBootEmailExample

1. Addjuk hozzá a megfelelő függőségeket:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

2. Levelet a Spring `JavaMailSender` keresztül tudunk küldeni a `MimeMessage` osztályt. Valahogy így:
```java
package com.progmatic.emaildemo.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailSender{

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Cheers from Progmatic!");
            helper.setFrom("hello@progmatic.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.err.println("** failed to send email\n" + e.getMessage());
            throw new IllegalStateException("failed to send email");
        }
    }
}

```

3. Ahhoz hogy a mailSender-t be tudja illeszteni a Spring nem szabad kihagynunk a konfigurációt a `properies` fileból.
Ezek az `SMTP` szerverhez való kapcsolódást írják le.
```properties

spring.mail.host=localhost
spring.mail.port=1025
spring.mail.username=hello
spring.mail.password=hello
spring.mail.properties.mail.smtp.ssl.trust=*
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.connectiontimeout=5000
spring.mail.properties.mail.smtp.timeout=3000
spring.mail.properties.mail.smtp.writetimeout=5000

```
