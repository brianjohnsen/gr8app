<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="tracks"/>
    <title>Select room</title>
</head>

<body>
<div id="equal-width-wrapper">
    <div>
        <g:each in="${rooms}" var="room">
            <g:link style="text-decoration: none;" controller="talk" action="intermission" params="[room: room]"><h1>${room}</h1></g:link>
        </g:each>
    </div>
</div>
</body>
</html>
