(function() {
    'use strict';
    angular
        .module('proyectoApp')
        .factory('Locus', Locus);

    Locus.$inject = ['$resource'];

    function Locus ($resource) {
        var resourceUrl =  'api/loci/:id';

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
			'queryFiltered':{method: 'GET', url:'api/loci-filtered',isArray: true},
			'analisisGenotipo':{method: 'GET', url:'api/loci-analisisGenotipo',isArray: true},
			'selector':{method: 'GET', url:'api/loci-selector',isArray: true},
			'selectorMin':{method: 'GET', url:'api/loci-selectorMin',isArray: true},
			'selectorCompleto':{method: 'GET', url:'api/loci-selectorCompleto',isArray: true},
			'items':{method: 'GET', url:'api/loci-items',isArray: true},
			'deleteByIdIn': {method: 'PUT', url:'api/loci-deleteByIdIn', isArray: true},
			'atributos':{method: 'GET', url:'api/loci-atributos',isArray: true}
        });
    }
})();
