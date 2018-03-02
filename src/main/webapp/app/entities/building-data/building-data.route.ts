import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BuildingDataComponent } from './building-data.component';
import { BuildingDataDetailComponent } from './building-data-detail.component';
import { BuildingDataPopupComponent } from './building-data-dialog.component';
import { BuildingDataDeletePopupComponent } from './building-data-delete-dialog.component';

@Injectable()
export class BuildingDataResolvePagingParams implements Resolve<any> {

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

export const buildingDataRoute: Routes = [
    {
        path: 'building-data',
        component: BuildingDataComponent,
        resolve: {
            'pagingParams': BuildingDataResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.buildingData.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'building-data/:id',
        component: BuildingDataDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.buildingData.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const buildingDataPopupRoute: Routes = [
    {
        path: 'building-data-new',
        component: BuildingDataPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.buildingData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'building-data/:id/edit',
        component: BuildingDataPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.buildingData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'building-data/:id/delete',
        component: BuildingDataDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.buildingData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
