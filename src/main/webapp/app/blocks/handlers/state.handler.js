(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .factory('stateHandler', stateHandler);

    stateHandler.$inject = ['$rootScope', '$state', '$sessionStorage', '$translate', 
							'JhiLanguageService', 'translationHandler', '$window',
        					'Auth', 'Principal', 'VERSION', 'ParametroTega','$q'];

    function stateHandler($rootScope, $state, $sessionStorage, $translate, 
						  JhiLanguageService, translationHandler, $window,
        				  Auth, Principal, VERSION, ParametroTega,$q) {

		var nombrePlataforma = null;

        return {
            initialize: initialize
        };

        function initialize() {
            $rootScope.VERSION = VERSION;
			//$rootScope.NOMBRE_PLATAFORMA = null;

			angular.element(document).ready(function(){
				ParametroTega.obtenerPublico({codigo: "URL_LOGO_ICO"}, function(p){
					var link = document.querySelector("link[rel*='icon']") || document.createElement('link');
					link.type = 'image/x-icon';
					link.rel = 'shortcut icon';
					link.href = p.valor;
					document.getElementsByTagName('head')[0].appendChild(link);
				});
			});

            var stateChangeStart = $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams, fromState) {
                $rootScope.toState = toState;
                $rootScope.toStateParams = toStateParams;
                $rootScope.fromState = fromState;

                // Redirect to a state with an external URL (http://stackoverflow.com/a/30221248/1098564)
                if (toState.external) {
                    event.preventDefault();
                    $window.open(toState.url, '_self');
                }

                if (Principal.isIdentityResolved()) {
                    Auth.authorize();
                }

                // Update the language
                JhiLanguageService.getCurrent().then(function (language) {
                    $translate.use(language);
                });
                
            });

            var stateChangeSuccess = $rootScope.$on('$stateChangeSuccess',  function(event, toState, toParams, fromState, fromParams) {
//                var titleKey = 'global.title' ;

//                // Set the page title key to the one configured in state or use default one
//                if (toState.data.pageTitle) {
//                    titleKey = toState.data.pageTitle;
//                }
//                translationHandler.updateTitle(titleKey);
				
				if(nombrePlataforma  == null){
					ParametroTega.obtenerPublico({codigo: "NOMBRE_PLATAFORMA"}, function(p){
								nombrePlataforma = p.valor;
								$window.document.title = nombrePlataforma;
							});
				}else{
					$window.document.title = nombrePlataforma ;
				}
				
            });


			

            $rootScope.$on('$destroy', function () {
                if(angular.isDefined(stateChangeStart) && stateChangeStart !== null){
                    stateChangeStart();
                }
                if(angular.isDefined(stateChangeSuccess) && stateChangeSuccess !== null){
                    stateChangeSuccess();
                }
            });
        }
    }
})();
