import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MicroSharedModule } from '../../shared';
import { MicroAdminModule } from '../../admin/admin.module';
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
    AffectationResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...affectationRoute,
    ...affectationPopupRoute,
];

@NgModule({
    imports: [
        MicroSharedModule,
        MicroAdminModule,
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
        AffectationResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MicroAffectationModule {}
