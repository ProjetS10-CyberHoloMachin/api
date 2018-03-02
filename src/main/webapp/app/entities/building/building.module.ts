import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MicroSharedModule } from '../../shared';
import {
    BuildingService,
    BuildingPopupService,
    BuildingComponent,
    BuildingDetailComponent,
    BuildingDialogComponent,
    BuildingPopupComponent,
    BuildingDeletePopupComponent,
    BuildingDeleteDialogComponent,
    buildingRoute,
    buildingPopupRoute,
    BuildingResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...buildingRoute,
    ...buildingPopupRoute,
];

@NgModule({
    imports: [
        MicroSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BuildingComponent,
        BuildingDetailComponent,
        BuildingDialogComponent,
        BuildingDeleteDialogComponent,
        BuildingPopupComponent,
        BuildingDeletePopupComponent,
    ],
    entryComponents: [
        BuildingComponent,
        BuildingDialogComponent,
        BuildingPopupComponent,
        BuildingDeleteDialogComponent,
        BuildingDeletePopupComponent,
    ],
    providers: [
        BuildingService,
        BuildingPopupService,
        BuildingResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MicroBuildingModule {}
