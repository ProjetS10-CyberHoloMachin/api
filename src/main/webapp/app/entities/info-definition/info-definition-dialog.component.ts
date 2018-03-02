import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { InfoDefinition } from './info-definition.model';
import { InfoDefinitionPopupService } from './info-definition-popup.service';
import { InfoDefinitionService } from './info-definition.service';

@Component({
    selector: 'jhi-info-definition-dialog',
    templateUrl: './info-definition-dialog.component.html'
})
export class InfoDefinitionDialogComponent implements OnInit {

    infoDefinition: InfoDefinition;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private infoDefinitionService: InfoDefinitionService,
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
        if (this.infoDefinition.id !== undefined) {
            this.subscribeToSaveResponse(
                this.infoDefinitionService.update(this.infoDefinition));
        } else {
            this.subscribeToSaveResponse(
                this.infoDefinitionService.create(this.infoDefinition));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<InfoDefinition>>) {
        result.subscribe((res: HttpResponse<InfoDefinition>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: InfoDefinition) {
        this.eventManager.broadcast({ name: 'infoDefinitionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-info-definition-popup',
    template: ''
})
export class InfoDefinitionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private infoDefinitionPopupService: InfoDefinitionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.infoDefinitionPopupService
                    .open(InfoDefinitionDialogComponent as Component, params['id']);
            } else {
                this.infoDefinitionPopupService
                    .open(InfoDefinitionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
