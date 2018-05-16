(function () {
    'use strict';

    angular
        .module('proyectoApp')
        .factory('User', User);

    User.$inject = ['$resource'];

    function User ($resource) {
        var service = $resource('api/users/:login', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'save': { method:'POST' },
            'update': { method:'PUT' },
            'delete':{ method:'DELETE'},
			'queryFiltered':{method: 'GET', url:'api/users-filtered',isArray: true},
			'deleteByIdIn': {method: 'PUT', url:'api/users-deleteByIdIn', isArray: true},
			'items':{method: 'GET', url:'api/users-items',isArray: true},
        });

        return service;
    }
})();
