$(function () {

    $(window).on('resize', function () {
        var ratio = $(this).width()/1920;
        $('#logo').css('background-size',70*ratio+'%');
        $('#main').css('font-size', 150*ratio+'%');
    }).trigger('resize');
});
