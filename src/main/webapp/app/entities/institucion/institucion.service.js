(function() {
    'use strict';
    angular
        .module('proyectoApp')
        .factory('Institucion', Institucion);

    Institucion.$inject = ['$resource'];

    function Institucion ($resource) {
        var resourceUrl =  'api/instituciones/:id';

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
			'queryFiltered':{method: 'GET', url:'api/instituciones-filtered',isArray: true},
			'selector':{method: 'GET', url:'api/instituciones-selector',isArray: true},
			'selectorMin':{method: 'GET', url:'api/instituciones-selectorMin',isArray: true},
			'items':{method: 'GET', url:'api/instituciones-items',isArray: true},
			'deleteByIdIn': {method: 'PUT', url:'api/instituciones-deleteByIdIn', isArray: true}
        });
    }
})();
