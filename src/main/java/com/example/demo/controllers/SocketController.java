package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.example.demo.components.JsonConversion;
import com.example.demo.getsockets.GetForm;
import com.example.demo.getsockets.GetVoiceRecognition;
import com.example.demo.sendsockets.SendVoiceRecognition;
import com.example.demo.sendsockets.SocketMessage;

//Websocketコントローラー
@Controller
public class SocketController {
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	JsonConversion json;

	// socket通信(音声認識)
	@MessageMapping("/voice_recog")
	@SendTo("/topic/voice_recog")
	public SendVoiceRecognition VoiceRecog_SocketManage(GetVoiceRecognition get_voice) throws Exception {
		// マルチスレッド処理中のCPUの負荷の抑え
		Thread.sleep(1000);
		// コンストラクタの呼び出し
		return new SendVoiceRecognition(HtmlUtils.htmlEscape(get_voice.getVoicetext()));
	}

	//
	@MessageMapping("/send_answer")
	@SendTo("/queue/greetings")
	public void greeting(GetForm getForm) throws Exception {
		// マルチスレッド処理中のCPUの負荷の抑え
		Thread.sleep(1000);

		// 送信データをJson形式に変換、SocketMessageクラスにセット
		String response_message = json.SocketToJSON(new SocketMessage(getForm.getMessage()));
		System.out.println("送信者" + getForm.getSendtoname());
		System.out.println("受信者" + getForm.getName());
		// 送信先ユーザーに送信
		messagingTemplate.convertAndSendToUser(getForm.getSendtoname(), "/queue/greetings", response_message);
		// 自分に送信
		messagingTemplate.convertAndSendToUser(getForm.getName(), "/queue/greetings", response_message);
	}
}