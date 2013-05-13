<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta name="layout" content="counter">

  <title>Talk Countdown</title>
</head>
<body>
<div class="countercontainer">
   <h1 id='counter'>10</h1>

</div>


<r:script>
    $("#counter").fitText(0.2);
    var counterInterval = window.setInterval(func, 1000);


    function func() {
        var counter = $('#counter');
        console.debug(counter.html());
        counter.html(parseInt(counter.html()) - 1);
        counter.fitText(0.2);
        if ( 0 < counter.html() && counter.html() <= 5) {
            counter.css('color', "yellow")
        } else if (counter.html() == 0) {
            clearInterval(counterInterval);
            counter.css('color', "red")
        } else {
            counter.css('color', "white")
        }
    }
</r:script>
</body>
</html>