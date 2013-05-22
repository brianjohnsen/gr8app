<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="tracks"/>
    <title>Intermission page</title></head>

<body>
    <g:if test="${mainRoomSlots == null && (remainingRooms + offTrack).size() == 0}">
        <div id="equal-width-wrapper">
            <div>
                <h1>NO EVENTS TODAY</h1>
            </div>
        </div>
    </g:if>
<g:if test="${(mainRoomSlots || remainingRooms)}">
<div id="equal-width-wrapper">
    <div>
        <h1>${mainRoom}</h1>
        <g:if test="${!mainRoomSlots}">No Events</g:if>
        <g:else>
        <dl>
        <g:each var="slot" in="${mainRoomSlots}">
            <dt>${String.format('%tR', slot.start) + " - " + String.format('%tR', slot.end)}<br/>
            <h2>${slot.name}</h2></dt><dd>${slot.speakers?.join(", ")}</dd>
        </g:each>
        </dl>
        </g:else>
    </div>
    <g:each var="room" in="${remainingRooms}">
    <div id="other-room">
        <h1>${room.key}</h1>
        <dl>
        <g:each var="slot" in="${room.value}">
            <dt>${String.format('%tR', slot.start) + " - " + String.format('%tR', slot.end)}<br/>
            <h2>${slot.name }</h2></dt><dd>${slot.speakers?.join(", ")}</dd>
        </g:each>
        </dl>
    </div>
    </g:each>
</div>
</g:if>
    <div id="equal-width-wrapper">
        <g:each var="room" in="${offTrack}">
            <div id="other-room">
                <h1>${room.key}</h1>
                <dl>
                    <g:each var="slot" in="${room.value}">
                        <dt>${String.format('%tR', slot.start) + " - " + String.format('%tR', slot.end)}<br/>
                        <h2>${slot.name }</h2></dt><dd>${slot.speakers?.join(", ")}</dd>
                    </g:each>
                </dl>
            </div>
        </g:each>
    </div>
<r:script>
    $(document).keypress(function(event) {
        if (event.which === 32) { window.location = '${g.createLink(action:'counter', params:[slotId:upcomingSlotId, room: mainRoom])}'; }
    });
</r:script>
</body>
</html>
