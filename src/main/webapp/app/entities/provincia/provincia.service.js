(function() {
    'use strict';
    angular
        .module('proyectoApp')
        .factory('Provincia', Provincia);

    Provincia.$inject = ['$resource'];

    function Provincia ($resource) {
        var resourceUrl =  'api/provincias/:id';

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
			'queryFiltered':{method: 'GET', url:'api/provincias-filtered',isArray: true},
			'selector':{method: 'GET', url:'api/provincias-selector',isArray: true},
			'selectorMin':{method: 'GET', url:'api/provincias-selectorMin',isArray: true},
			'items':{method: 'GET', url:'api/provincias-items',isArray: true},
			'deleteByIdIn': {method: 'PUT', url:'api/provincias-deleteByIdIn', isArray: true}
        });
    }
})();
