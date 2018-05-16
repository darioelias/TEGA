(function() {
    'use strict';
    angular
        .module('proyectoApp')
        .factory('ParametroProcedimiento', ParametroProcedimiento);

    ParametroProcedimiento.$inject = ['$resource'];

    function ParametroProcedimiento ($resource) {
        var resourceUrl =  'api/parametros-procedimiento/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {method: 'GET'},
            'update': { method:'PUT' },
			'queryFiltered':{method: 'GET', url:'api/parametros-procedimiento-filtered',isArray: true},
			'selector':{method: 'GET', url:'api/parametros-procedimiento-selector',isArray: true},
			'selectorMin':{method: 'GET', url:'api/parametros-procedimiento-selectorMin',isArray: true},
			'items':{method: 'GET', url:'api/parametros-procedimiento-items',isArray: true},
			'deleteByIdIn': {method: 'PUT', url:'api/parametros-procedimiento-deleteByIdIn', isArray: true},
			'procedimiento':{method: 'GET', url:'api/parametros-procedimiento-procedimiento-ejec',isArray: true}
        });
    }
})();
