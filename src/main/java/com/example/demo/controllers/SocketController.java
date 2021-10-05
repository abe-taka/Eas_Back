package com.example.demo.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

import com.example.demo.getsocket.GetVoiceRecognition;
import com.example.demo.sendsocket.SendVoiceRecognition;

//Websocketコントローラー
@Controller
public class SocketController {

	//socket通信(音声認識)
    @MessageMapping("/voice_recog")
    @SendTo("/topic/voice_recog")
    public SendVoiceRecognition VoiceRecog_SocketManage(GetVoiceRecognition get_voice) throws Exception {
    	//マルチスレッド処理中のCPUの負荷の抑え
        Thread.sleep(1000);
        //コンストラクタの呼び出し
        return new SendVoiceRecognition(HtmlUtils.htmlEscape(get_voice.getVoicetext()));
    }
    
    @GetMapping(value="/index")
    public String Index() {
    	return "index";
    }
}