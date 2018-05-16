(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('ParametroProcedimientoProcesarParametros', ParametroProcedimientoProcesarParametros)

	function ParametroProcedimientoProcesarParametros() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'ParametroProcedimiento'];

		function getService($uibModal, ParametroProcedimiento){
		
			return {procesar: procesar};

			function procesar(parametros){
				var categorias = [];

				parametros.sort(function(a,b){
					if(a.codigo > b.codigo){return 1;}
					else if(a.codigo < b.codigo){return -1;}
					return 0;
				});

				parametros.forEach(function(p){
					var c = categorias.find(function(e){return e.id == p.categoria.id});
					if(c){
						c.parametros.push(p);
					}else{
						p.categoria.parametros = [];
						p.categoria.parametros.push(p);
						categorias.push(p.categoria);
					}
					delete p.categoria;
					delete p.procedimiento;
				});

				categorias.sort(function(a,b){
					if(a.nombre > b.nombre){return 1;}
					else if(a.nombre < b.nombre){return -1;}
					return 0;
				});

				return categorias; 
			}
		}
	}
})();
