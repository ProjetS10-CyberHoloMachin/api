import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MicroSharedModule } from '../../shared';
import {
    BuildingDataService,
    BuildingDataPopupService,
    BuildingDataComponent,
    BuildingDataDetailComponent,
    BuildingDataDialogComponent,
    BuildingDataPopupComponent,
    BuildingDataDeletePopupComponent,
    BuildingDataDeleteDialogComponent,
    buildingDataRoute,
    buildingDataPopupRoute,
    BuildingDataResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...buildingDataRoute,
    ...buildingDataPopupRoute,
];

@NgModule({
    imports: [
        MicroSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BuildingDataComponent,
        BuildingDataDetailComponent,
        BuildingDataDialogComponent,
        BuildingDataDeleteDialogComponent,
        BuildingDataPopupComponent,
        BuildingDataDeletePopupComponent,
    ],
    entryComponents: [
        BuildingDataComponent,
        BuildingDataDialogComponent,
        BuildingDataPopupComponent,
        BuildingDataDeleteDialogComponent,
        BuildingDataDeletePopupComponent,
    ],
    providers: [
        BuildingDataService,
        BuildingDataPopupService,
        BuildingDataResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MicroBuildingDataModule {}
