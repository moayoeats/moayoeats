var stompClient = null;
const host = 'http://' + window.location.host;
var sendButton = document.getElementById('send-button');
var chatInput = document.getElementById('chat-input');

sendButton.addEventListener('click', function () {
  sendChat();
});

chatInput.addEventListener('keypress', function (event) {
  if (event.key === 'Enter') {
    sendChat();
  }
});

function sendChat() {
  var messageContent = chatInput.value.trim();
  if (messageContent !== '') {
    if (messageContent.length > 100) {
      alert('채팅 내용이 너무 길어요! 최대 100자까지 입력 가능합니다.');
    } else {
      sendMessage(messageContent, username);
      chatInput.value = '';
    }
  }
}

function connect(postId, username) {
  fetchAndShowHistory(postId, function () {
    var socket = new SockJS('/wss');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
      console.log('Connected: ' + frame);
      stompClient.subscribe('/sub/chats/room/' + postId, function (message) {
        var receivedMessage = JSON.parse(message.body);
        showMessage(receivedMessage.sender, receivedMessage.content,
            receivedMessage.createdAt);
      });

      var chatMessage = {
        sender: username,
        content: "님이 입장하셨습니다."
      };
      stompClient.send("/pub/chats/join/" + postId, {},
          JSON.stringify(chatMessage));
    });
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
    content: message
  };
  stompClient.send("/pub/chats/message/" + postId, {},
      JSON.stringify(chatMessage));
}

function showMessage(username, message, createdAt) {
  var messageArea = document.getElementById('chat-messages');
  var messageElement = document.createElement('div');
  var contentElement = document.createElement('div');
  var timeElement = document.createElement('div');

  messageElement.classList.add('chat-message');
  contentElement.classList.add('message-content');
  timeElement.classList.add('message-time');

  if (username === window.username) {
    messageElement.classList.add('my-message');
  }

  var messageText = (message === "님이 입장하셨습니다.") ? username + message : username
      + " : " + message;

  contentElement.innerText = messageText;
  timeElement.innerText = createdAt;

  messageElement.appendChild(contentElement);
  messageElement.appendChild(timeElement);

  messageArea.appendChild(messageElement);
  messageArea.scrollTop = messageArea.scrollHeight;
}

function fetchAndShowHistory(postId, callback) {
  $.ajax({
    url: `/chats/history/${postId}`,
    type: 'GET',
    success: function (messages) {
      messages.forEach(function (message) {
        showMessage(message.sender, message.content, message.createdAt);
      });
      if (callback) {
        callback();
      }
    }
  });

}
