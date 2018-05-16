(function() {
    'use strict';
    angular
        .module('proyectoApp')
        .factory('Region', Region);

    Region.$inject = ['$resource'];

    function Region ($resource) {
        var resourceUrl =  'api/regiones/:id';

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
			'queryFiltered':{method: 'GET', url:'api/regiones-filtered',isArray: true},
			'selector':{method: 'GET', url:'api/regiones-selector',isArray: true},
			'selectorMin':{method: 'GET', url:'api/regiones-selectorMin',isArray: true},
			'items':{method: 'GET', url:'api/regiones-items',isArray: true},
			'deleteByIdIn': {method: 'PUT', url:'api/regiones-deleteByIdIn', isArray: true}
        });
    }
})();
