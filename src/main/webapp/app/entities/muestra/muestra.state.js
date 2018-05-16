(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('muestra', {
            parent: 'entity',
            url: '/muestra?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoApp.muestra.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/muestra/muestras.html',
                    controller: 'MuestraController',
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
                    $translatePartialLoader.addPart('muestra');
                    $translatePartialLoader.addPart('sexo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('muestra-detail', {
            parent: 'entity',
            url: '/muestra/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoApp.muestra.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/muestra/muestra-detail.html',
                    controller: 'MuestraDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('muestra');
                    $translatePartialLoader.addPart('sexo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Muestra', function($stateParams, Muestra) {
                    return Muestra.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {

					var name = $state.current.name || 'muestra';
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
		.state('muestras-map', {
            parent: 'entity',
            url: '/muestras-map',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'proyectoApp.muestra.home.map'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/muestra/muestras-map.html',
                    controller: 'MuestraMapController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('muestra');
                    $translatePartialLoader.addPart('sexo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        });
    }

})();
