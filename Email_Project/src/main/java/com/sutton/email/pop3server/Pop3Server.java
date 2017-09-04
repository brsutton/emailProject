package com.sutton.email.pop3server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sutton.email.service.UserService;

import model.User;

@Component
public class Pop3Server {
	@Autowired
	UserService userService;
	private String hostval = "pop.gmail.com";
	private String mailStrProt = "pop3";
	private String uname = "brianleesutton1978@gmail.com";
	private String password = "somePassword123";

	public int checkMail() {
		try {
			Properties props = new Properties();
			props.put("mail.pop3.host", hostval);
			props.put("mail.pop3.port", "995");
			props.put("mail.pop3.starttls.enable", "true");
			Session emailSession = Session.getDefaultInstance(props);
			Store store = emailSession.getStore("pop3s");
			store.connect(hostval, uname, password);
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);
			Message[] messages = emailFolder.getMessages();
			// ArrayList<File> attachments = new ArrayList<>();
			System.out.println(messages.length);
			for (Message message : messages) {
				System.out.println(message.getContent().toString());

				if (message.getContentType().contains("multipart")) {

					Multipart multiPart = (Multipart) message.getContent();

					for (int i = 0; i < multiPart.getCount(); i++) {
						MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
						if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
							String destFilePath = "C:/tmp/" + part.getFileName();
							FileOutputStream output = new FileOutputStream(destFilePath);
							InputStream input = part.getInputStream();
							byte[] buffer = new byte[4096];
							int byteRead;

							while ((byteRead = input.read(buffer)) != -1) {
								output.write(buffer, 0, byteRead);
							}
							output.close();
							part.saveFile("C:/tmp/" + part.getFileName());
							Scanner scanner = new Scanner(new File(destFilePath));
							scanner.useDelimiter(",");
							String userDetails ="";
							
							while(scanner.hasNextLine()) {
								userDetails = scanner.nextLine();
								String [] details = userDetails.split(",");
								User user = new User();
								user.setName(details[0]);
								user.setUserName(details[1]);
								user.setContact(details[2]);
								user.setPassword(details[3]);
								if(userService.addUser(user) == 0 ) {
									scanner.close();
									return 0;
								}
								
							}
							scanner.close();
							return 1;
							
						}
					}

				}

			}

			emailFolder.close(false);
			store.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}

}
