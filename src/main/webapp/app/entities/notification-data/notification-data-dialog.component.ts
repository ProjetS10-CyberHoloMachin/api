import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { NotificationData } from './notification-data.model';
import { NotificationDataPopupService } from './notification-data-popup.service';
import { NotificationDataService } from './notification-data.service';
import { Notification, NotificationService } from '../notification';

@Component({
    selector: 'jhi-notification-data-dialog',
    templateUrl: './notification-data-dialog.component.html'
})
export class NotificationDataDialogComponent implements OnInit {

    notificationData: NotificationData;
    isSaving: boolean;

    notifications: Notification[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private notificationDataService: NotificationDataService,
        private notificationService: NotificationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.notificationService.query()
            .subscribe((res: HttpResponse<Notification[]>) => { this.notifications = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.notificationData.id !== undefined) {
            this.subscribeToSaveResponse(
                this.notificationDataService.update(this.notificationData));
        } else {
            this.subscribeToSaveResponse(
                this.notificationDataService.create(this.notificationData));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<NotificationData>>) {
        result.subscribe((res: HttpResponse<NotificationData>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: NotificationData) {
        this.eventManager.broadcast({ name: 'notificationDataListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackNotificationById(index: number, item: Notification) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-notification-data-popup',
    template: ''
})
export class NotificationDataPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private notificationDataPopupService: NotificationDataPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.notificationDataPopupService
                    .open(NotificationDataDialogComponent as Component, params['id']);
            } else {
                this.notificationDataPopupService
                    .open(NotificationDataDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
