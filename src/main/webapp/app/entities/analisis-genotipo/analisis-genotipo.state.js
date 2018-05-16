(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('analisis-genotipo', {
            parent: 'entity',
            url: '/analisis-genotipo?page&sort&search',
            data: {
                authorities: ['ROLE_USER_EXTENDED'],
                pageTitle: 'proyectoApp.analisisGenotipo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/analisis-genotipo/analisis-genotipos.html',
                    controller: 'AnalisisGenotipoController',
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
                    $translatePartialLoader.addPart('analisisGenotipo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('analisis-genotipo-detail', {
            parent: 'entity',
            url: '/analisis-genotipo/{id}',
            data: {
                authorities: ['ROLE_USER_EXTENDED'],
                pageTitle: 'proyectoApp.analisisGenotipo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/analisis-genotipo/analisis-genotipo-detail.html',
                    controller: 'AnalisisGenotipoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('analisisGenotipo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'AnalisisGenotipo', function($stateParams, AnalisisGenotipo) {
                    return AnalisisGenotipo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {

					var name = $state.current.name || 'analisis-genotipo';
					if($state.params.id !== undefined)
						name = name+"({id:"+$state.params.id+"})";

                    var currentStateData = {
                        name: name,
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
		.state('analisis-genotipo-alelos', {
            parent: 'analisis-genotipo',
            url: '/analisis-genotipo-alelos/{id}',
            data: {
                authorities: ['ROLE_USER_EXTENDED']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/analisis-genotipo/analisis-genotipos-alelos.html',
                    controller: 'AnalisisGenotiposAlelosController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AnalisisGenotipo', function(AnalisisGenotipo) {
                            return AnalisisGenotipo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('analisis-genotipo', null, { reload: 'analisis-genotipo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
		.state('analisis-genotipo-structure', {
            parent: 'analisis-genotipo',
            url: '/analisis-genotipo-structure/{id}',
            data: {
                authorities: ['ROLE_ABM']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/analisis-genotipo/analisis-genotipo-structure-dialog.html',
                    controller: 'AnalisisGenotipoStructureDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
					resolve: {
                        id: [function(){return $stateParams.id;}]
                    }
                }).result.then(function() {
                    $state.go('analisis-genotipo', null, { reload: 'analisis-genotipo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
		.state('analisis-genotipo-genepop', {
            parent: 'analisis-genotipo',
            url: '/analisis-genotipo-genepop/{id}',
            data: {
                authorities: ['ROLE_ABM']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/analisis-genotipo/analisis-genotipo-genepop-dialog.html',
                    controller: 'AnalisisGenotipoGenePopDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
					resolve: {
                        id: [function(){return $stateParams.id;}]
                    }
                }).result.then(function() {
                    $state.go('analisis-genotipo', null, { reload: 'analisis-genotipo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
