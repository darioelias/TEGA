(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('LocusDialog', LocusDialog)
        .controller('LocusDialogController', LocusDialogController);

    LocusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Locus', 'Alelo', 'AnalisisGenotipo'];

    function LocusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Locus, Alelo, AnalisisGenotipo) {
        var vm = this;

        vm.locus = entity;
        vm.clear = clear;
        vm.save = save;
		vm.locus.atributos = [];

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.locus.id !== null) {
                Locus.update(vm.locus, onSaveSuccess, onSaveError);
            } else {
                Locus.save(vm.locus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoApp:locusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }

	function LocusDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Locus'];

		function getService($uibModal, Locus){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						templateUrl: 'app/entities/locus/locus-dialog.html',
				        controller: 'LocusDialogController',
				        controllerAs: 'vm',
				        backdrop: 'static',
				        size: 'lg',
				        resolve: {
				            entity: function() {return id != null ? Locus.get({id : id}).$promise : {id : null};}
				        }
					}).result;
			}
		}
	}
})();
