import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BuildingData } from './building-data.model';
import { BuildingDataPopupService } from './building-data-popup.service';
import { BuildingDataService } from './building-data.service';
import { Building, BuildingService } from '../building';
import { BuildingDataDefinition, BuildingDataDefinitionService } from '../building-data-definition';

@Component({
    selector: 'jhi-building-data-dialog',
    templateUrl: './building-data-dialog.component.html'
})
export class BuildingDataDialogComponent implements OnInit {

    buildingData: BuildingData;
    isSaving: boolean;

    buildings: Building[];

    buildingdatadefinitions: BuildingDataDefinition[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private buildingDataService: BuildingDataService,
        private buildingService: BuildingService,
        private buildingDataDefinitionService: BuildingDataDefinitionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.buildingService.query()
            .subscribe((res: HttpResponse<Building[]>) => { this.buildings = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.buildingDataDefinitionService.query()
            .subscribe((res: HttpResponse<BuildingDataDefinition[]>) => { this.buildingdatadefinitions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.buildingData.id !== undefined) {
            this.subscribeToSaveResponse(
                this.buildingDataService.update(this.buildingData));
        } else {
            this.subscribeToSaveResponse(
                this.buildingDataService.create(this.buildingData));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<BuildingData>>) {
        result.subscribe((res: HttpResponse<BuildingData>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: BuildingData) {
        this.eventManager.broadcast({ name: 'buildingDataListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackBuildingById(index: number, item: Building) {
        return item.id;
    }

    trackBuildingDataDefinitionById(index: number, item: BuildingDataDefinition) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-building-data-popup',
    template: ''
})
export class BuildingDataPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private buildingDataPopupService: BuildingDataPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.buildingDataPopupService
                    .open(BuildingDataDialogComponent as Component, params['id']);
            } else {
                this.buildingDataPopupService
                    .open(BuildingDataDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
