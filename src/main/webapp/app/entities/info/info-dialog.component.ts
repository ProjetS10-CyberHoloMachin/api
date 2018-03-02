import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Info } from './info.model';
import { InfoPopupService } from './info-popup.service';
import { InfoService } from './info.service';
import { InfoDefinition, InfoDefinitionService } from '../info-definition';
import { Notification, NotificationService } from '../notification';

@Component({
    selector: 'jhi-info-dialog',
    templateUrl: './info-dialog.component.html'
})
export class InfoDialogComponent implements OnInit {

    info: Info;
    isSaving: boolean;

    infodefinitions: InfoDefinition[];

    notifications: Notification[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private infoService: InfoService,
        private infoDefinitionService: InfoDefinitionService,
        private notificationService: NotificationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.infoDefinitionService.query()
            .subscribe((res: HttpResponse<InfoDefinition[]>) => { this.infodefinitions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.notificationService.query()
            .subscribe((res: HttpResponse<Notification[]>) => { this.notifications = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.info.id !== undefined) {
            this.subscribeToSaveResponse(
                this.infoService.update(this.info));
        } else {
            this.subscribeToSaveResponse(
                this.infoService.create(this.info));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Info>>) {
        result.subscribe((res: HttpResponse<Info>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Info) {
        this.eventManager.broadcast({ name: 'infoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackInfoDefinitionById(index: number, item: InfoDefinition) {
        return item.id;
    }

    trackNotificationById(index: number, item: Notification) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-info-popup',
    template: ''
})
export class InfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private infoPopupService: InfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.infoPopupService
                    .open(InfoDialogComponent as Component, params['id']);
            } else {
                this.infoPopupService
                    .open(InfoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
