import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CyberholocampusNotificationModule } from './notification/notification.module';
import { CyberholocampusNotificationDataModule } from './notification-data/notification-data.module';
import { CyberholocampusAffectationModule } from './affectation/affectation.module';
import { CyberholocampusBuildingModule } from './building/building.module';
import { CyberholocampusBuildingDataModule } from './building-data/building-data.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CyberholocampusNotificationModule,
        CyberholocampusNotificationDataModule,
        CyberholocampusAffectationModule,
        CyberholocampusBuildingModule,
        CyberholocampusBuildingDataModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CyberholocampusEntityModule {}
