package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.example.demo.components.JsonConversion;
import com.example.demo.getsockets.GetIssue;
import com.example.demo.getsockets.GetNotice;
import com.example.demo.getsockets.GetSession;
import com.example.demo.getsockets.GetVoiceRecognition;
import com.example.demo.sendsockets.SendNotice;
import com.example.demo.sendsockets.SendSession;
import com.example.demo.sendsockets.SendVoiceRecognition;
import com.example.demo.sendsockets.SendIssue;

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
	public SendVoiceRecognition SocketManage_VoiceRecog(GetVoiceRecognition get_voice) throws Exception {
		// マルチスレッド処理中のCPUの負荷の抑え
		Thread.sleep(1000);
		// コンストラクタの呼び出し
		return new SendVoiceRecognition(HtmlUtils.htmlEscape(get_voice.getVoicetext()));
	}

	//授業内問題解答
	@MessageMapping("/send_answer")
	@SendTo("/queue/greetings")
	public void SocketManage_Issue(GetIssue getIssue) throws Exception {
		// マルチスレッド処理中のCPUの負荷の抑え
		Thread.sleep(1000);
		// 送信データをJson形式に変換、SocketMessageクラスにセット
		String response_message = json.ObjectToJSON(new SendIssue(getIssue.getIssue(),getIssue.getAnswer()));	
		// 送信先ユーザーに送信
		messagingTemplate.convertAndSendToUser("101825939669419593829", "/queue/greetings", response_message);
	}
	
	//セッションid送信
	@MessageMapping(value="/send_sessionid")
	@SendTo("/topic/send_sessionid")
	public SendSession SocketManage_SessionId(GetSession getsession) throws Exception{
		// マルチスレッド処理中のCPUの負荷の抑え
		Thread.sleep(1000);
		
		System.out.println("送信するセッションid" + getsession.getSession_id());
		return new SendSession(HtmlUtils.htmlEscape(getsession.getSession_id()));
	}
	
	//通知取得、表示クラス
	@MessageMapping("/send_notice")
	@SendTo("/queue/notice")
	public void SocketManage_Notice(GetNotice getNotice) throws Exception {
		// マルチスレッド処理中のCPUの負荷の抑え
		Thread.sleep(1000);

		// 送信データをJson形式に変換、SocketNoticeクラスにセット
		String response_message = json.ObjectToJSON(new SendNotice(getNotice.getStudent_name(),getNotice.getStudent_classno()));
		// 送信先ユーザーに送信
		messagingTemplate.convertAndSendToUser(getNotice.getTeacher_sessionid(), "/queue/notice", response_message);
	}
}