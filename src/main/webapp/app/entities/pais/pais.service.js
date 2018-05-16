(function() {
    'use strict';
    angular
        .module('proyectoApp')
        .factory('Pais', Pais);

    Pais.$inject = ['$resource'];

    function Pais ($resource) {
        var resourceUrl =  'api/paises/:id';

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
			'queryFiltered':{method: 'GET', url:'api/paises-filtered',isArray: true},
			'selector':{method: 'GET', url:'api/paises-selector',isArray: true},
			'selectorMin':{method: 'GET', url:'api/paises-selectorMin',isArray: true},
			'items':{method: 'GET', url:'api/paises-items',isArray: true},
			'deleteByIdIn': {method: 'PUT', url:'api/paises-deleteByIdIn', isArray: true}
        });
    }
})();
