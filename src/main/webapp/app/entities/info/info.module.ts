import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MicroSharedModule } from '../../shared';
import {
    InfoService,
    InfoPopupService,
    InfoComponent,
    InfoDetailComponent,
    InfoDialogComponent,
    InfoPopupComponent,
    InfoDeletePopupComponent,
    InfoDeleteDialogComponent,
    infoRoute,
    infoPopupRoute,
    InfoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...infoRoute,
    ...infoPopupRoute,
];

@NgModule({
    imports: [
        MicroSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        InfoComponent,
        InfoDetailComponent,
        InfoDialogComponent,
        InfoDeleteDialogComponent,
        InfoPopupComponent,
        InfoDeletePopupComponent,
    ],
    entryComponents: [
        InfoComponent,
        InfoDialogComponent,
        InfoPopupComponent,
        InfoDeleteDialogComponent,
        InfoDeletePopupComponent,
    ],
    providers: [
        InfoService,
        InfoPopupService,
        InfoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MicroInfoModule {}
