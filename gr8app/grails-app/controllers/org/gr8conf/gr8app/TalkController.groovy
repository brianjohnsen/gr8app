package org.gr8conf.gr8app

import gr8app.AgendaParserService
import gr8app.Slot
import grails.converters.JSON

class TalkController {

    IntermissionService intermissionService

    def index() {
        [slotId:"11"]
    }


    def slotRemainingTimeAJAX(long slotId) {
        println slotId
        Map slotInformation = [:]
        slotInformation.remaining = 2
        render slotInformation as JSON
    }


    def intermission(String room) {
        def slotsByRoom = intermissionService.getUpcomingSlotsByRoom()//new Date("05/22/2013 18:50"))
        def mainRoomSlots = slotsByRoom."$room"
        def remainingRooms = slotsByRoom.findAll { entry -> entry.key != room && !entry.value?.any{it.offtrack}}
        def offTrack = slotsByRoom.findAll {entry -> entry.value?.any{it.offtrack}}
        def retval = [mainRoom: room, mainRoomSlots: mainRoomSlots, remainingRooms: remainingRooms, offTrack:offTrack]
        println retval
        return retval
    }

}
