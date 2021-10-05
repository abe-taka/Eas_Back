package com.example.demo.sendsocket;

//クライアントに送信するデータをセットするクラス(音声認識)
public class SendVoiceRecognition {
	//変数
    private String voicetext;

    //コンストラクタとオーバーロード
    public SendVoiceRecognition() {
    }

    public SendVoiceRecognition(String voicetext) {
        this.voicetext = voicetext;
    }

    //ゲッター
    public String getVoicetext() {
        return voicetext;
    }
}