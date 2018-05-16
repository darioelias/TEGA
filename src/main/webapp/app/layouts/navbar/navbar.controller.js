(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', 'Auth', 'Principal', 'BackupDialog',
								'ProfileService', 'LoginService','ParametroTega'];

    function NavbarController ($state, Auth, Principal, BackupDialog,
								ProfileService, LoginService, ParametroTega) {
        var vm = this;

		vm.URL_LOGO_MENU="";
        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

		ParametroTega.obtenerPublico({codigo: "URL_LOGO_MENU"}, function(p){
			vm.URL_LOGO_MENU = p.valor;
		});

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
		vm.crearBackup = crearBackup;
        vm.$state = $state;

        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }

		function crearBackup(){
			collapseNavbar();
			BackupDialog.open();
		}
    }
})();
