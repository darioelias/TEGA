(function() {
    'use strict';
    angular
        .module('proyectoApp')
        .factory('Alelo', Alelo);

    Alelo.$inject = ['$resource'];

    function Alelo ($resource) {
        var resourceUrl =  'api/alelos/:id';

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
			'queryFiltered':{method: 'GET', url:'api/alelos-filtered',isArray: true},
			'analisisGenotipo':{method: 'GET', url:'api/alelos-analisis-genotipo', isArray: true},
			'muestra':{method: 'GET', url:'api/alelos-muestra', isArray: true},
			'updateCreateAlelos':{method: 'PUT', url:'api/alelos-masivo', isArray: true},
			'items':{method: 'GET', url:'api/alelos-items',isArray: true},
			'deleteByIdIn': {method: 'PUT', url:'api/alelos-deleteByIdIn', isArray: true}
        });
    }
})();
