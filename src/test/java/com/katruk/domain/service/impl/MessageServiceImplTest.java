package com.katruk.domain.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.katruk.domain.service.MessageService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

@RunWith(JUnit4.class)
public class MessageServiceImplTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();
  @Mock
  private HttpSession httpSession;
  private List<MessageServiceImpl.Message> messages;
  private MessageService messageService;

  @Before
  public void setUp() throws Exception {
    this.messageService = new MessageServiceImpl(httpSession);
    this.messages = new ArrayList<>();
  }

  @Test
  public void addInfoMessage_text() throws Exception {
    //given
    String msg = "info";

    //when
    when(this.httpSession.getAttribute(anyString())).thenReturn(this.messages);
    this.messageService.addInfo(msg);
    String result = this.messages.get(0).getText();

    //then
    assertEquals(msg, result);
  }

  @Test
  public void addInfoMessage_type() throws Exception {
    //given
    String msg = "info";
    MessageServiceImpl.MessageType mType = MessageServiceImpl.MessageType.INFO;

    //when
    when(this.httpSession.getAttribute(anyString())).thenReturn(this.messages);
    this.messageService.addInfo(msg);
    String result = this.messages.get(0).getType().toString();

    //then
    assertEquals("INFO", result);
  }

  @Test
  public void addErrorMessage_text() throws Exception {
    //given
    String msg = "error";

    //when
    when(this.httpSession.getAttribute(anyString())).thenReturn(messages);
    this.messageService.addError(msg);
    String result = messages.get(0).getText();

    //then
    assertEquals(msg, result);
  }

  @Test
  public void addErrorMessage_type() throws Exception {
    //given
    String msg = "error";
    MessageServiceImpl.MessageType mType = MessageServiceImpl.MessageType.ERROR;

    //when
    when(this.httpSession.getAttribute(anyString())).thenReturn(this.messages);
    this.messageService.addError(msg);
    String result = this.messages.get(0).getType().toString();

    //then
    verify(this.httpSession, times(1)).getAttribute(anyString());
    assertEquals("ERROR", result);
  }

  @Test
  public void addErrorMessage_was_empty() throws Exception {
    //given
    String msg = "error";

    //when
    when(this.httpSession.getAttribute(anyString())).thenReturn(null);
    this.messageService.addError(msg);

    //then
    verify(this.httpSession, times(1)).getAttribute(anyString());
    assertTrue(this.messages.isEmpty());
  }
}