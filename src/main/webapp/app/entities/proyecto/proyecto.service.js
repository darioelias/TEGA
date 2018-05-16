(function() {
    'use strict';
    angular
        .module('proyectoApp')
        .factory('Proyecto', Proyecto);

    Proyecto.$inject = ['$resource', 'DateUtils'];

    function Proyecto ($resource, DateUtils) {
        var resourceUrl =  'api/proyectos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fecha = DateUtils.convertLocalDateFromServer(data.fecha);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fecha = DateUtils.convertLocalDateToServer(copy.fecha);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fecha = DateUtils.convertLocalDateToServer(copy.fecha);
                    return angular.toJson(copy);
                }
            },
			'queryFiltered':{method: 'GET', url:'api/proyectos-filtered',isArray: true},
			'selector':{method: 'GET', url:'api/proyectos-selector',isArray: true},
			'selectorMin':{method: 'GET', url:'api/proyectos-selectorMin',isArray: true},
			'items':{method: 'GET', url:'api/proyectos-items',isArray: true},
			'muestra':{method: 'GET', url:'api/proyectos-muestra',isArray: true},
			'extraccion':{method: 'GET', url:'api/proyectos-extraccion',isArray: true},
			'deleteByIdIn': {method: 'PUT', url:'api/proyectos-deleteByIdIn', isArray: true},
			'atributos':{method: 'GET', url:'api/proyectos-atributos',isArray: true}
        });
    }
})();
