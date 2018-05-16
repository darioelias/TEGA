(function() {
    'use strict';
    angular
        .module('proyectoApp')
        .factory('ParametroTega', ParametroTega);

    ParametroTega.$inject = ['$resource', 'DateUtils'];

    function ParametroTega ($resource, DateUtils) {
        var resourceUrl =  'api/parametros-tega/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {method: 'GET'},
            'update': { method:'PUT'},
			'queryFiltered':{method: 'GET', url:'api/parametros-tega-filtered',isArray: true},
			'selector':{method: 'GET', url:'api/parametros-tega-selector',isArray: true},
			'selectorMin':{method: 'GET', url:'api/parametros-tega-selectorMin',isArray: true},
			'items':{method: 'GET', url:'api/parametros-tega-items',isArray: true},
			'obtener':{
						method: 'GET', 	
						url:'api/parametros-tega-codigo',
						transformResponse: function (data) {
				            if (data) {
				                data = angular.fromJson(data);

								if(data.tipo == "NUMERICO"){
									data.valor = Number(data.valor);
								}else if(data.tipo == "CARACTER"){
									data.valor = data.valor.trim();
								}else if(data.tipo == "LOGICO"){
									data.valor = data.valor.trim() == "1";
								}else if(data.tipo == "FECHA"){
									data.valor = DateUtils.convertLocalDateFromServer(data.valor);
								}
				            }
				            return data;
               			}},
			'obtenerPublico':{
						method: 'GET', 	
						url:'api/parametros-tega-codigo-publico',
						transformResponse: function (data) {
				            if (data) {
				                data = angular.fromJson(data);

								if(data.tipo == "NUMERICO"){
									data.valor = Number(data.valor);
								}else if(data.tipo == "CARACTER"){
									data.valor = data.valor.trim();
								}else if(data.tipo == "LOGICO"){
									data.valor = data.valor.trim() == "1";
								}else if(data.tipo == "FECHA"){
									data.valor = DateUtils.convertLocalDateFromServer(data.valor);
								}
				            }
				            return data;
               			}},
			'deleteByIdIn': {method: 'PUT', url:'api/parametros-tega-deleteByIdIn', isArray: true}
        });
    }
})();
