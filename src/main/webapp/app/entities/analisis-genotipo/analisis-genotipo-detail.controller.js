(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('AnalisisGenotipoDetailController', AnalisisGenotipoDetailController);

    AnalisisGenotipoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 
												'entity', 'AnalisisGenotipo', 'Archivo', 'Locus', 
												'ConjuntoMuestras', 'Proyecto','AnalisisGenotipoDialog'];

    function AnalisisGenotipoDetailController($scope, $rootScope, $stateParams, previousState, 
											  entity, AnalisisGenotipo, Archivo, Locus, 
											  ConjuntoMuestras, Proyecto, AnalisisGenotipoDialog) {
        var vm = this;
        
        vm.previousState = previousState.name;
		vm.editar = editar;
		vm.detalleEjecucion = detalleEjecucion;
		vm.cargarEjecucion = cargarEjecucion;

       	load(entity);

		function load(entity){
			vm.analisisGenotipo = entity;
			vm.analisisGenotipo.atributos = [];
			vm.editable = vm.analisisGenotipo.estado == "DISPONIBLE";
			vm.analisisGenotipo.conjuntosMuestras = ConjuntoMuestras.analisisGenotipo({id:vm.analisisGenotipo.id});
			vm.analisisGenotipo.loci = Locus.analisisGenotipo({id:vm.analisisGenotipo.id});
			vm.analisisGenotipo.archivos = Archivo.analisisGenotipo({id:vm.analisisGenotipo.id});
			vm.analisisGenotipo.ejecuciones = AnalisisGenotipo.ejecuciones({id:vm.analisisGenotipo.id});
		}

		function editar(){
			AnalisisGenotipoDialog.open(vm.analisisGenotipo.id)
					   .then(function(){AnalisisGenotipo.get({id : vm.analisisGenotipo.id}, load)});
		}

		function detalleEjecucion(analisis){
			if(analisis.estado != "DISPONIBLE" && analisis.procedimiento){
				return analisis.procedimiento.nombre;
			}
			return "";
		}

		function cargarEjecucion(e){
			if(!e.archivos)
				e.archivos = Archivo.ejecucion({id: e.id});

			if(!e.categorias)
				e.categorias = AnalisisGenotipo.parametrosEjecucion({id: e.id});
		}
    }
})();
