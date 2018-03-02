import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BuildingDataDefinition } from './building-data-definition.model';
import { BuildingDataDefinitionPopupService } from './building-data-definition-popup.service';
import { BuildingDataDefinitionService } from './building-data-definition.service';

@Component({
    selector: 'jhi-building-data-definition-dialog',
    templateUrl: './building-data-definition-dialog.component.html'
})
export class BuildingDataDefinitionDialogComponent implements OnInit {

    buildingDataDefinition: BuildingDataDefinition;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private buildingDataDefinitionService: BuildingDataDefinitionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.buildingDataDefinition.id !== undefined) {
            this.subscribeToSaveResponse(
                this.buildingDataDefinitionService.update(this.buildingDataDefinition));
        } else {
            this.subscribeToSaveResponse(
                this.buildingDataDefinitionService.create(this.buildingDataDefinition));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<BuildingDataDefinition>>) {
        result.subscribe((res: HttpResponse<BuildingDataDefinition>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: BuildingDataDefinition) {
        this.eventManager.broadcast({ name: 'buildingDataDefinitionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-building-data-definition-popup',
    template: ''
})
export class BuildingDataDefinitionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private buildingDataDefinitionPopupService: BuildingDataDefinitionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.buildingDataDefinitionPopupService
                    .open(BuildingDataDefinitionDialogComponent as Component, params['id']);
            } else {
                this.buildingDataDefinitionPopupService
                    .open(BuildingDataDefinitionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
