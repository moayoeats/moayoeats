$(document).ready(function() {
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
function updateNickname() {

}

function updatePw() {

}

function getToken() {

  let auth = Cookies.get('Authorization');

  if(auth === undefined) {
    return '';
  }

  if(auth.indexOf('Bearer') === -1 && auth !== ''){
    auth = 'Bearer ' + auth;
  }

  return auth;
}

function getMyPage() {
  $.ajax({
    type: "GET",
    url: `/api/v1/users/me`,
  })
  .done(function(res, status, xhr) {
    let nickname = res.data.nickname;
    let email = res.data.email;
    let score = res.data.score;
    let reviews = res.data.reviews.reviews;
    let pastOrderList = res.data.pastOrderList;

    $('#nickname').text(nickname);
    $('#email').text(email);
    $('#score').text(score);

    for(let review in reviews) {
      $('#my-review').append(`
        <div id="reviews">
           ${review} <a class="review-count">${reviews[review]}</a>
        </div>
      `);
      console.log(review + " " + reviews[review]);
    }

    pastOrderList.forEach(order => {
      let menuList = order.menus.map(menu => menu.menuname).join('<br>');
      $('#my-order-info').append(`
        <div>
          <p>가게 이름: ${order.store}</p>
          <p>수령인 이름: ${order.receiverName}</p>
          <p>메뉴
            <div>${menuList}</div>
          </p>
        </div>
      `);
    });

  })
}
