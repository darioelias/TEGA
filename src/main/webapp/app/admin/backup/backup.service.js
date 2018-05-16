(function() {
    'use strict';
    angular
        .module('proyectoApp')
        .factory('Backup', Backup);

    Backup.$inject = ['$resource'];

    function Backup ($resource) {
        var resourceUrl =  'api/backup';

        return $resource(resourceUrl, {}, {
            'crear': { method: 'GET', url:'api/backup-crear'}
        });
    }
})();
