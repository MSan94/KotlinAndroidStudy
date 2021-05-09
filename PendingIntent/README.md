2021.05.09 PendingIntent

# PendingIntent
- Intent를 가지고 있는 클래스로, 다른 애플리케이션의 권한을 허가하여 가지고 있는 Intent를 본인 앱의 프로세스에서 실행
- 인텐트를 전송하고자 하는 '송신자'가 인텐트를 하나 생성한 후, 별 도의 컴포넌트에게 ' 이 인텐트를 나중에 나 대신 보내줘'라고 전달할 떄 사용되는 클래스

## PendingIntent 생성
- Activity를 시작하는 Intent -> PendingIntent.getActivity()
- Service를 시작하는 Intent -> PendingIntent.getService()
- BroadcastReceiver를 시작하는 Intent -> PendingIntent.getBroadcast()

## PendingIntent의 Flag
- FLAG_CANCEL_CURRENT : 이전에 생성한 PendingIntent는 취소하고, 새롭게 하나를 만든다.
- FLAG_NO_CREATE : 현재 생성된 PendingIntent를 반환
- FLAG_ONE_SHOT : 해당 플래그를 통해 생성된 PendingIntent는 단 한번만 사용 가능
- FLAG_UPDATE_CURRENT : 만약 이미 생성된 PendingIntent가 존재하면, 해당 Intent의 내용을 변경
