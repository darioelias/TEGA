(function() {
    'use strict';
    angular
        .module('proyectoApp')
        .factory('ConjuntoMuestras', ConjuntoMuestras);

    ConjuntoMuestras.$inject = ['$resource'];

    function ConjuntoMuestras ($resource) {
        var resourceUrl =  'api/conjuntos-muestras/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
			'queryFiltered':{method: 'GET', url:'api/conjuntos-muestras-filtered',isArray: true},
			'analisisGenotipo':{method: 'GET', url:'api/conjuntos-muestras-analisisGenotipo',isArray: true},
			'analisisSecuencias':{method: 'GET', url:'api/conjuntos-muestras-analisisSecuencias',isArray: true},
			'muestra':{method: 'GET', url:'api/conjuntos-muestras-muestra',isArray: true},
			'selector':{method: 'GET', url:'api/conjuntos-muestras-selector',isArray: true},
			'selectorMin':{method: 'GET', url:'api/conjuntos-muestras-selectorMin',isArray: true},
			'items':{method: 'GET', url:'api/conjuntos-muestras-items',isArray: true},
			'deleteByIdIn': {method: 'PUT', url:'api/conjuntos-muestras-deleteByIdIn', isArray: true},
			'atributos':{method: 'GET', url:'api/conjuntos-muestras-atributos',isArray: true}
        });
    }
})();
