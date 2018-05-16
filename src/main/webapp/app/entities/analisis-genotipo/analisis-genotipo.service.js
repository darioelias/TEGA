(function() {
    'use strict';
    angular
        .module('proyectoApp')
        .factory('AnalisisGenotipo', AnalisisGenotipo);

    AnalisisGenotipo.$inject = ['$resource', 'DateUtils', 'ParametroProcedimientoProcesarParametros'];

    function AnalisisGenotipo ($resource, DateUtils, ParametroProcedimientoProcesarParametros) {
        var resourceUrl =  'api/analisis-genotipos/:id';

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
			'queryFiltered':{method: 'GET', url:'api/analisis-genotipos-filtered',isArray: true},
			'selector':{method: 'GET', url:'api/analisis-genotipos-selector',isArray: true},
			'selectorMin':{method: 'GET', url:'api/analisis-genotipos-selectorMin',isArray: true},
			'items':{method: 'GET', url:'api/analisis-genotipos-items',isArray: true},
			'deleteByIdIn': {method: 'PUT', url:'api/analisis-genotipos-deleteByIdIn', isArray: true},
			'ejecutarProcedimiento':{method: 'GET', url:'api/analisis-genotipos-ejecutar-procedimiento'},
			'verLog':{method: 'GET', url:'api/analisis-genotipos-ver-log-procedimiento',
				transformResponse: function(data) {
					return {content: data};
				}
			},
			'atributos':{method: 'GET', url:'api/analisis-genotipos-atributos',isArray: true},
			'ejecuciones':{method: 'GET', url:'api/analisis-genotipos-ejecuciones',isArray: true,
				transformResponse: function (data) {
	                if (data) {
	                    var ejecuciones = angular.fromJson(data);
	                    
						ejecuciones.sort(function(a,b){
							if(a.procedimiento.nombre > b.procedimiento.nombre){return 1;}
							else if(a.procedimiento.nombre < b.procedimiento.nombre){return -1;}
							else if(a.id > b.id){return 1;}
							else if(a.id < b.id){return -1;}
							return 0;
						});
						return ejecuciones;
	                }else{
	                	return data;
	                }
	                
	            }
			},
			'parametrosEjecucion':{method: 'GET', url:'api/analisis-genotipos-parametros-ejecucion',isArray: true,
				transformResponse: function (data) {
	                if (data) {
	                    var parametros = angular.fromJson(data);
	                    var aux = [];
						parametros.forEach(function(p){
							p.parametro.valor = p.valor;
							aux.push(p.parametro);
						});

						return ParametroProcedimientoProcesarParametros.procesar(aux);
	                }else{
	                	return data;
	                }
	                
	            }
			}
        });
    }
})();
