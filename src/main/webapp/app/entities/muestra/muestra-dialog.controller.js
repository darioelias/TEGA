(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('MuestraDialog', MuestraDialog)
        .controller('MuestraDialogController', MuestraDialogController);

    MuestraDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 
									   'entity', 'Muestra', 'Archivo', 'Region', 'Localidad', 
									   'Profesional', 'Institucion', 'Especie', 'Tejido', 'ModoRecoleccion',
									   'Proyecto','Alelo','ConjuntoMuestras'];

    function MuestraDialogController ($timeout, $scope, $stateParams, $uibModalInstance, 
									  entity, Muestra, Archivo, Region, Localidad, 
									  Profesional, Institucion, Especie, Tejido, ModoRecoleccion,
									  Proyecto,Alelo,ConjuntoMuestras) {


        var vm = this;

        vm.muestra = entity;
        vm.clear = clear;
        vm.save = save;
		vm.muestra.atributos = [];

		if(vm.muestra.id == null){
			vm.muestra.fechaRecoleccion = new Date();
			vm.muestra.proyectos = [];
			vm.muestra.archivos = [];
			vm.muestra.alelos = [];
			vm.muestra.conjuntosMuestras = [];
		}else{
			vm.muestra.proyectos = Proyecto.muestra({id: vm.muestra.id});
			vm.muestra.archivos = Archivo.muestra({id: vm.muestra.id});
			vm.muestra.alelos = Alelo.muestra({id:vm.muestra.id});
			vm.muestra.conjuntosMuestras = ConjuntoMuestras.muestra({id:vm.muestra.id});
		}

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.muestra.id !== null) {
                Muestra.update(vm.muestra, onSaveSuccess, onSaveError);
            } else {
                Muestra.save(vm.muestra, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoApp:muestraUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

    }



	function MuestraDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Muestra'];

		function getService($uibModal, Muestra){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						templateUrl: 'app/entities/muestra/muestra-dialog.html',
				        controller: 'MuestraDialogController',
				        controllerAs: 'vm',
				        backdrop: 'static',
				        size: 'lg',
				        resolve: {
				            entity: function() {return id != null ? Muestra.get({id : id}).$promise : {id : null};}
				        }
					}).result;
			}
		}
	}
})();
