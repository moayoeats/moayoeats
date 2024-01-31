function connectSse() {
  const eventSource = new EventSource(`/api/v1/notification-push/subscribe`);

  eventSource.addEventListener("apply", function (event) {
    console.log(event.data);
    const data = event.data;
    (async () => {
      //브라우저 알림
      const showNotification = () => {
        const notification = new Notification('알림', {
          body: data
        });

        setTimeout(() => {
          notification.close();
        }, 60 * 1000 * 60);

        notification.addEventListener('click', () => {
          notification.close();
          window.location.href = '/post/' + postId;
        });
      }

      let granted = false;
      if (Notification.permission === 'granted') {
        granted = true;
      } else if (Notification.permission !== 'denied') {
        let permission = await Notification.requestPermission();
        granted = permission === 'granted';
      }

      if (granted) {
        showNotification();
      }
    })();
  })

  eventSource.addEventListener("approved", function (event) {
    console.log(event.data);
    const data = event.data;
    (async () => {
      //브라우저 알림
      const showNotification = () => {
        const notification = new Notification('알림', {
          body: data
        });

        setTimeout(() => {
          notification.close();
        }, 60 * 1000 * 60);

        notification.addEventListener('click', () => {
          notification.close();
          window.location.href = '/post/' + postId;
        });
      }

      let granted = false;
      if (Notification.permission === 'granted') {
        granted = true;
      } else if (Notification.permission !== 'denied') {
        let permission = await Notification.requestPermission();
        granted = permission === 'granted';
      }

      if (granted) {
        showNotification();
      }
    })();
  })

  eventSource.addEventListener("rejected", function (event) {
    console.log(event.data);
    const data = event.data;
    (async () => {
      //브라우저 알림
      const showNotification = () => {
        const notification = new Notification('알림', {
          body: data
        });

        setTimeout(() => {
          notification.close();
        }, 60 * 1000 * 60);

        notification.addEventListener('click', () => {
          notification.close();
          window.location.href = '/post/' + postId;
        });
      }

      let granted = false;
      if (Notification.permission === 'granted') {
        granted = true;
      } else if (Notification.permission !== 'denied') {
        let permission = await Notification.requestPermission();
        granted = permission === 'granted';
      }

      if (granted) {
        showNotification();
      }
    })();
  })

  eventSource.addEventListener("collected", function (event) {
    console.log(event.data);
    const data = event.data;
    (async () => {
      //브라우저 알림
      const showNotification = () => {
        const notification = new Notification('알림', {
          body: data
        });

        setTimeout(() => {
          notification.close();
        }, 60 * 1000 * 60);

        notification.addEventListener('click', () => {
          notification.close();
          window.location.href = '/post/' + postId;
        });
      }

      let granted = false;
      if (Notification.permission === 'granted') {
        granted = true;
      } else if (Notification.permission !== 'denied') {
        let permission = await Notification.requestPermission();
        granted = permission === 'granted';
      }

      if (granted) {
        showNotification();
      }
    })();
  })

  eventSource.addEventListener("not-collected", function (event) {
    console.log(event.data);
    const data = event.data;
    (async () => {
      //브라우저 알림
      const showNotification = () => {
        const notification = new Notification('알림', {
          body: data
        });

        setTimeout(() => {
          notification.close();
        }, 60 * 1000 * 60);

        notification.addEventListener('click', () => {
          notification.close();
          window.location.href = '/post/' + postId;
        });
      }

      let granted = false;
      if (Notification.permission === 'granted') {
        granted = true;
      } else if (Notification.permission !== 'denied') {
        let permission = await Notification.requestPermission();
        granted = permission === 'granted';
      }

      if (granted) {
        showNotification();
      }
    })();
  })

  eventSource.addEventListener("closed", function (event) {
    console.log(event.data);
    const data = event.data;
    (async () => {
      //브라우저 알림
      const showNotification = () => {
        const notification = new Notification('알림', {
          body: data
        });

        setTimeout(() => {
          notification.close();
        }, 60 * 1000 * 60);

        notification.addEventListener('click', () => {
          notification.close();
          window.location.href = '/post/' + postId;
        });
      }

      let granted = false;
      if (Notification.permission === 'granted') {
        granted = true;
      } else if (Notification.permission !== 'denied') {
        let permission = await Notification.requestPermission();
        granted = permission === 'granted';
      }

      if (granted) {
        showNotification();
      }
    })();
  })

  eventSource.addEventListener("canceled", function (event) {
    console.log(event.data);
    const data = event.data;
    (async () => {
      //브라우저 알림
      const showNotification = () => {
        const notification = new Notification('알림', {
          body: data
        });

        setTimeout(() => {
          notification.close();
        }, 60 * 1000 * 60);

        notification.addEventListener('click', () => {
          notification.close();
          window.location.href = '/post/' + postId;
        });
      }

      let granted = false;
      if (Notification.permission === 'granted') {
        granted = true;
      } else if (Notification.permission !== 'denied') {
        let permission = await Notification.requestPermission();
        granted = permission === 'granted';
      }

      if (granted) {
        showNotification();
      }
    })();
  })

  eventSource.addEventListener("completed", function (event) {
    console.log(event.data);
    const data = event.data;
    (async () => {
      //브라우저 알림
      const showNotification = () => {
        const notification = new Notification('알림', {
          body: data
        });

        setTimeout(() => {
          notification.close();
        }, 60 * 1000 * 60);

        notification.addEventListener('click', () => {
          notification.close();
          window.location.href = '/post/' + postId;
        });
      }

      let granted = false;
      if (Notification.permission === 'granted') {
        granted = true;
      } else if (Notification.permission !== 'denied') {
        let permission = await Notification.requestPermission();
        granted = permission === 'granted';
      }

      if (granted) {
        showNotification();
      }
    })();
  })
////////////////////////////////////////////////////////////////////////////////
  eventSource.addEventListener("invitedClub", function (event) {
    console.log(event.data);
    const data = event.data;
    (async () => {
      //브라우저 알림
      const showNotification = () => {
        const notification = new Notification('알림', {
          body: data
        });

        setTimeout(() => {
          notification.close();
        }, 10 * 1000 * 60);
      }

      let granted = false;
      if (Notification.permission === 'granted') {
        granted = true;
      } else if (Notification.permission !== 'denied') {
        let permission = await Notification.requestPermission();
        granted = permission === 'granted';
      }

      if (granted) {
        showNotification();
      }
    })();
  })

  eventSource.addEventListener("message", function (event) {
    console.log(event.data);
    const data = event.data;
    (async () => {
      //브라우저 알림
      const showNotification = () => {
        const notification = new Notification('알림', {
          body: data
        });

        setTimeout(() => {
          notification.close();
        }, 60 * 1000 * 60);

        notification.addEventListener('click', () => {
          notification.close();
          window.location.href = '/api/chat/member/' + username
              + '/chatRoomListPage';
        });
      }

      let granted = false;
      if (Notification.permission === 'granted') {
        granted = true;
      } else if (Notification.permission !== 'denied') {
        let permission = await Notification.requestPermission();
        granted = permission === 'granted';
      }

      if (granted) {
        showNotification();
      }
    })();
  })
}