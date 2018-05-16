(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('RegionDialog', RegionDialog)
        .controller('RegionDialogController', RegionDialogController);

    RegionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Region'];

    function RegionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Region) {
        var vm = this;

        vm.region = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.region.id !== null) {
                Region.update(vm.region, onSaveSuccess, onSaveError);
            } else {
                Region.save(vm.region, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoApp:regionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }


	function RegionDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'Region'];

		function getService($uibModal, Region){
		
			return {open: open};

			function open(id){

				return $uibModal.open({
						templateUrl: 'app/entities/region/region-dialog.html',
				        controller: 'RegionDialogController',
				        controllerAs: 'vm',
				        backdrop: 'static',
				        size: 'lg',
				        resolve: {
				            entity: function() {return id != null ? Region.get({id : id}).$promise : {id : null};}
				        }
					}).result;
			}
		}
	}
})();
