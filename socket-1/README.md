## 課題4-B オリジナル通信プログラムの作成
ローカルで通信し、複数のクライアントでチャットすることができます。
通信はChatMessageクラスを介して行われ、
サーバーで受け取った時間を付記して全てのクライアントにブロードキャストされます。

## 動作ログ
```
(Server)
Server is running...
Client connected.
Received message: [11:37:36] Client1: Hello World!
Received message: [11:37:40] Client1: Test0
Client connected.
Received message: [11:38:08] Client2: Test1
Received message: [11:38:13] Client1: Test2
Client disconnected.
Client disconnected.
```
```
(Client1)
Local Chat: 5050 ユーザ: Client1 (exit で終了)
--------------------------------------------------
[11:37:31] Server: 新しいユーザが入室しました (接続中: 1人)
[11:37:36] Client1: Hello World!
[11:37:40] Client1: Test0
[11:37:50] Server: 新しいユーザが入室しました (接続中: 2人)
[11:38:08] Client2: Test1
[11:38:13] Client1: Test2
exit
```
```
(Client2)
Local Chat: 5050 ユーザ: Client2 (exit で終了)
--------------------------------------------------
[11:37:50] Server: 新しいユーザが入室しました (接続中: 2人)
[11:38:08] Client2: Test1
[11:38:13] Client1: Test2
[11:38:22] Server: ユーザが退室しました (接続中: 1人)
exit
```

