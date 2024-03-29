const host = 'http://' + window.location.host;
var status;
var type;
var page;
var ten;
var category;

$(document).ready(function () {
  page = 0;
  ten = 0;
  status = "ALL";
  type = "ALL"
  getData(page, ten);
});

function createPost() {
  const host = 'http://' + window.location.host;
  window.location.href = host + `/createpost`;
}

function getPage(button) {
  if (type === "ALL") {

    page = $(button).attr("index");
    ten = $(button).attr("ten");
    getData(page, ten);

  } else if (type === "CATEGORY") {

    page = $(button).attr("index");
    ten = $(button).attr("ten");
    getDataByCategory(category, page, ten)

  } else if (type === "SEARCH") {

    page = $(button).attr("index");
    ten = $(button).attr("ten");
    var keyword = $('#keyword').val();
    getDataByKeyword(keyword, page, ten);

  }
}

function getData(page, ten) {
  var page = page;
  var statusEnum = status;
  if (statusEnum === "ALL") {
    $.ajax({
      type: 'GET',
      url: `/api/v1/posts/status/${statusEnum}/page/${page}`,
      dataType: "json",
      contentType: 'application/json',
      data: {},
      success: function (response) {
        console.log('Success:', response);

        removeAllPosts();
        drawAllPosts(response.data);
        drawPages(ten);
      },
      error: function (error) {
        console.error('Error:', error);
      }
    });
  } else {
    $.ajax({
      type: 'GET',
      url: `/api/v1/posts/status/${statusEnum}/page/${page}`,
      dataType: "json",
      contentType: 'application/json',
      data: {},
      success: function (response) {
        console.log('Success:', response);

        removeAllPosts();
        drawAllPosts(response.data);
        drawPages(ten);
      },
      error: function (error) {
        console.error('Error:', error);
      }
    });
  }
}

function search() {
  var keyword = $('#keyword').val();
  type = "SEARCH"
  page = 0;
  ten = 0;
  getDataByKeyword(keyword, page, ten);
}

function getDataByKeyword(keyword, page, ten) {
  var keyword = keyword;
  var page = page;
  var ten = ten;
  var statusEnum = status;
  if (statusEnum === "ALL") {
    $.ajax({
      type: 'GET',
      url: `/api/v1/posts/search/${page}?keyword=${keyword}`,
      dataType: "json",
      contentType: 'application/json',
      data: {},
      success: function (response) {
        console.log('Success:', response.message);
        removeAllPosts();
        drawAllPosts(response.data);
        drawPages(ten);

      },
      error: function (error) {
        console.error('Error:', error);
      }
    });
  } else {
    $.ajax({
      type: 'GET',
      url: `/api/v1/posts/status/${statusEnum}/keyword/${keyword}/page/${page}`,
      dataType: "json",
      contentType: 'application/json',
      data: {},
      success: function (response) {
        console.log('Success:', response.message);
        removeAllPosts();
        drawAllPosts(response.data);
        drawPages(ten);

      },
      error: function (error) {
        console.error('Error:', error);
      }
    });
  }
}

function getByCategory(button) {
  type = "CATEGORY"
  category = $(button).attr("category");
  page = 0;
  ten = 0;
  getDataByCategory(category, page, ten);
}

function getDataByCategory(categoryEnum, page, ten) {
  var category = categoryEnum;
  var page = page;
  var ten = ten;
  var statusEnum = status;

  if (statusEnum === "ALL") {
    $.ajax({
      type: 'GET',
      url: `/api/v1/posts/category/page/${page}?category=${category}`,
      dataType: "json",
      contentType: 'application/json',
      data: {},
      success: function (response) {
        removeAllPosts();
        drawAllPosts(response.data);
        drawPages(ten);
      },
      error: function (error) {
        console.error('Error:', error);
      }
    });
  } else {
    $.ajax({
      type: 'GET',
      url: `/api/v1/posts/status/${statusEnum}/category/${category}/page/${page}`,
      dataType: "json",
      contentType: 'application/json',
      data: {},
      success: function (response) {
        removeAllPosts();
        drawAllPosts(response.data);
        drawPages(ten);
      },
      error: function (error) {
        console.error('Error:', error);
      }
    });
  }
}

function removeAllPosts() {
  const element = document.getElementById("posts");
  while (element.firstChild) {
    element.removeChild(element.firstChild);
  }
}

function drawAllPosts(data) {
    data.forEach((post) => drawPost(post));
}

function drawPost(post){
  // if(post.status==="OPEN"){
  //   drawOpenPost(post);
  // }else{
    drawOtherPost(post);
  // }
}

function drawOpenPost(post){
  $('#posts').append(`
              <div class="post" post = "${post.id}" onclick="sendData(this)">
                <div> <span style="font-weight: bold">작성자   </span> ${post.author} </div>
                <div> <span style="font-weight: bold">가게   </span>${post.store}</div>
                <div> <span style="font-weight: bold">모인 금액   </span>${post.sumPrice} / ${post.minPrice}</div>
                <div>${post.deadline}<span style="font-weight: bold"> 후에 마감됩니다!</span></div>
                <div><span style="font-weight: bold">${post.status}   </span>${getStatus(
      post.status)}</div>
              </div>
            `);
}

function drawOtherPost(post){
  $('#posts').append(`
              <div class="post" post = "${post.id}" onclick="sendData(this)">
                <div> <span style="font-weight: bold">작성자   </span> ${post.author} </div>
                <div> <span style="font-weight: bold">가게   </span>${post.store}</div>
                <div> <span style="font-weight: bold">모인 금액   </span>${post.sumPrice} / ${post.minPrice}</div>
                <div><span style="font-weight: bold">${post.status}   </span>${getStatus(
      post.status)}</div>
              </div>
            `);
}


function removeAllPages() {
  const element = document.getElementById("pages");
  while (element.firstChild) {
    element.removeChild(element.firstChild);
  }
}

function drawPages(ten) {
  removeAllPages();
  var index = ten * 10;
  $('#pages').append(`
    <div class="btn-group" role="group" aria-label="Basic outlined example">
      <button type="button" class="btn btn-outline-secondary" ten = ${ten} index = ${index} onclick="getPreviousPages(this)">이전</button>
      <button type="button" class="btn btn-outline-secondary" ten = ${ten} index = ${index
  + 0} onclick="getPage(this)">${index + 1}</button>
      <button type="button" class="btn btn-outline-secondary" ten = ${ten} index = ${index
  + 1} onclick="getPage(this)">${index + 2}</button>
      <button type="button" class="btn btn-outline-secondary" ten = ${ten} index = ${index
  + 2} onclick="getPage(this)">${index + 3}</button>
      <button type="button" class="btn btn-outline-secondary" ten = ${ten} index = ${index
  + 3} onclick="getPage(this)">${index + 4}</button>
      <button type="button" class="btn btn-outline-secondary" ten = ${ten} index = ${index
  + 4} onclick="getPage(this)">${index + 5}</button>
      <button type="button" class="btn btn-outline-secondary" ten = ${ten} index = ${index
  + 5} onclick="getPage(this)">${index + 6}</button>
      <button type="button" class="btn btn-outline-secondary" ten = ${ten} index = ${index
  + 6} onclick="getPage(this)">${index + 7}</button>
      <button type="button" class="btn btn-outline-secondary" ten = ${ten} index = ${index
  + 7} onclick="getPage(this)">${index + 8}</button>
      <button type="button" class="btn btn-outline-secondary" ten = ${ten} index = ${index
  + 8} onclick="getPage(this)">${index + 9}</button>
      <button type="button" class="btn btn-outline-secondary" ten = ${ten} index = ${index
  + 9} onclick="getPage(this)">${index + 10}</button>
      <button type="button" class="btn btn-outline-secondary" ten = ${ten} index = ${index} onclick="getNextPages(this)">다음</button>
    </div>
    `);
}

function getPreviousPages(button) {
  ten = +$(button).attr("ten");
  if (ten > 0) {
    ten = ten - 1;
  }
  drawPages(ten);
}

function getNextPages(button) {
  ten = +$(button).attr("ten");
  ten = ten + 1;
  drawPages(ten);
}

function getStatus(status) {
  if (status === "OPEN") {
    return "모집이 열려있습니다!"
  } else if (status === "CLOSED") {
    return "모집이 마감되었습니다!"
  } else if (status === "ORDERED") {
    return "주문 완료"
  } else if (status === "RECEIVED") {
    return "주문이 모두 수령된 게시글입니다!"
  }
}

function setStatus(button) {
  if ($(button).prop("checked")) {
    status = "OPEN";
  } else {
    status = "ALL";
  }
  if (type === "ALL") {
    getData(page, ten);
  } else if (type === "CATEGORY") {
    getDataByCategory(category, page, ten);
  } else if (type === "SEARCH") {
    var keyword = $('#keyword').val();
    getDataByKeyword(keyword, page, ten);
  }
}

function sendData(button) {
  var postId = $(button).attr("post");
  const host = 'http://' + window.location.host;
  window.location.href = host + `/post/${postId}`;
}
