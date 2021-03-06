import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Building } from './building.model';
import { BuildingService } from './building.service';

@Component({
    selector: 'jhi-building-detail',
    templateUrl: './building-detail.component.html'
})
export class BuildingDetailComponent implements OnInit, OnDestroy {

    building: Building;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private buildingService: BuildingService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBuildings();
    }

    load(id) {
        this.buildingService.find(id)
            .subscribe((buildingResponse: HttpResponse<Building>) => {
                this.building = buildingResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBuildings() {
        this.eventSubscriber = this.eventManager.subscribe(
            'buildingListModification',
            (response) => this.load(this.building.id)
        );
    }
}
