import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MicroSharedModule } from '../../shared';
import {
    BuildingDataDefinitionService,
    BuildingDataDefinitionPopupService,
    BuildingDataDefinitionComponent,
    BuildingDataDefinitionDetailComponent,
    BuildingDataDefinitionDialogComponent,
    BuildingDataDefinitionPopupComponent,
    BuildingDataDefinitionDeletePopupComponent,
    BuildingDataDefinitionDeleteDialogComponent,
    buildingDataDefinitionRoute,
    buildingDataDefinitionPopupRoute,
    BuildingDataDefinitionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...buildingDataDefinitionRoute,
    ...buildingDataDefinitionPopupRoute,
];

@NgModule({
    imports: [
        MicroSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BuildingDataDefinitionComponent,
        BuildingDataDefinitionDetailComponent,
        BuildingDataDefinitionDialogComponent,
        BuildingDataDefinitionDeleteDialogComponent,
        BuildingDataDefinitionPopupComponent,
        BuildingDataDefinitionDeletePopupComponent,
    ],
    entryComponents: [
        BuildingDataDefinitionComponent,
        BuildingDataDefinitionDialogComponent,
        BuildingDataDefinitionPopupComponent,
        BuildingDataDefinitionDeleteDialogComponent,
        BuildingDataDefinitionDeletePopupComponent,
    ],
    providers: [
        BuildingDataDefinitionService,
        BuildingDataDefinitionPopupService,
        BuildingDataDefinitionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MicroBuildingDataDefinitionModule {}
