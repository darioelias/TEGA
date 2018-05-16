(function() {
    'use strict';
    angular
        .module('proyectoApp')
        .factory('Profesional', Profesional);

    Profesional.$inject = ['$resource'];

    function Profesional ($resource) {
        var resourceUrl =  'api/profesionales/:id';

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
			'queryFiltered':{method: 'GET', url:'api/profesionales-filtered',isArray: true},
			'selector':{method: 'GET', url:'api/profesionales-selector',isArray: true},
			'selectorMin':{method: 'GET', url:'api/profesionales-selectorMin',isArray: true},
			'items':{method: 'GET', url:'api/profesionales-items',isArray: true},
			'deleteByIdIn': {method: 'PUT', url:'api/profesionales-deleteByIdIn', isArray: true}
        });
    }
})();
