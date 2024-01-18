$(document).ready(function () {
  const auth = getToken();

  if (auth !== undefined && auth !== '') {
    $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
      jqXHR.setRequestHeader('Authorization', auth);
    });
    getMyPage();
  } else {
    window.location.href = host + '/login';
    return;
  }
});

function getToken() {

  let auth = Cookies.get('Authorization');
  console.log("@@@@@@@@@@@@@@@@@@@@@@@@@@ " + auth);

  if (auth === undefined) {
    return '';
  }

  if (auth.indexOf('Bearer') === -1 && auth !== '') {
    auth = 'Bearer ' + auth;
  }

  return auth;
}

// mypage ------------------------------------------------------
function updateNickname() {

}

function updatePw() {

}

function getMyPage() {
  $.ajax({
    type: "GET",
    url: `/api/v1/users/me`,
  })
  .done(function (res, status, xhr) {
    let nickname = res.data.nickname;
    let email = res.data.email;
    let score = res.data.score;
    let reviews = res.data.reviews.reviews;
    let pastOrderList = res.data.pastOrderList;

    $('#nickname').text(nickname);
    $('#email').text(email);
    $('#score').text(score);
    $('#reviews').text(reviews);
    $('#pastOrderList').text(pastOrderList);

  })
// ---------------------------------------------------------------

}
