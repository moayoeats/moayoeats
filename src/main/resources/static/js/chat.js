var stompClient = null;

document.getElementById('send-button').addEventListener('click', function() {
  sendMessageFromInput();
});

document.addEventListener('keypress', function(event) {
  if(event.key === 'Enter') {
    sendMessageFromInput();
  }
});

function sendMessageFromInput() {
  var messageContent = document.getElementById('chat-input').value;
  if (messageContent.trim() !== '') {
    sendMessage(messageContent, username);
    document.getElementById('chat-input').value = '';
  }
}

function connect(postId, username) {
  var socket = new SockJS('/ws');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/sub/chats/room/' + postId, function (message) {
      showMessage(JSON.parse(message.body).content);
    });
    var chatMessage = {
      sender: username,
      content: username + "님이 입장하셨습니다."
    };
    stompClient.send("/pub/chats/join/" + postId, {}, JSON.stringify(chatMessage));
  });
}

function disconnect() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  console.log("Disconnected");
}

function sendMessage(message) {
  var chatMessage = {
    sender: username,
    content:  username + " : " + message
  };
  stompClient.send("/pub/chats/message/" + postId, {}, JSON.stringify(chatMessage));
}

function showMessage(message) {
  console.log('Received message:', message);  // 디버깅 코드 추가
  var messageArea = document.getElementById('chat-messages');
  var messageElement = document.createElement('div');
  messageElement.classList.add('chat-message');
  messageElement.innerText = message;
  messageArea.appendChild(messageElement);
  messageArea.scrollTop = messageArea.scrollHeight;
}
