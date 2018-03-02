import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { InfoDefinitionComponent } from './info-definition.component';
import { InfoDefinitionDetailComponent } from './info-definition-detail.component';
import { InfoDefinitionPopupComponent } from './info-definition-dialog.component';
import { InfoDefinitionDeletePopupComponent } from './info-definition-delete-dialog.component';

@Injectable()
export class InfoDefinitionResolvePagingParams implements Resolve<any> {

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

export const infoDefinitionRoute: Routes = [
    {
        path: 'info-definition',
        component: InfoDefinitionComponent,
        resolve: {
            'pagingParams': InfoDefinitionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.infoDefinition.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'info-definition/:id',
        component: InfoDefinitionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.infoDefinition.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const infoDefinitionPopupRoute: Routes = [
    {
        path: 'info-definition-new',
        component: InfoDefinitionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.infoDefinition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'info-definition/:id/edit',
        component: InfoDefinitionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.infoDefinition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'info-definition/:id/delete',
        component: InfoDefinitionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'microApp.infoDefinition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
