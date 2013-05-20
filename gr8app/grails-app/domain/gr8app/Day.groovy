package gr8app

class Day {

    Date start
    Date end


    static hasMany = [tracks: Track]


    static constraints = {
    }

    @Override
    String toString() {
        def date = start.format('yyyy-MM-dd')
        def s = start.format('HH:mm')
        def e = end.format('HH:mm')
        "$date $s to $e: tracks = $tracks"
    }
}
