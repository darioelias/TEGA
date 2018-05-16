(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('userfiltros', function () {
		    return {
				restrict: 'E',
				transclude: true,
		        scope: {
					criterio: '='
				},
				templateUrl: 'app/admin/user-management/user-management-filtros.html',
				controller: ['$scope','$translate', '$translatePartialLoader',
							 function filterController($scope, $translate, $translatePartialLoader) {

					$translatePartialLoader.addPart('user-management');
                    $translatePartialLoader.addPart('global');
                    $translate.refresh();

					if($scope.criterio == null || Object.keys($scope.criterio).length == 0)
						$scope.criterio = {login: null, firstName: null, lastName: null, email: null, 
										   activated: null, id: null, rolUsuario: null};	

					$scope.cambioRolUsuario = function(){
						if($scope.criterio.rolUsuario == "")
							$scope.criterio.rolUsuario = null;
					}			

					$scope.cambioActivated = function(){
						if($scope.criterio.activated == "")
							$scope.criterio.activated = null;
					}	
				}]
		    };
		});
		
})();
