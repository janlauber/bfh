package chatapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatService {

	private static final ChatService instance = new ChatService();

	private final List<ChatMessage> messages = new ArrayList<>();
	private int lastId = 0;

	public static ChatService getInstance() {
		return instance;
	}

	private ChatService() {
		try (Scanner scanner = new Scanner(ChatService.class.getResourceAsStream("/messages.txt"))) {
			while (scanner.hasNextLine()) {
				String[] tokens = scanner.nextLine().split(":");
				addMessage(new ChatMessage(tokens[0], tokens[1]));
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public List<String> getSubjects() {
		List<String> result = new ArrayList<>();
		for (ChatMessage message : messages) {
			if (!result.contains(message.getSubject())) {
				result.add(message.getSubject());
			}
		}
		return result;
	}

	public void removeMessage(String id) {
		System.out.println("removeMessage: " + id);
		// search for id in messages
		for (int i = 0; i < messages.size(); i++) {
			if (messages.get(i).getId() == Integer.parseInt(id)) {
				messages.remove(i);
				break;
			}
		}
	}

	public List<ChatMessage> getMessages() {
		return messages;
	}

	public List<ChatMessage> getMessages(String subject) {
		List<ChatMessage> result = new ArrayList<>();
		for (ChatMessage message : messages) {
			if (message.getSubject().equals(subject)) {
				result.add(message);
			}
		}
		return result;
	}

	public void addMessage(ChatMessage message) {
		message.setId(++lastId);
		messages.add(message);
	}
}
