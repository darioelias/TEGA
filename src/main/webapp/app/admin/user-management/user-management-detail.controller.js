(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('UserManagementDetailController', UserManagementDetailController);

    UserManagementDetailController.$inject = ['$stateParams', 'User', 'entity', 'UserManagementDialog'];

    function UserManagementDetailController ($stateParams, User, entity, UserManagementDialog) {
        var vm = this;

        vm.load = load;
        vm.editar = editar;

        load(entity);

		function load(entity){
			vm.user = entity;
		}

        function editar(){
			UserManagementDialog.open(vm.user.login)
					   			.then(function(){User.get({login : vm.user.login}, load)});
		}
    }
})();
