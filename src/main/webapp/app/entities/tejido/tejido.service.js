(function() {
    'use strict';
    angular
        .module('proyectoApp')
        .factory('Tejido', Tejido);

    Tejido.$inject = ['$resource'];

    function Tejido ($resource) {
        var resourceUrl =  'api/tejidos/:id';

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
			'queryFiltered':{method: 'GET', url:'api/tejidos-filtered',isArray: true},
			'selector':{method: 'GET', url:'api/tejidos-selector',isArray: true},
			'selectorMin':{method: 'GET', url:'api/tejidos-selectorMin',isArray: true},
			'items':{method: 'GET', url:'api/tejidos-items',isArray: true},
			'deleteByIdIn': {method: 'PUT', url:'api/tejidos-deleteByIdIn', isArray: true}
        });
    }
})();
