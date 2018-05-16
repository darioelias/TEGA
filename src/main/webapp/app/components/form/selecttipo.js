(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .directive('selecttipo', function () {
		    return {
		        scope: {
					tipo: '=',
					tr: '@',
					opcional: '=?',
					sinlabel: '=?',
					singroup: '=?',
					change: '=',
					readonly: '=?'
				},
				templateUrl: '/selecttipo.html',
				controller: ['$scope',function selecttipoController($scope) {
							
					if($scope.tr === undefined)
						$scope.tr = "general.tipo";	

					if($scope.opcional === undefined)
						$scope.opcional = true;						
		
					if($scope.tipo === undefined)
						$scope.tipo = null;		

					if($scope.readonly === undefined)
						$scope.readonly = false;				

					if($scope.sinlabel == null)
						$scope.sinlabel = false;
					
					if($scope.singroup == null)
						$scope.singroup = false;	

				}]
		    };
		});
		
})();
