modules = {
    application {
        resource url: 'js/application.js'
    }

    'style' {
        resource url: 'css/main.css'
        resource url: 'css/mobile.css'
        resource url: 'less/trackapp.less', attrs: [rel: "stylesheet/less", type: 'css']
    }

    'trackapp-style' {
        resource url: 'less/trackapp.less', attrs: [rel: "stylesheet/less", type: 'css']
    }

    'logo-resizer' {
        dependsOn 'jquery'
        resource url: 'js/logo-resizer.js'
    }
}
