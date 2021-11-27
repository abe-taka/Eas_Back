package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.demo.components.JsonConversion;
import com.example.demo.components.SessionManage;
import com.example.demo.entities.SessionEntity;
import com.example.demo.forms.VoiceRecognitionForm;
import com.example.demo.getsockets.GetAnswer;
import com.example.demo.getsockets.GetBulkExit;
import com.example.demo.getsockets.GetIssue;
import com.example.demo.getsockets.GetNotice;
import com.example.demo.getsockets.GetSession;
import com.example.demo.getsockets.GetVoiceRecognition;
import com.example.demo.repositories.SessionRepository;
import com.example.demo.sendsockets.SendAnswer;
import com.example.demo.sendsockets.SendBulkExit;
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
	@Autowired
	VoiceRecognitionForm voice_Form;
	@Autowired
	SessionManage session_manage;

	// socket通信(音声認識)
	@MessageMapping("/voice_recog")
	public void SocketManage_VoiceRecog(GetVoiceRecognition get_voice) throws Exception {
		// マルチスレッド処理中のCPUの負荷の抑え
		Thread.sleep(1000);

		try {
			// 送信データをJson形式に変換
			String response_message = json.ObjectToJSON(new SendVoiceRecognition(get_voice.getVoicetext()));

			// 自分自身に送信
			String teachersession_id = voice_Form.getTeacher_sessionid();
			System.out.println("先生セッションid" + teachersession_id);
			messagingTemplate.convertAndSendToUser(teachersession_id, "/queue/voice_recog", response_message);

			// 授業中のクラスの学生セッションidを取得
			String class_id = voice_Form.getClassid();
			List<SessionEntity> list_sessionEntity = sessionRepository.SearchStudentInClass(class_id);
			for (int i = 0; i < list_sessionEntity.size(); i++) {
				String session_id = null;
				session_id = list_sessionEntity.get(i).getSessionid();
				// 送信
				messagingTemplate.convertAndSendToUser(session_id, "/queue/voice_recog", response_message);
			}
		} catch (Exception e) {
			System.out.println("[Websocket]SocketManage_VoiceRecog　：　" + e);
		}
	}

	// 授業内問題解答
	@MessageMapping("/send_answer")
	public void SocketManage_Issue(GetIssue getIssue) throws Exception {
		// マルチスレッド処理中のCPUの負荷の抑え
		Thread.sleep(1000);
		try {
			// 送信データをJson形式に変換、SocketMessageクラスにセット
			String response_message = json.ObjectToJSON(new SendIssue(getIssue.getIssue(), getIssue.getAnswer()));

			// 授業中のクラスの学生セッションidを取得
			String class_id = getIssue.getClassid();
			List<SessionEntity> list_sessionEntity = sessionRepository.SearchStudentInClass(class_id);
			for (int i = 0; i < list_sessionEntity.size(); i++) {
				String session_id = null;
				session_id = list_sessionEntity.get(i).getSessionid();
				// 送信
				messagingTemplate.convertAndSendToUser(session_id, "/queue/send_answer", response_message);
			}
		} catch (Exception e) {
			System.out.println("[Websocket]SocketManage_Issue　：　" + e);
		}
	}

	// セッションid送信
	@MessageMapping(value = "/send_sessionid")
	public void SocketManage_SessionId(GetSession getsession) throws Exception {
		// マルチスレッド処理中のCPUの負荷の抑え
		Thread.sleep(1000);

		// クラスidの取得
		String class_id = getsession.getClass_id();

		// 先生のセッションidを取得、Json形式に変換
		String response_message = json.ObjectToJSON(new SendSession(getsession.getSession_id()));

		// 授業中のクラスの学生セッションidを取得
		List<SessionEntity> list_sessionEntity = sessionRepository.SearchStudentInClass(class_id);
		for (int i = 0; i < list_sessionEntity.size(); i++) {
			String session_id = null;
			session_id = list_sessionEntity.get(i).getSessionid();
			// 送信
			messagingTemplate.convertAndSendToUser(session_id, "/queue/send_sessionid", response_message);
		}
	}

	// 通知取得、表示クラス
	@MessageMapping("/send_notice")
	public void SocketManage_Notice(GetNotice getNotice) throws Exception {
		// マルチスレッド処理中のCPUの負荷の抑え
		Thread.sleep(1000);

		// 送信データをJson形式に変換、SocketNoticeクラスにセット
		String response_message = json
				.ObjectToJSON(new SendNotice(getNotice.getStudent_name(), getNotice.getStudent_classno()));
		// 送信先ユーザーに送信
		messagingTemplate.convertAndSendToUser(getNotice.getTeacher_sessionid(), "/queue/notice", response_message);
	}

	// 一括退出
	@MessageMapping("/bulkexit")
	public void SocketManage_BulkExit(GetBulkExit getBulkExit) throws Exception {
		try {
			// マルチスレッド処理中のCPUの負荷の抑え
			Thread.sleep(1000);

			// 送信データをJson形式に変換
			String response_message = json.ObjectToJSON(new SendBulkExit("1"));
			// クラスidを取得
			String classid = getBulkExit.getClass_id();
			List<SessionEntity> list_sessionEntity = sessionRepository.SearchStudentInClass(classid);
			for (int i = 0; i < list_sessionEntity.size(); i++) {
				String session_id = null;
				session_id = list_sessionEntity.get(i).getSessionid();
				// 送信
				messagingTemplate.convertAndSendToUser(session_id, "/queue/bulkexit", response_message);
			}
		} catch (Exception e) {
			System.out.println("[Websocket]SocketManage_BulkExit　：　" + e);
		}
	}

	// 生と解答送信、解答状況表示
	@MessageMapping("/send_student_answer")
	public void SocketManage_StudentAnswer(GetAnswer getAnswer) throws Exception {
		try {
			// マルチスレッド処理中のCPUの負荷の抑え
			Thread.sleep(1000);

			// 送信データをJson形式に変換
			String response_message = json.ObjectToJSON(new SendAnswer(getAnswer.getStudentname(),getAnswer.getClass_no(),getAnswer.getAnswer()));
			//先生のセッションidの取得
			String teacher_sessionid = getAnswer.getTeacher_sessionid();
			// 送信
			messagingTemplate.convertAndSendToUser(teacher_sessionid, "/queue/student_answer", response_message);
		} catch (Exception e) {
			System.out.println("[Websocket]SocketManage_StudentAnswer　：　" + e);
		}
	}
}