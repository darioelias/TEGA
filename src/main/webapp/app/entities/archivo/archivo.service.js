(function() {
    'use strict';
    angular
        .module('proyectoApp')
        .factory('Archivo', Archivo);

    Archivo.$inject = ['$resource'];

    function Archivo ($resource) {
        var resourceUrl =  'api/archivos/:id';

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
			'analisisGenotipo':{method: 'GET', url:'api/archivos-analisisGenotipo',isArray: true},
			'muestra':{method: 'GET', url:'api/archivos-muestra',isArray: true},
			'proyecto':{method: 'GET', url:'api/archivos-proyecto',isArray: true},
			'procedimiento':{method: 'GET', url:'api/archivos-procedimiento',isArray: true},
			'ejecucion':{method: 'GET', url:'api/archivos-ejecucion',isArray: true}
        });
    }
})();
