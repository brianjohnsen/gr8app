package gr8app

import grails.converters.JSON

class TalkController {

    def index() {
        [slotId:"11"]
    }


    def slotRemainingTimeAJAX(long slotId) {
        println slotId
        Map slotInformation = [:]
        slotInformation.remaining = 2
        render slotInformation as JSON
    }

}
