$(function () {

    $(window).on('resize', function () {
        var ratio = 70*$(this).width()/1920;
        $('#logo').css('background-size',ratio+'%');
    }).trigger('resize');
});
