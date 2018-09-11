$(function () {

    $('div#postArea div.post-new div.textPostSmall').click(function () {
        $(this).hide();
        $('div#postArea div.post-new div.post-new-footer').show("slow");
        $('div#postArea div.post-new textarea#textPostFull').show().focus();
        $('div#postArea div.post-new div#postHidden').show("slow");
        $('div#postArea div.post-new div#postHidden').find('div#attachPost').hide();
    });
});

$(document).ready(function () {
    var track_load = 1; //total loaded record group(s)
    var loading = false; //to prevents multipal ajax loads
    //total record group(s)
    $(window).scroll(function () { //detect page scroll
        if ($(window).scrollTop() + $(window).height() == $(document).height())  //user scrolled to bottom of the page?
        {
            if (track_load <= total_groups && loading == false) //there's more data to load
            {
                loading = true; //prevent further ajax loading
                $('.load-more-animation').show(); //show loading image
                $.ajax({
                    type: "POST",
                    data: {
                        groupNumber: track_load
                    }, url: "/dashboard/loadMorePost"
                }).success(
                    function (data) {
                        $('div#postArea div#postAreaPadding').append(data);
                        $('.load-more-animation').hide(); //hide loading image once data is received
                        track_load++; //loaded group increment
                        loading = false;
                        $("img.post-user-info-hover").bind('mouseenter', function () {
                            var aPos = $(this).offset(), ctTop = 0,ctLeft =0;
                            ctTop = (aPos.top- $(window).scrollTop());
                            ctLeft = (aPos.left- $(window).scrollLeft()) -360;
                            chatUserDiv.css('top', ctTop + "px");
                            chatUserDiv.css('left', ctLeft + "px");
                            chatUserDiv.show();
                            chatUserDiv.find("img").attr("src",$(this).attr("src"));
                            chatUserDiv.find("strong").html($(this).attr("name"));
                            chatUserDiv.find("div.chat-user-info-team").html($(this).attr("team"));
                            chatUserDiv.find("div.chat-user-info-position").html($(this).attr("position"));
                            chatUserDiv.find("div.chat-user-info-phone").html($(this).attr("phone"));
                            chatUserDiv.find("div.chat-user-info-email").html($(this).attr("email"));
                            mousePostUserImg = true;
                        }).bind('mouseleave', function () {
                            mousePostUserImg = false;
                            var position = chatUserDiv.offset();
                            var bottom = position.top + chatUserDiv.outerHeight();
                            var right = position.left + chatUserDiv.outerWidth();
                            right+=50;
                            if( !(currentMousePos.x > position.left && currentMousePos.x < right
                                && currentMousePos.y > position.top && currentMousePos.y < bottom)){
                                chatUserDiv.hide();
                            }
                        });

                    }).error(function (thrownError) {
                        alert(thrownError); //alert with HTTP error
                        $('.load-more-animation').hide(); //hide loading image
                        loading = false;
                    });
            }
        }
    });
});