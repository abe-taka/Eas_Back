package com.example.demo.getsockets;

//クライアントからデータを受け取るクラス(音声認識)
public class GetVoiceRecognition {
	//変数
    private String voicetext;

    //コンストラクタとオーバーロード
    public GetVoiceRecognition() {
    }

    public GetVoiceRecognition(String voicetext) {
        this.voicetext = voicetext;
    }

    //ゲッター、セッター
    public String getVoicetext() {
        return voicetext;
    }

    public void setVoicetext(String voicetext) {
        this.voicetext = voicetext;
    }
}