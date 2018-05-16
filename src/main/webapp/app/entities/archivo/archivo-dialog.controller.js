(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('ArchivoDialogController', ArchivoDialogController);

    ArchivoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Archivo', 'Muestra', 'Extraccion', 'TipoArchivo', 'AnalisisGenotipo', 'Proyecto'];

    function ArchivoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Archivo, Muestra, Extraccion, TipoArchivo, AnalisisGenotipo, Proyecto) {
        var vm = this;

        vm.archivo = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.archivo.id !== null) {
                Archivo.update(vm.archivo, onSaveSuccess, onSaveError);
            } else {
                Archivo.save(vm.archivo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('proyectoApp:archivoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
