var stompClient = null;

document.getElementById('send-button').addEventListener('click', function () {
  sendMessageFromInput();
});

document.addEventListener('keypress', function (event) {
  if (event.key === 'Enter') {
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
  fetchAndShowHistory(postId, function () {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
      console.log('Connected: ' + frame);
      stompClient.subscribe('/sub/chats/room/' + postId, function (message) {
        var receivedMessage = JSON.parse(message.body);
        showMessage(receivedMessage.sender, receivedMessage.content);
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

function showMessage(username, message) {
  var messageArea = document.getElementById('chat-messages');
  var messageElement = document.createElement('div');
  messageElement.classList.add('chat-message');

  if (username === window.username) {
    messageElement.classList.add('my-message');
  }

  if (message === "님이 입장하셨습니다.") {
    messageElement.innerText = username + message;  // 입장 메시지
  } else {
    messageElement.innerText = username + " : " + message;  // 일반 메시지
  }

  messageArea.appendChild(messageElement);
  messageArea.scrollTop = messageArea.scrollHeight;
}

function fetchAndShowHistory(postId, callback) {
  $.ajax({
    url: `/chats/history/${postId}`,
    type: 'GET',
    success: function (messages) {
      messages.forEach(function (message) {
        showMessage(message.sender, message.content);
      });
      if (callback) {
        callback();
      }
    }
  });
}
