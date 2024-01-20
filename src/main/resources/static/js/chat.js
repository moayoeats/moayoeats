var stompClient = null;

document.getElementById('send-button').addEventListener('click', function() {
  var button = this;
  var postId = button.getAttribute('data-postid');
  var username = button.getAttribute('data-username');

  sendMessage(postId, username);
});

function connect(postId, username) {
  var socket = new SockJS('/ws'); // WebSocket 연결 주소
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);

    // 채팅방 구독 설정
    stompClient.subscribe('/sub/chats/room/' + postId, function (message) {
      showMessage(JSON.parse(message.body).content);
    });

    // 채팅방 입장 메시지 보내기
    stompClient.send("/pub/chats/join", {},
        JSON.stringify({postId: postId, sender: username}));
  });
}

function disconnect() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  console.log("Disconnected");
}

function sendMessage(postId, username) {
  var messageContent = document.getElementById('chat-input').value;
  if (messageContent.trim() !== '') {
    var chatMessage = {
      postId: postId,
      content: messageContent,
      sender: username
    };
    stompClient.send("/pub/chats/message", {}, JSON.stringify(chatMessage));
    document.getElementById('chat-input').value = '';
  }
}

function showMessage(message) {
  var messageArea = document.getElementById('chat-messages');
  var messageElement = document.createElement('div');
  messageElement.classList.add('chat-message');
  messageElement.innerText = message;
  messageArea.appendChild(messageElement);
  messageArea.scrollTop = messageArea.scrollHeight; // Scroll to the bottom
}
