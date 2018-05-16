(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('FooterController', FooterController);

    FooterController.$inject = ['$q','$scope', '$rootScope', '$state', 'ParametroTega','JhiLanguageService'];

    function FooterController ($q, $scope, $rootScope, $state, ParametroTega, JhiLanguageService) {
        var vm = this;


		vm.HTML_FOOTER = "";
		vm.HTML_FOOTER_ES = "";
		vm.HTML_FOOTER_EN = "";

		vm.pHtml_ES = ParametroTega.obtenerPublico({codigo: "HTML_FOOTER_ES"});
		vm.pHtml_EN = ParametroTega.obtenerPublico({codigo: "HTML_FOOTER_EN"});
		vm.lenguaje = JhiLanguageService.getCurrent();		

		$q.all([vm.pHtml_ES.$promise, 
				vm.pHtml_EN.$promise,
				vm.lenguaje.$promise]).then(function(){
			vm.HTML_FOOTER_ES = vm.pHtml_ES.valor;
			vm.HTML_FOOTER_EN = vm.pHtml_EN.valor;
			setHTML(vm.lenguaje.$$state.value);
		});

		function setHTML(lenguaje){
			if(lenguaje == "es"){
				vm.HTML_FOOTER = vm.HTML_FOOTER_ES
			}else{
				vm.HTML_FOOTER = vm.HTML_FOOTER_EN
			}
		}

		$rootScope.$on('$translateChangeSuccess', function(event, current, previous) {
			setHTML(current.language)
		});
    }
})();
