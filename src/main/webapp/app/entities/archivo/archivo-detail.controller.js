(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('ArchivoDetailController', ArchivoDetailController);

    ArchivoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Archivo', 'Muestra', 'Extraccion', 'TipoArchivo', 'AnalisisGenotipo', 'Proyecto'];

    function ArchivoDetailController($scope, $rootScope, $stateParams, previousState, entity, Archivo, Muestra, Extraccion, TipoArchivo, AnalisisGenotipo, Proyecto) {
        var vm = this;

        vm.archivo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('proyectoApp:archivoUpdate', function(event, result) {
            vm.archivo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
