(function() {
    'use strict';
    angular
        .module('proyectoApp')
        .factory('Procedimiento', Procedimiento);

    Procedimiento.$inject = ['$resource'];

    function Procedimiento ($resource) {
        var resourceUrl =  'api/procedimientos/:id';

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
			'queryFiltered':{method: 'GET', url:'api/procedimientos-filtered',isArray: true},
			'selector':{method: 'GET', url:'api/procedimientos-selector',isArray: true},
			'selectorMin':{method: 'GET', url:'api/procedimientos-selectorMin',isArray: true},
			'items':{method: 'GET', url:'api/procedimientos-items',isArray: true},
			'deleteByIdIn': {method: 'PUT', url:'api/procedimientos-deleteByIdIn', isArray: true}
        });
    }
})();
