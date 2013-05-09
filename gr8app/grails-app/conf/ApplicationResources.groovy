modules = {
    application {
        resource url: 'js/application.js'
    }

    'style' {
        resource url: 'css/main.css'
        resource url: 'css/mobile.css'
        resource url: 'less/trackapp.less', attrs: [rel: "stylesheet/less", type: 'css']
    }
}
