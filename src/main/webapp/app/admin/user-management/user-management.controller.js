(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('UserManagementController', UserManagementController);

    UserManagementController.$inject = ['Principal', 'User', 'ParseLinks', 'AlertService', '$state', 
										'pagingParams', 'paginationConstants', 'JhiLanguageService',
										'UserManagementDialog', 'UserManagementDelete'];

    function UserManagementController(Principal, User, ParseLinks, AlertService, $state, 
										pagingParams, paginationConstants, JhiLanguageService,
										UserManagementDialog, UserManagementDelete) {
        var vm = this;

        vm.authorities = ['ROLE_USER','ROLE_USER_EXTENDED','ROLE_ABM','ROLE_MANAGER','ROLE_ADMIN'];
        vm.currentAccount = null;
        vm.languages = null;
        vm.loadAll = loadAll;
        vm.setActive = setActive;
        vm.users = [];
        vm.page = 1;
        vm.totalItems = null;
        vm.clear = clear;
        vm.links = null;
		vm.crearEditar = crearEditar;
		vm.eliminar = eliminar;

        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

		vm.usuarioEditable = usuarioEditable;
		vm.criterio = {};
        vm.loadAll();
        
        JhiLanguageService.getAll().then(function (languages) {
            vm.languages = languages;
        });
        Principal.identity().then(function(account) {
            vm.currentAccount = account;
        });

        function setActive (user, isActivated) {

			if(usuarioEditable(user)){
		        user.activated = isActivated;
		        User.update(user, function () {
		            vm.loadAll();
		            vm.clear();
		        });
			}
        }


        function loadAll (itemsPerPage, page) {

			if(itemsPerPage != null)
				vm.itemsPerPage = itemsPerPage;

			if(page != null)
				vm.page = page;

            var parametros = {page: vm.page - 1, size: vm.itemsPerPage, sort: sort(), criterio: vm.criterio};
			User.queryFiltered(parametros, onSuccess, onError);

            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
			    vm.totalItems = headers('X-Total-Count');
                vm.users = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }


		function crearEditar(login){
			UserManagementDialog.open(login).then(function(){vm.loadAll()});
		}

		function eliminar(login){
			UserManagementDelete.open(login).then(function(){vm.loadAll()});
		}


        function clear () {
            vm.user = {
                id: null, login: null, firstName: null, lastName: null, email: null,
                activated: null, langKey: null, createdBy: null, createdDate: null,
                lastModifiedBy: null, lastModifiedDate: null, resetDate: null,
                resetKey: null, authorities: null
            };
        }


		function usuarioEditable(usuario){

			if(usuario.login === 'anonymoususer')
				return false;

			if(Principal.hasAnyAuthority(['ROLE_ADMIN']))
				return true;

			return usuario.rolUsuario != "IMPLEMENTADOR";				 
		}
    }
})();
