import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MicroSharedModule } from '../../shared';
import {
    InfoDefinitionService,
    InfoDefinitionPopupService,
    InfoDefinitionComponent,
    InfoDefinitionDetailComponent,
    InfoDefinitionDialogComponent,
    InfoDefinitionPopupComponent,
    InfoDefinitionDeletePopupComponent,
    InfoDefinitionDeleteDialogComponent,
    infoDefinitionRoute,
    infoDefinitionPopupRoute,
    InfoDefinitionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...infoDefinitionRoute,
    ...infoDefinitionPopupRoute,
];

@NgModule({
    imports: [
        MicroSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        InfoDefinitionComponent,
        InfoDefinitionDetailComponent,
        InfoDefinitionDialogComponent,
        InfoDefinitionDeleteDialogComponent,
        InfoDefinitionPopupComponent,
        InfoDefinitionDeletePopupComponent,
    ],
    entryComponents: [
        InfoDefinitionComponent,
        InfoDefinitionDialogComponent,
        InfoDefinitionPopupComponent,
        InfoDefinitionDeleteDialogComponent,
        InfoDefinitionDeletePopupComponent,
    ],
    providers: [
        InfoDefinitionService,
        InfoDefinitionPopupService,
        InfoDefinitionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MicroInfoDefinitionModule {}
