// 안드로이드 6.0버전 이상인지 체크해서 퍼미션 체크
if(Build.VERSION.SDK_INT >= 23) {
    ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.INTERNET,
            Manifest.premission.RECORD_AUDIO}, PERMISSION);
        }
