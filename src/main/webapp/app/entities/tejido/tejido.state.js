(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tejido', {
            parent: 'entity',
            url: '/tejido?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoApp.tejido.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tejido/tejidos.html',
                    controller: 'TejidoController',
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
                    $translatePartialLoader.addPart('tejido');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tejido-detail', {
            parent: 'entity',
            url: '/tejido/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoApp.tejido.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tejido/tejido-detail.html',
                    controller: 'TejidoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tejido');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tejido', function($stateParams, Tejido) {
                    return Tejido.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {

					var name = $state.current.name || 'tejido';
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
        });
    }

})();
