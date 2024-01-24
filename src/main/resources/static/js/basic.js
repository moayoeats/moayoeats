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

  if (xhr.status === 500 || xhr.message) {
    console.log(res.status + "- " + res.message);
    alert("요청을 처리하는 중 문제가 발생했습니다. 잠시 후에 다시 시도해주세요.");

    goLogin();
  } else if (xhr.status === 401 || res.status === 401) {
    console.log(res.status + "- " + res.message);
    console.log(xhr.status + ": " + xhr.responseText);
    alert("해당 페이지는 로그인 후 사용 가능합니다.");

    goLogin();
  } else {
    console.log(errorStatus + ": " + xhr.responseText);
    alert("해당 페이지는 로그인 후 사용 가능합니다.");
    goLogin();
  }

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
