import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { InfoComponent } from './info.component';
import { InfoDetailComponent } from './info-detail.component';
import { InfoPopupComponent } from './info-dialog.component';
import { InfoDeletePopupComponent } from './info-delete-dialog.component';

@Injectable()
export class InfoResolvePagingParams implements Resolve<any> {

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

export const infoRoute: Routes = [
    {
        path: 'info',
        component: InfoComponent,
        resolve: {
            'pagingParams': InfoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.info.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'info/:id',
        component: InfoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.info.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const infoPopupRoute: Routes = [
    {
        path: 'info-new',
        component: InfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.info.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'info/:id/edit',
        component: InfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.info.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'info/:id/delete',
        component: InfoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.info.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
