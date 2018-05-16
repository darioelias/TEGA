(function () {
    'use strict';

    angular
        .module('proyectoApp')
        .factory('JhiLanguageService', JhiLanguageService);

    JhiLanguageService.$inject = ['$q', '$http', '$translate', 'LANGUAGES', 'ParametroTega'];

    function JhiLanguageService ($q, $http, $translate, LANGUAGES, ParametroTega) {

		var lenguajeCargado = false;

        var service = {
            getAll: getAll,
            getCurrent: getCurrent
        };

        return service;

        function getAll () {
            var deferred = $q.defer();
            deferred.resolve(LANGUAGES);
            return deferred.promise;
        }

        function getCurrent () {
            var deferred = $q.defer();

			if(lenguajeCargado){
            	var language = $translate.storage().get('NG_TRANSLATE_LANG_KEY');
           		deferred.resolve(language);				
			}else{
	        	ParametroTega.obtenerPublico({codigo: "LENGUAJE_DEFECTO"}, function(p){
					lenguajeCargado = true;
					deferred.resolve(p.valor);
				});
			}

            return deferred.promise;
        }

    }
})();
