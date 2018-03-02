import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MicroNotificationModule } from './notification/notification.module';
import { MicroInfoDefinitionModule } from './info-definition/info-definition.module';
import { MicroInfoModule } from './info/info.module';
import { MicroAffectationModule } from './affectation/affectation.module';
import { MicroBuildingModule } from './building/building.module';
import { MicroBuildingDataDefinitionModule } from './building-data-definition/building-data-definition.module';
import { MicroBuildingDataModule } from './building-data/building-data.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        MicroNotificationModule,
        MicroInfoDefinitionModule,
        MicroInfoModule,
        MicroAffectationModule,
        MicroBuildingModule,
        MicroBuildingDataDefinitionModule,
        MicroBuildingDataModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MicroEntityModule {}
