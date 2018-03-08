import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { NotificationDataComponent } from './notification-data.component';
import { NotificationDataDetailComponent } from './notification-data-detail.component';
import { NotificationDataPopupComponent } from './notification-data-dialog.component';
import { NotificationDataDeletePopupComponent } from './notification-data-delete-dialog.component';

export const notificationDataRoute: Routes = [
    {
        path: 'notification-data',
        component: NotificationDataComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cyberholocampusApp.notificationData.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'notification-data/:id',
        component: NotificationDataDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cyberholocampusApp.notificationData.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const notificationDataPopupRoute: Routes = [
    {
        path: 'notification-data-new',
        component: NotificationDataPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cyberholocampusApp.notificationData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'notification-data/:id/edit',
        component: NotificationDataPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cyberholocampusApp.notificationData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'notification-data/:id/delete',
        component: NotificationDataDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'cyberholocampusApp.notificationData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
