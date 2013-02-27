package gr8app

class Day {

    Date start
    Date end


    static hasMany = [tracks: Track]


    static constraints = {
    }
}
