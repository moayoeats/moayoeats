const host = 'http://' + window.location.host;

$(document).ready(function () {
  const auth = getToken();

  if (auth !== undefined && auth !== '') {
    $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
      jqXHR.setRequestHeader('Authorization', auth);
    });
  } else {
    window.location.href = host + '/login';
    return;
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

function updatePw() {

}
