import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CyberholocampusSharedModule } from '../../shared';
import { CyberholocampusAdminModule } from '../../admin/admin.module';
import {
    AffectationService,
    AffectationPopupService,
    AffectationComponent,
    AffectationDetailComponent,
    AffectationDialogComponent,
    AffectationPopupComponent,
    AffectationDeletePopupComponent,
    AffectationDeleteDialogComponent,
    affectationRoute,
    affectationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...affectationRoute,
    ...affectationPopupRoute,
];

@NgModule({
    imports: [
        CyberholocampusSharedModule,
        CyberholocampusAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AffectationComponent,
        AffectationDetailComponent,
        AffectationDialogComponent,
        AffectationDeleteDialogComponent,
        AffectationPopupComponent,
        AffectationDeletePopupComponent,
    ],
    entryComponents: [
        AffectationComponent,
        AffectationDialogComponent,
        AffectationPopupComponent,
        AffectationDeleteDialogComponent,
        AffectationDeletePopupComponent,
    ],
    providers: [
        AffectationService,
        AffectationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CyberholocampusAffectationModule {}
