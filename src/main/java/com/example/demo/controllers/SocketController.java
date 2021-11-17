package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.example.demo.components.JsonConversion;
import com.example.demo.entities.SessionEntity;
import com.example.demo.getsockets.GetIssue;
import com.example.demo.getsockets.GetNotice;
import com.example.demo.getsockets.GetSession;
import com.example.demo.getsockets.GetVoiceRecognition;
import com.example.demo.repositories.SessionRepository;
import com.example.demo.sendsockets.SendIssue;
import com.example.demo.sendsockets.SendNotice;
import com.example.demo.sendsockets.SendSession;
import com.example.demo.sendsockets.SendVoiceRecognition;

//Websocketコントローラー
@Controller
public class SocketController {
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	@Autowired
	private SessionRepository sessionRepository;
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
		try {
			// 送信データをJson形式に変換、SocketMessageクラスにセット
			String response_message = json.ObjectToJSON(new SendIssue(getIssue.getIssue(),getIssue.getAnswer()));
			
			//授業中のクラスの学生セッションidを取得
			String classid = getIssue.getClassid();
			System.out.println("クラス" + classid);
			List<SessionEntity> list_sessionEntity = sessionRepository.SearchStudentInClass(classid);
			for(int i=0; i<list_sessionEntity.size(); i++) {
				String session_id = null;
				session_id = list_sessionEntity.get(i).getSessionid();
				// 送信
				messagingTemplate.convertAndSendToUser(session_id, "/queue/greetings", response_message);
			}
		}catch(Exception e) {
			System.out.println("SocketManage_Issue：fail" + e);
		}
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