const host = 'http://' + window.location.host;

$(document).ready(function () {
  const auth = getToken();

  if (auth !== undefined && auth !== '') {
    $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
      jqXHR.setRequestHeader('Authorization', auth);
    });
  } else {
    // alert("해당 페이지는 로그인 후 사용 가능합니다.");
    //
    // window.location.href = host + '/login';
    // return;
  }
});

function getToken() {
  let auth = Cookies.get('Authorization');

  if (auth === undefined) {
    return '';
  }

  if (auth.indexOf('Bearer') === -1 && auth !== '') {
    auth = 'Bearer ' + auth;
  }
  return auth;
}

function errorHandler(res, xhr, error, errorStatus) {
  console.log(res.status);
  alert("해당 페이지는 로그인 후 사용 가능합니다.");
  goLogin();

}

function goLogin() {
  setTimeout(function () {
    window.location.href = host + '/login';
  }, 0);
}

function goMain() {
  setTimeout(function () {
    window.location.href = host + '/';
  }, 0);
}
