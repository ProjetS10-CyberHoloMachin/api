import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { BuildingData } from './building-data.model';
import { BuildingDataService } from './building-data.service';

@Component({
    selector: 'jhi-building-data-detail',
    templateUrl: './building-data-detail.component.html'
})
export class BuildingDataDetailComponent implements OnInit, OnDestroy {

    buildingData: BuildingData;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private buildingDataService: BuildingDataService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBuildingData();
    }

    load(id) {
        this.buildingDataService.find(id)
            .subscribe((buildingDataResponse: HttpResponse<BuildingData>) => {
                this.buildingData = buildingDataResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBuildingData() {
        this.eventSubscriber = this.eventManager.subscribe(
            'buildingDataListModification',
            (response) => this.load(this.buildingData.id)
        );
    }
}
