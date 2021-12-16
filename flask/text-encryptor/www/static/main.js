function flyImage() {
    var windowheight = $(window).height();
    var imgheight = $("img").height();
    var windowwidth = $(window).width();
    var imgwidth = $("img").width();

    var ispeedy = 0;
    var vy = 10;
    var ispeedx = 0;
    var vx = 10;

    function move() {
        if (ispeedx <= 0) {
            vx = 10;
        }

        if (ispeedy < 0) {
            vy = 10
        }

        if (ispeedx >= windowwidth - imgwidth) {
            ispeedx = windowwidth - imgwidth;
            vx = -10;
        }

        if (ispeedy >= windowheight - imgheight) {
            ispeedy = windowheight - imgheight;
            vy = -10
        }

        ispeedy += vy;
        ispeedx += vx;
        $("img").animate({ top: ispeedy, left: ispeedx }, 100);
    }

    var timer = null;
    timer = setInterval(function () {
        move()
    }, 100);
}
