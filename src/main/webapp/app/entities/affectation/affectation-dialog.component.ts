import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Affectation } from './affectation.model';
import { AffectationPopupService } from './affectation-popup.service';
import { AffectationService } from './affectation.service';
import { User, UserService } from '../../shared';
import { Notification, NotificationService } from '../notification';

@Component({
    selector: 'jhi-affectation-dialog',
    templateUrl: './affectation-dialog.component.html'
})
export class AffectationDialogComponent implements OnInit {

    affectation: Affectation;
    isSaving: boolean;

    users: User[];

    notifications: Notification[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private affectationService: AffectationService,
        private userService: UserService,
        private notificationService: NotificationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.notificationService.query()
            .subscribe((res: HttpResponse<Notification[]>) => { this.notifications = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.affectation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.affectationService.update(this.affectation));
        } else {
            this.subscribeToSaveResponse(
                this.affectationService.create(this.affectation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Affectation>>) {
        result.subscribe((res: HttpResponse<Affectation>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Affectation) {
        this.eventManager.broadcast({ name: 'affectationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackNotificationById(index: number, item: Notification) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-affectation-popup',
    template: ''
})
export class AffectationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private affectationPopupService: AffectationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.affectationPopupService
                    .open(AffectationDialogComponent as Component, params['id']);
            } else {
                this.affectationPopupService
                    .open(AffectationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
