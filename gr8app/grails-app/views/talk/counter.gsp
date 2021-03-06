<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="counter">

    <title>Talk Countdown</title>
</head>

<body>
<div class="countercontainer">
    <h1 id='counter'>6</h1>

</div>


<r:script>
    $('body').css({backgroundImage: 'url("../images/track-background-black.png")'});
    updateRemainingTime()
    $("#counter").fitText(0.2);
    var updateInterval = window.setInterval(updateRemainingTime, 60*5*1000);
    var counterInterval = window.setInterval(counter, 60*1000);
    $(document).keypress(function(event) {
        if (event.which === 32) { window.location = '${g.createLink(action: 'intermission', params: [room: room])}'; }
    });

    function counter() {
        var counter = $('#counter');
        if (counter.html() == 0) {
            clearInterval(counterInterval);
            clearInterval(updateInterval);
        } else {
            counter.html(parseInt(counter.html()) - 1);
            counter.fitText(0.2);
            setCounterColor(counter);
        }
    }

    var col
    function setCounterColor(counter) {
        if ( 5 < counter.html() && counter.html() <= 10) {
            counter.css('color', "yellow");
        } else if ( 0 < counter.html() && counter.html() <= 5) {
            counter.css('color', "red");
        } else if ( 0 == counter.html()) {
            counter.css('color', "red");
            setInterval(function(){
                if(col == 1){
                     $('body').css({backgroundImage: 'url("../images/track-background-black.png")'});
                     col = 0;
                } else {
                    $('body').css({backgroundImage: 'url("../images/track-background.png")'});
                    col = 1;

                }
            }, 1000);
        }  else {
            counter.css('color', "white")
        }
    }

    function updateRemainingTime() {
         var counter = $('#counter');
         $.getJSON("${createLink(action: 'slotRemainingTimeAJAX')}",{slotId:'${slotId}'}, function(data) {
            if(Math.pow(counter.html() - data.remaining , 2) > 1) {
                counter.html(data.remaining)
                setCounterColor(counter)
            }
        });
    }
</r:script>
</body>
</html>