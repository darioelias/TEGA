(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('linkpadron', function () {
		    return {
				restrict: 'E',
		        scope: {
					link: '@',
					detalle: '=',
					id: '='
				},
				templateUrl: '/linkpadron.html',
				controller: ['$scope','$state','Principal', function linkPadronController($scope, $state, Principal) {
	
					$scope.esABM = Principal.hasAnyAuthority(['ROLE_ADMIN','ROLE_ABM']);

					$scope.getLinkUrl = function(){
					  return $state.href($scope.link, {id: $scope.id});
					};
				
				}]
		    };
		});
		
})();
