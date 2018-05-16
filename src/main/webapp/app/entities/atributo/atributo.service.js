(function() {
    'use strict';
    angular
        .module('proyectoApp')
        .factory('Atributo', Atributo);

    Atributo.$inject = ['$resource'];

    function Atributo ($resource) {
        var resourceUrl =  'api/atributos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {method: 'GET'},
            'update': { method:'PUT' },
			'queryFiltered':{method: 'GET', url:'api/atributos-filtered',isArray: true},
			'selector':{method: 'GET', url:'api/atributos-selector',isArray: true},
			'selectorMin':{method: 'GET', url:'api/atributos-selectorMin',isArray: true},
			'items':{method: 'GET', url:'api/atributos-items',isArray: true},
			'deleteByIdIn': {method: 'PUT', url:'api/atributos-deleteByIdIn', isArray: true},
			'entidad':{method: 'GET', url:'api/atributos-entidad',isArray: true}
        });
    }
})();
