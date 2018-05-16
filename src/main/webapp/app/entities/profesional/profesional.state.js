(function() {
    'use strict';

    angular
        .module('proyectoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('profesional', {
            parent: 'entity',
            url: '/profesional?page&sort&search',
            data: {
                authorities: ['ROLE_ABM'],
                pageTitle: 'proyectoApp.profesional.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/profesional/profesionales.html',
                    controller: 'ProfesionalController',
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
                    $translatePartialLoader.addPart('profesional');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('profesional-detail', {
            parent: 'entity',
            url: '/profesional/{id}',
            data: {
                authorities: ['ROLE_ABM'],
                pageTitle: 'proyectoApp.profesional.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/profesional/profesional-detail.html',
                    controller: 'ProfesionalDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('profesional');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Profesional', function($stateParams, Profesional) {
                    return Profesional.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {

					var name = $state.current.name || 'profesional';
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
