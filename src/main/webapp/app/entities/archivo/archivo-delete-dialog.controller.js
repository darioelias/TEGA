(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .controller('ArchivoDeleteController',ArchivoDeleteController);

    ArchivoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Archivo'];

    function ArchivoDeleteController($uibModalInstance, entity, Archivo) {
        var vm = this;

        vm.archivo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Archivo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
