(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('archivo', {
            parent: 'entity',
            url: '/archivo?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoApp.archivo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/archivo/archivos.html',
                    controller: 'ArchivoController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('archivo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('archivo-detail', {
            parent: 'entity',
            url: '/archivo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoApp.archivo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/archivo/archivo-detail.html',
                    controller: 'ArchivoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('archivo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Archivo', function($stateParams, Archivo) {
                    return Archivo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'archivo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('archivo-detail.edit', {
            parent: 'archivo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/archivo/archivo-dialog.html',
                    controller: 'ArchivoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Archivo', function(Archivo) {
                            return Archivo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('archivo.new', {
            parent: 'archivo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/archivo/archivo-dialog.html',
                    controller: 'ArchivoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                detalle: null,
                                direccion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('archivo', null, { reload: 'archivo' });
                }, function() {
                    $state.go('archivo');
                });
            }]
        })
        .state('archivo.edit', {
            parent: 'archivo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/archivo/archivo-dialog.html',
                    controller: 'ArchivoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Archivo', function(Archivo) {
                            return Archivo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('archivo', null, { reload: 'archivo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('archivo.delete', {
            parent: 'archivo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/archivo/archivo-delete-dialog.html',
                    controller: 'ArchivoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Archivo', function(Archivo) {
                            return Archivo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('archivo', null, { reload: 'archivo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
