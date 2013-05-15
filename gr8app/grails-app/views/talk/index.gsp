<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta name="layout" content="counter">

  <title>Talk Countdown</title>
</head>
<body>
<div class="countercontainer">
   <h1 id='counter'>12</h1>

</div>


<r:script>
    $("#counter").fitText(0.2);
    var counterInterval = window.setInterval(counter, 1000);
    var updateInterval = window.setInterval(updateRemainingTime, 5000);


    function counter() {
        var counter = $('#counter');
        counter.html(parseInt(counter.html()) - 1);
        counter.fitText(0.2);
        setCounterColor(counter)
        if (counter.html() == 0) {
            clearInterval(counterInterval);
            clearInterval(updateInterval);
        }
    }

    function setCounterColor(counter) {
        if ( 5 < counter.html() && counter.html() <= 10) {
            counter.css('color', "yellow")
        } else if ( 0 < counter.html() && counter.html() <= 5) {
            counter.css('color', "red")
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