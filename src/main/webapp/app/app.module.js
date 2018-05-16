(function() {
    'use strict';

    angular
        .module('proyectoApp', [
            'ngStorage', 
            'tmh.dynamicLocale',
            'pascalprecht.translate', 
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
			'color.picker',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar',
			'ngSanitize',
			'ui.select',
			'ngCsv',
			'openlayers-directive'
        ])
        .run(run);

    run.$inject = ['stateHandler', 'translationHandler'];

    function run(stateHandler, translationHandler) {
        stateHandler.initialize();
        translationHandler.initialize();
    }
})();
