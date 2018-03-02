import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BuildingDataDefinitionComponent } from './building-data-definition.component';
import { BuildingDataDefinitionDetailComponent } from './building-data-definition-detail.component';
import { BuildingDataDefinitionPopupComponent } from './building-data-definition-dialog.component';
import { BuildingDataDefinitionDeletePopupComponent } from './building-data-definition-delete-dialog.component';

@Injectable()
export class BuildingDataDefinitionResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const buildingDataDefinitionRoute: Routes = [
    {
        path: 'building-data-definition',
        component: BuildingDataDefinitionComponent,
        resolve: {
            'pagingParams': BuildingDataDefinitionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.buildingDataDefinition.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'building-data-definition/:id',
        component: BuildingDataDefinitionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.buildingDataDefinition.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const buildingDataDefinitionPopupRoute: Routes = [
    {
        path: 'building-data-definition-new',
        component: BuildingDataDefinitionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.buildingDataDefinition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'building-data-definition/:id/edit',
        component: BuildingDataDefinitionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.buildingDataDefinition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'building-data-definition/:id/delete',
        component: BuildingDataDefinitionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.buildingDataDefinition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
