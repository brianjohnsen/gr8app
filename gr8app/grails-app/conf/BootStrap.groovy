import gr8app.AgendaParserService

class BootStrap {

    AgendaParserService agendaParserService

    def init = { servletContext ->

        agendaParserService.importAgendaData()

    }
    def destroy = {
    }
}
