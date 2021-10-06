package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

import com.example.demo.components.Session_manage;
import com.example.demo.forms.LoginForm;
import com.example.demo.getsockets.GetVoiceRecognition;
import com.example.demo.sendsockets.SendVoiceRecognition;

//Websocketコントローラー
@Controller
public class SocketController {
	@Autowired
	Session_manage session_manage;

	// セッション用変数
	private String session_name = null;

	// socket通信(音声認識)
	@MessageMapping("/voice_recog")
	@SendTo("/topic/voice_recog")
	public SendVoiceRecognition VoiceRecog_SocketManage(GetVoiceRecognition get_voice) throws Exception {
		// マルチスレッド処理中のCPUの負荷の抑え
		Thread.sleep(1000);
		// コンストラクタの呼び出し
		return new SendVoiceRecognition(HtmlUtils.htmlEscape(get_voice.getVoicetext()));
	}

	// 音声認識ページ
	@GetMapping(value = "/index")
	public String Index() {
		// セッションがあるかをチェック
		if (!session_manage.Get_SessionName(session_name)) {
			return "redirect:login";
		} else {
			return "index";
		}
	}
}