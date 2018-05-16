(function() {
    'use strict';
    angular
        .module('proyectoApp')
        .factory('Localidad', Localidad);

    Localidad.$inject = ['$resource'];

    function Localidad ($resource) {
        var resourceUrl =  'api/localidades/:id';

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
			'queryFiltered':{method: 'GET', url:'api/localidades-filtered',isArray: true},
			'selector':{method: 'GET', url:'api/localidades-selector',isArray: true},
			'selectorMin':{method: 'GET', url:'api/localidades-selectorMin',isArray: true},
			'items':{method: 'GET', url:'api/localidades-items',isArray: true},
			'deleteByIdIn': {method: 'PUT', url:'api/localidades-deleteByIdIn', isArray: true}
        });
    }
})();
