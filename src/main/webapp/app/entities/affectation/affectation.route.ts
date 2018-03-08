import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AffectationComponent } from './affectation.component';
import { AffectationDetailComponent } from './affectation-detail.component';
import { AffectationPopupComponent } from './affectation-dialog.component';
import { AffectationDeletePopupComponent } from './affectation-delete-dialog.component';

export const affectationRoute: Routes = [
    {
        path: 'affectation',
        component: AffectationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cyberholocampusApp.affectation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'affectation/:id',
        component: AffectationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cyberholocampusApp.affectation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const affectationPopupRoute: Routes = [
    {
        path: 'affectation-new',
        component: AffectationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cyberholocampusApp.affectation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'affectation/:id/edit',
        component: AffectationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cyberholocampusApp.affectation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'affectation/:id/delete',
        component: AffectationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cyberholocampusApp.affectation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
