(function() {
    'use strict';
    angular
        .module('proyectoApp')
        .factory('Muestra', Muestra);

    Muestra.$inject = ['$resource', 'DateUtils'];

    function Muestra ($resource, DateUtils) {
        var resourceUrl =  'api/muestras/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
						data.fechaRecoleccion = DateUtils.convertLocalDateFromServer(data.fechaRecoleccion);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
					copy.fechaRecoleccion = DateUtils.convertLocalDateToServer(copy.fechaRecoleccion);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
					copy.fechaRecoleccion = DateUtils.convertLocalDateToServer(copy.fechaRecoleccion);
                    return angular.toJson(copy);
                }
            },
			'queryFiltered':{method: 'GET', url:'api/muestras-filtered',isArray: true},
			'selector':{method: 'GET', url:'api/muestras-selector',isArray: true},
			'selectorMin':{method: 'GET', url:'api/muestras-selectorMin',isArray: true},
			'items':{method: 'GET', url:'api/muestras-items',isArray: true},
			'proyecto':{method: 'GET', url:'api/muestras-proyecto',isArray: true},			
			'analisisGenotipo':{method: 'GET', url:'api/muestras-analisisGenotipo',isArray: true},
			'conjuntoMuestras':{method: 'GET', url:'api/muestras-conjuntoMuestras',isArray: true},
			'deleteByIdIn': {method: 'PUT', url:'api/muestras-deleteByIdIn', isArray: true},
			'atributos':{method: 'GET', url:'api/muestras-atributos',isArray: true}
        });
    }
})();
