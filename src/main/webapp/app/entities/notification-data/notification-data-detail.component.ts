import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { NotificationData } from './notification-data.model';
import { NotificationDataService } from './notification-data.service';

@Component({
    selector: 'jhi-notification-data-detail',
    templateUrl: './notification-data-detail.component.html'
})
export class NotificationDataDetailComponent implements OnInit, OnDestroy {

    notificationData: NotificationData;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private notificationDataService: NotificationDataService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNotificationData();
    }

    load(id) {
        this.notificationDataService.find(id)
            .subscribe((notificationDataResponse: HttpResponse<NotificationData>) => {
                this.notificationData = notificationDataResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNotificationData() {
        this.eventSubscriber = this.eventManager.subscribe(
            'notificationDataListModification',
            (response) => this.load(this.notificationData.id)
        );
    }
}
