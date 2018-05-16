(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('UserManagementDialog', UserManagementDialog)
        .controller('UserManagementDialogController',UserManagementDialogController);

    UserManagementDialogController.$inject = ['$stateParams', '$uibModalInstance', 'entity', 'User', 'JhiLanguageService'];

    function UserManagementDialogController ($stateParams, $uibModalInstance, entity, User, JhiLanguageService) {
        var vm = this;

        vm.authorities = ['ROLE_USER','ROLE_USER_EXTENDED','ROLE_ABM','ROLE_MANAGER','ROLE_ADMIN'];
        vm.clear = clear;
        vm.languages = null;
        vm.save = save;
        vm.user = entity;


        JhiLanguageService.getAll().then(function (languages) {
            vm.languages = languages;
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function onSaveSuccess (result) {
            vm.isSaving = false;
            $uibModalInstance.close(result);
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function save () {
            vm.isSaving = true;
            if (vm.user.id !== null) {
                User.update(vm.user, onSaveSuccess, onSaveError);
            } else {
                User.save(vm.user, onSaveSuccess, onSaveError);
            }
        }
    }


	function UserManagementDialog() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'User'];

		function getService($uibModal, User){
		
			return {open: open};

			function open(login){

				return $uibModal.open({
						templateUrl: 'app/admin/user-management/user-management-dialog.html',
				        controller: 'UserManagementDialogController',
				        controllerAs: 'vm',
				        backdrop: 'static',
				        size: 'lg',
				        resolve: {
				            entity: function() {return login != null ? User.get({login : login}).$promise : {login : null, id: null};}
				        }
					}).result;
			}
		}
	}
})();
