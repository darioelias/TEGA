(function() {
    'use strict';
    angular
        .module('proyectoApp')
        .factory('Especie', Especie);

    Especie.$inject = ['$resource'];

    function Especie ($resource) {
        var resourceUrl =  'api/especies/:id';

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
			'queryFiltered':{method: 'GET', url:'api/especies-filtered',isArray: true},
			'selector':{method: 'GET', url:'api/especies-selector',isArray: true},
			'selectorMin':{method: 'GET', url:'api/especies-selectorMin',isArray: true},
			'items':{method: 'GET', url:'api/especies-items',isArray: true},
			'deleteByIdIn': {method: 'PUT', url:'api/especies-deleteByIdIn', isArray: true}
        });
    }
})();
