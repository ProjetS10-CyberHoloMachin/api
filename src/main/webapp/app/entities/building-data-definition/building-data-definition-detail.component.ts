import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { BuildingDataDefinition } from './building-data-definition.model';
import { BuildingDataDefinitionService } from './building-data-definition.service';

@Component({
    selector: 'jhi-building-data-definition-detail',
    templateUrl: './building-data-definition-detail.component.html'
})
export class BuildingDataDefinitionDetailComponent implements OnInit, OnDestroy {

    buildingDataDefinition: BuildingDataDefinition;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private buildingDataDefinitionService: BuildingDataDefinitionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBuildingDataDefinitions();
    }

    load(id) {
        this.buildingDataDefinitionService.find(id)
            .subscribe((buildingDataDefinitionResponse: HttpResponse<BuildingDataDefinition>) => {
                this.buildingDataDefinition = buildingDataDefinitionResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBuildingDataDefinitions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'buildingDataDefinitionListModification',
            (response) => this.load(this.buildingDataDefinition.id)
        );
    }
}
