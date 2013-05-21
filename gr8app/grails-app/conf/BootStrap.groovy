import gr8app.AgendaParserService
import grails.converters.JSON

class BootStrap {

    AgendaParserService agendaParserService

    def init = { servletContext ->
        def data // = JSON.parse(new File("data/gr8.json").text)
        agendaParserService.importAgendaData(data)

    }
    def destroy = {
    }
}
