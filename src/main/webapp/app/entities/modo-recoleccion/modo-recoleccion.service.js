(function() {
    'use strict';
    angular
        .module('proyectoApp')
        .factory('ModoRecoleccion', ModoRecoleccion);

    ModoRecoleccion.$inject = ['$resource'];

    function ModoRecoleccion ($resource) {
        var resourceUrl =  'api/modos-recoleccion/:id';

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
			'queryFiltered':{method: 'GET', url:'api/modos-recoleccion-filtered',isArray: true},
			'selector':{method: 'GET', url:'api/modos-recoleccion-selector',isArray: true},
			'selectorMin':{method: 'GET', url:'api/modos-recoleccion-selectorMin',isArray: true},
			'items':{method: 'GET', url:'api/modos-recoleccion-items',isArray: true},
			'deleteByIdIn': {method: 'PUT', url:'api/modos-recoleccion-deleteByIdIn', isArray: true}
        });
    }
})();
