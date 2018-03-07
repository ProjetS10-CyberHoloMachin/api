import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { NotificationData } from './notification-data.model';
import { NotificationDataPopupService } from './notification-data-popup.service';
import { NotificationDataService } from './notification-data.service';

@Component({
    selector: 'jhi-notification-data-delete-dialog',
    templateUrl: './notification-data-delete-dialog.component.html'
})
export class NotificationDataDeleteDialogComponent {

    notificationData: NotificationData;

    constructor(
        private notificationDataService: NotificationDataService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.notificationDataService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'notificationDataListModification',
                content: 'Deleted an notificationData'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-notification-data-delete-popup',
    template: ''
})
export class NotificationDataDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private notificationDataPopupService: NotificationDataPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.notificationDataPopupService
                .open(NotificationDataDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
