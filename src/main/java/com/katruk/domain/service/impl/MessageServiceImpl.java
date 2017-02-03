package com.katruk.domain.service.impl;

import com.katruk.domain.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

@Service
public class MessageServiceImpl implements MessageService {

  public static final String MSG_SESSION_KEY = "siteMessages";
  private final HttpSession httpSession;

  @Autowired
  public MessageServiceImpl(HttpSession httpSession) {
    this.httpSession = httpSession;
  }

  @Override
  public void addInfo(String message) {
    addMessage(MessageType.INFO, message);
  }

  @Override
  public void addError(String message) {
    addMessage(MessageType.ERROR, message);
  }

  private void addMessage(MessageType type, String msg) {
    List<Message> messages = (List<Message>) httpSession.getAttribute(MSG_SESSION_KEY);

    if (messages == null) {
      messages = new ArrayList<>();
    }
    messages.add(new Message(type, msg));
    httpSession.setAttribute(MSG_SESSION_KEY, messages);
  }

  public enum MessageType {
    INFO,
    ERROR
  }

  public class Message {

    MessageType type;
    String text;

    public Message(MessageType type, String text) {
      this.type = type;
      this.text = text;
    }

    public MessageType getType() {
      return type;
    }

    public String getText() {
      return text;
    }
  }
}