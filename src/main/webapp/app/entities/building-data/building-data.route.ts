import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { BuildingDataComponent } from './building-data.component';
import { BuildingDataDetailComponent } from './building-data-detail.component';
import { BuildingDataPopupComponent } from './building-data-dialog.component';
import { BuildingDataDeletePopupComponent } from './building-data-delete-dialog.component';

export const buildingDataRoute: Routes = [
    {
        path: 'building-data',
        component: BuildingDataComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cyberholocampusApp.buildingData.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'building-data/:id',
        component: BuildingDataDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cyberholocampusApp.buildingData.home.title'
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
            pageTitle: 'cyberholocampusApp.buildingData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'building-data/:id/edit',
        component: BuildingDataPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cyberholocampusApp.buildingData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'building-data/:id/delete',
        component: BuildingDataDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cyberholocampusApp.buildingData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
