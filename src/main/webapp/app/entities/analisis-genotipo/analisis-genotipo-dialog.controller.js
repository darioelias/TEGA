(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('AnalisisGenotipoDialog', AnalisisGenotipoDialog)
        .controller('AnalisisGenotipoDialogController', AnalisisGenotipoDialogController);

    AnalisisGenotipoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 
												'AnalisisGenotipo', 'Archivo', 'Locus', 'ConjuntoMuestras'];

    function AnalisisGenotipoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, 
												AnalisisGenotipo, Archivo, Locus, ConjuntoMuestras) {
        var vm = this;

        vm.analisisGenotipo = entity;
        vm.clear = clear;
        vm.save = save;
		vm.detalleEjecucion = detalleEjecucion;
		vm.cargarEjecucion = cargarEjecucion;
		vm.eliminarEjecucion = eliminarEjecucion;
		vm.analisisGenotipo.atributos = [];

		if(vm.analisisGenotipo.id == null){
			vm.analisisGenotipo.fecha = new Date();
			vm.analisisGenotipo.conjuntosMuestras = [];
			vm.analisisGenotipo.loci = [];
			vm.analisisGenotipo.archivos = [];
			vm.analisisGenotipo.ejecuciones = [];
			vm.analisisGenotipo.estado = null;
		}else{
			vm.analisisGenotipo.conjuntosMuestras = ConjuntoMuestras.analisisGenotipo({id:vm.analisisGenotipo.id});
			vm.analisisGenotipo.loci = Locus.analisisGenotipo({id:vm.analisisGenotipo.id});
			vm.analisisGenotipo.archivos = Archivo.analisisGenotipo({id:vm.analisisGenotipo.id});
			vm.analisisGenotipo.ejecuciones = AnalisisGenotipo.ejecuciones({id:vm.analisisGenotipo.id});
		}


        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.analisisGenotipo.id !== null) {
                AnalisisGenotipo.update(vm.analisisGenotipo, onSaveSuccess, onSaveError);
            } else {
                AnalisisGenotipo.save(vm.analisisGenotipo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoApp:analisisGenotipoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
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


		function eliminarEjecucion(e){
			if (e != null){
				vm.analisisGenotipo.ejecuciones.splice(vm.analisisGenotipo.ejecuciones.indexOf(e), 1);
			}
		}

    }


	function AnalisisGenotipoDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'AnalisisGenotipo'];

		function getService($uibModal, AnalisisGenotipo){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						templateUrl: 'app/entities/analisis-genotipo/analisis-genotipo-dialog.html',
				        controller: 'AnalisisGenotipoDialogController',
				        controllerAs: 'vm',
				        backdrop: 'static',
				        size: 'lg',
				        resolve: {
				            entity: function() {return id != null ? AnalisisGenotipo.get({id : id}).$promise : {id : null};}
				        }
					}).result;
			}
		}
	}
})();
