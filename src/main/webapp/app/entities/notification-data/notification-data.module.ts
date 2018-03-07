import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CyberholocampusSharedModule } from '../../shared';
import {
    NotificationDataService,
    NotificationDataPopupService,
    NotificationDataComponent,
    NotificationDataDetailComponent,
    NotificationDataDialogComponent,
    NotificationDataPopupComponent,
    NotificationDataDeletePopupComponent,
    NotificationDataDeleteDialogComponent,
    notificationDataRoute,
    notificationDataPopupRoute,
} from './';

const ENTITY_STATES = [
    ...notificationDataRoute,
    ...notificationDataPopupRoute,
];

@NgModule({
    imports: [
        CyberholocampusSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        NotificationDataComponent,
        NotificationDataDetailComponent,
        NotificationDataDialogComponent,
        NotificationDataDeleteDialogComponent,
        NotificationDataPopupComponent,
        NotificationDataDeletePopupComponent,
    ],
    entryComponents: [
        NotificationDataComponent,
        NotificationDataDialogComponent,
        NotificationDataPopupComponent,
        NotificationDataDeleteDialogComponent,
        NotificationDataDeletePopupComponent,
    ],
    providers: [
        NotificationDataService,
        NotificationDataPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CyberholocampusNotificationDataModule {}
