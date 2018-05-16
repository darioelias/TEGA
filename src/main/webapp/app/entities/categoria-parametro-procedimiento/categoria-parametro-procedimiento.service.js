(function() {
    'use strict';
    angular
        .module('proyectoApp')
        .factory('CategoriaParametroProcedimiento', CategoriaParametroProcedimiento);

    CategoriaParametroProcedimiento.$inject = ['$resource'];

    function CategoriaParametroProcedimiento ($resource) {
        var resourceUrl =  'api/categorias-parametro-procedimiento/:id';

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
			'queryFiltered':{method: 'GET', url:'api/categorias-parametro-procedimiento-filtered',isArray: true},
			'selector':{method: 'GET', url:'api/categorias-parametro-procedimiento-selector',isArray: true},
			'selectorMin':{method: 'GET', url:'api/categorias-parametro-procedimiento-selectorMin',isArray: true},
			'items':{method: 'GET', url:'api/categorias-parametro-procedimiento-items',isArray: true},
			'deleteByIdIn': {method: 'PUT', url:'api/categorias-parametro-procedimiento-deleteByIdIn', isArray: true}
        });
    }
})();
