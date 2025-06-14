    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void speakOut(){
        CharSequence text = busnum.getText();
        tts.setPitch((float)0.6); // 음성 톤 높이 지정
        tts.setSpeechRate((float)0.1); // 음성 속도 지정

        // 첫 번째 매개변수: 음성 출력을 할 텍스트
        // 두 번째 매개변수: 1. TextToSpeech.QUEUE_FLUSH - 진행중인 음성 출력을 끊고 이번 TTS의 음성 출력
        //                 2. TextToSpeech.QUEUE_ADD - 진행중인 음성 출력이 끝난 후에 이번 TTS의 음성 출력
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "id1");
    }

    @Override
    public void onDestroy() {
        if(tts!=null){ // 사용한 TTS객체 제거
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onInit(int status) { // OnInitListener를 통해서 TTS 초기화
        if(status == TextToSpeech.SUCCESS){
            int result = tts.setLanguage(Locale.KOREA); // TTS언어 한국어로 설정

            if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                Log.e("TTS", "This Language is not supported");
            }else{
                sound.setEnabled(true);
                speakOut();// onInit에 음성출력할 텍스트를 넣어줌
            }
        }else{
            Log.e("TTS", "Initialization Failed!");
        }
    }


// private texttospeech tts;를 통해 tts를 사용할 수 있게 환경을 만듦
// tts 언어를 한국어로 설정한 후 speakout 함수를 통해 텍스트를 음성으로 출력함
// speakout 함수는 busnum에서 텍스트를 입력받아 해당 텍스트를 음성으로 출력하게 하는 함수
