import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CyberholocampusSharedModule } from '../../shared';
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
} from './';

const ENTITY_STATES = [
    ...buildingDataRoute,
    ...buildingDataPopupRoute,
];

@NgModule({
    imports: [
        CyberholocampusSharedModule,
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
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CyberholocampusBuildingDataModule {}
