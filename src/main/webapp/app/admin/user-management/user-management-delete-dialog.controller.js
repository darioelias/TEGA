(function() {
    'use strict';

    angular
        .module('proyectoApp')
		.provider('UserManagementDelete', UserManagementDelete)
        .controller('UserManagementDeleteController', UserManagementDeleteController);

    UserManagementDeleteController.$inject = ['$uibModalInstance', 'entity', 'User'];

    function UserManagementDeleteController ($uibModalInstance, entity, User) {
        var vm = this;

        vm.user = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (login) {
            User.delete({login: login},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }

	function UserManagementDelete() {
	
		this.$get = getService;

		getService.$inject = ['$uibModal', 'User'];

		function getService($uibModal, User){
		
			return {open: open};

			function open(login){

				return $uibModal.open({
						        templateUrl: 'app/admin/user-management/user-management-delete-dialog.html',
						        controller: 'UserManagementDeleteController',
						        controllerAs: 'vm',
						        size: 'md',
						        resolve: {
						            entity: function() {return User.get({login : login}).$promise;}
								}
                   	   }).result;
			}
		}
	}
})();
