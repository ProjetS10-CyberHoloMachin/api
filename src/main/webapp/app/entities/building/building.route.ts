import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BuildingComponent } from './building.component';
import { BuildingDetailComponent } from './building-detail.component';
import { BuildingPopupComponent } from './building-dialog.component';
import { BuildingDeletePopupComponent } from './building-delete-dialog.component';

@Injectable()
export class BuildingResolvePagingParams implements Resolve<any> {

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

export const buildingRoute: Routes = [
    {
        path: 'building',
        component: BuildingComponent,
        resolve: {
            'pagingParams': BuildingResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.building.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'building/:id',
        component: BuildingDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.building.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const buildingPopupRoute: Routes = [
    {
        path: 'building-new',
        component: BuildingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.building.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'building/:id/edit',
        component: BuildingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.building.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'building/:id/delete',
        component: BuildingDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.building.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
