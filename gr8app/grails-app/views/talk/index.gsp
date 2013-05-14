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
        counter.html(counter.html() - 1);
        counter.fitText(0.2);
        if (counter.html() <= 5) {
            counter.css('color', "yellow")
        }
        if (counter.html() == 0) {
            clearInterval(counterInterval);
            counter.css('color', "red")
        }
    }
</r:script>
</body>
</html>