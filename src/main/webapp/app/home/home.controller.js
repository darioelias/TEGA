(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$q','$rootScope','$scope', 'Principal', 'LoginService', 
							  '$state', 'ParametroTega','JhiLanguageService'];

    function HomeController ($q, $rootScope, $scope, Principal, LoginService, 
							 $state, ParametroTega,JhiLanguageService) {
        var vm = this;



        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;

		vm.HTML_HOME = "";
		vm.HTML_HOME_ES = "";
		vm.HTML_HOME_EN = "";
		
		
		vm.pHtmlHome_ES = ParametroTega.obtenerPublico({codigo: "HTML_HOME_ES"});
		vm.pHtmlHome_EN = ParametroTega.obtenerPublico({codigo: "HTML_HOME_EN"});
		vm.lenguaje = JhiLanguageService.getCurrent();		


		$q.all([vm.pHtmlHome_ES.$promise, 
				vm.pHtmlHome_EN.$promise,
				vm.lenguaje.$promise]).then(function(){
			vm.HTML_HOME_ES = vm.pHtmlHome_ES.valor;
			vm.HTML_HOME_EN = vm.pHtmlHome_EN.valor;
			setHTML_HOME(vm.lenguaje.$$state.value);
		});

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }

		function setHTML_HOME(lenguaje){
			if(lenguaje == "es"){
				vm.HTML_HOME = vm.HTML_HOME_ES
			}else{
				vm.HTML_HOME = vm.HTML_HOME_EN
			}
		}

		$rootScope.$on('$translateChangeSuccess', function(event, current, previous) {
			setHTML_HOME(current.language);
		});
    }
})();
